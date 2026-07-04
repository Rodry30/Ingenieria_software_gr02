package com.foodgest.pedidos.servicesimplements;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodgest.pedidos.dtos.TransaccionCreateDto;
import com.foodgest.pedidos.dtos.TransaccionResponseDto;
import com.foodgest.pedidos.entities.Pedido;
import com.foodgest.pedidos.entities.Transaccion;
import com.foodgest.pedidos.repositories.PedidoRepository;
import com.foodgest.pedidos.repositories.TransaccionRepository;
import com.foodgest.pedidos.servicesinterfaces.ITransaccionService;
import com.foodgest.shared.exceptions.BusinessException;
import com.foodgest.users.servicesinterfaces.IWalletService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransaccionServiceImpl implements ITransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final PedidoRepository pedidoRepository;
    private final IWalletService walletService;
    private final ObjectMapper objectMapper;

    @Value("${culqi.webhook.secret:dummy_webhook_secret}")
    private String webhookSecret;

    public TransaccionServiceImpl(TransaccionRepository transaccionRepository,
                                  PedidoRepository pedidoRepository,
                                  IWalletService walletService,
                                  ObjectMapper objectMapper) {
        this.transaccionRepository = transaccionRepository;
        this.pedidoRepository = pedidoRepository;
        this.walletService = walletService;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransaccionResponseDto> listByPedido(UUID pedidoId) {
        return transaccionRepository.findByPedidoIdOrderByFechaTransaccionDesc(pedidoId).stream()
                .map(TransaccionResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TransaccionResponseDto registrarPagoAprobado(UUID pedidoId, TransaccionCreateDto dto) {
        if (transaccionRepository.findByReferenciaExterna(dto.getReferenciaExterna()).isPresent()) {
            throw new BusinessException("La transaccion ya fue procesada", HttpStatus.CONFLICT);
        }

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new BusinessException("Pedido no encontrado", HttpStatus.NOT_FOUND));
        if (!"pendiente_pago".equals(pedido.getEstado())) {
            throw new BusinessException("El pedido no esta pendiente de pago", HttpStatus.BAD_REQUEST);
        }

        Transaccion transaccion = new Transaccion();
        transaccion.setPedido(pedido);
        transaccion.setTipo("pago");
        transaccion.setMonto(pedido.getTotal());
        transaccion.setMoneda(pedido.getMoneda());
        transaccion.setEstado("aprobada");
        transaccion.setPasarelaPago(dto.getPasarelaPago());
        transaccion.setReferenciaExterna(dto.getReferenciaExterna());
        transaccion.setCodigoAutorizacion(dto.getCodigoAutorizacion());
        transaccion.setMetadata(dto.getMetadata());
        transaccion.setDescripcion("Pago aprobado y retenido en escrow");

        pedido.setEstado("pagado_por_enviar");
        pedido.setFechaConfirmacion(OffsetDateTime.now());
        pedidoRepository.save(pedido);
        walletService.retenerPagoVenta(pedido);

        return TransaccionResponseDto.from(transaccionRepository.save(transaccion));
    }

    @Override
    @Transactional
    public void procesarWebhookCulqi(String payload, String signature) {
        // Fail-closed: sin secreto configurado o sin firma, se rechaza el webhook
        // en vez de procesarlo como si viniera de Culqi. Antes, dejar el secreto
        // en su valor por defecto (o no mandar firma) se saltaba la verificacion
        // por completo y cualquiera podia marcar pedidos como pagados.
        if (webhookSecret == null || webhookSecret.isBlank() || webhookSecret.equals("dummy_webhook_secret")) {
            throw new BusinessException(
                    "El webhook de Culqi no esta configurado (falta CULQI_WEBHOOK_SECRET en el servidor)",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (signature == null || signature.isBlank()) {
            throw new BusinessException("Falta la firma x-culqi-signature del webhook", HttpStatus.UNAUTHORIZED);
        }

        try {
            javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA256");
            javax.crypto.spec.SecretKeySpec secretKey = new javax.crypto.spec.SecretKeySpec(webhookSecret.getBytes("UTF-8"), "HmacSHA256");
            mac.init(secretKey);
            byte[] hash = mac.doFinal(payload.getBytes("UTF-8"));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            String computedSignatureHex = hexString.toString();

            String computedSignatureB64 = java.util.Base64.getEncoder().encodeToString(hash);

            boolean match = java.security.MessageDigest.isEqual(computedSignatureHex.getBytes(), signature.getBytes())
                    || java.security.MessageDigest.isEqual(computedSignatureB64.getBytes(), signature.getBytes());

            if (!match) {
                throw new BusinessException("Firma del webhook de Culqi invalida", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            if (e instanceof BusinessException) throw (BusinessException) e;
            throw new BusinessException("Error validando firma de webhook: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            JsonNode root = objectMapper.readTree(payload);
            JsonNode dataNode = root.path("data");
            String orderId = dataNode.path("id").asText();
            
            JsonNode metadataNode = dataNode.path("metadata");
            if (metadataNode.isMissingNode() || metadataNode.isNull()) {
                metadataNode = root.path("metadata");
            }
            
            String pedidoIdStr = null;
            if (!metadataNode.isMissingNode() && !metadataNode.isNull()) {
                if (metadataNode.has("pedidoId")) {
                    pedidoIdStr = metadataNode.path("pedidoId").asText();
                } else if (metadataNode.has("pedido_id")) {
                    pedidoIdStr = metadataNode.path("pedido_id").asText();
                }
            }
            
            if (pedidoIdStr == null || pedidoIdStr.trim().isEmpty()) {
                java.util.Iterator<JsonNode> fields = dataNode.elements();
                while (fields.hasNext()) {
                    JsonNode field = fields.next();
                    if (field.isTextual() && isUUID(field.asText())) {
                        pedidoIdStr = field.asText();
                        break;
                    }
                }
            }
            
            if (pedidoIdStr == null || pedidoIdStr.trim().isEmpty()) {
                throw new BusinessException("No se encontro pedidoId en el payload del webhook", HttpStatus.BAD_REQUEST);
            }
            
            UUID pedidoId = UUID.fromString(pedidoIdStr);
            
            boolean isPaymentSuccess = true; 
            if (dataNode.has("status")) {
                String status = dataNode.path("status").asText();
                if ("failed".equalsIgnoreCase(status) || "expired".equalsIgnoreCase(status)) {
                    isPaymentSuccess = false;
                }
            }
            
            if (isPaymentSuccess) {
                TransaccionCreateDto transCreateDto = new TransaccionCreateDto();
                transCreateDto.setPasarelaPago("Culqi");
                transCreateDto.setReferenciaExterna(orderId != null && !orderId.isEmpty() ? orderId : UUID.randomUUID().toString());
                
                String authCode = dataNode.path("charge").path("authorization_code").asText();
                if (authCode == null || authCode.isEmpty()) {
                    authCode = dataNode.path("authorization_code").asText();
                }
                transCreateDto.setCodigoAutorizacion(authCode != null && !authCode.isEmpty() ? authCode : "N/A");
                transCreateDto.setMetadata(payload);
                
                if (transaccionRepository.findByReferenciaExterna(transCreateDto.getReferenciaExterna()).isPresent()) {
                    return;
                }
                
                registrarPagoAprobado(pedidoId, transCreateDto);
            } else {
                Pedido pedido = pedidoRepository.findById(pedidoId).orElse(null);
                if (pedido != null && "pendiente_pago".equals(pedido.getEstado())) {
                    pedido.setEstado("cancelado");
                    pedidoRepository.save(pedido);
                }
            }
        } catch (Exception e) {
            if (e instanceof BusinessException) throw (BusinessException) e;
            throw new BusinessException("Error al procesar JSON de webhook: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isUUID(String str) {
        try {
            UUID.fromString(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

