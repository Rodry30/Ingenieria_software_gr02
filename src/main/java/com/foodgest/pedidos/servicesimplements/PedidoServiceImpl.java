package com.foodgest.pedidos.servicesimplements;

import com.foodgest.marketplace.ofertas.entities.Oferta;
import com.foodgest.marketplace.ofertas.repositories.OfertaRepository;
import com.foodgest.pedidos.dtos.PedidoCreateDto;
import com.foodgest.pedidos.dtos.PedidoEstadoUpdateDto;
import com.foodgest.pedidos.dtos.PedidoResponseDto;
import com.foodgest.pedidos.entities.Pedido;
import com.foodgest.pedidos.repositories.PedidoRepository;
import com.foodgest.pedidos.servicesinterfaces.IPedidoService;
import com.foodgest.perfiles.compradores.entities.Comprador;
import com.foodgest.perfiles.compradores.repositories.CompradorRepository;
import com.foodgest.shared.exceptions.BusinessException;
import com.foodgest.users.servicesinterfaces.IWalletService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements IPedidoService {

    private static final BigDecimal COMISION_RATE = new BigDecimal("0.05");

    private final PedidoRepository pedidoRepository;
    private final OfertaRepository ofertaRepository;
    private final CompradorRepository compradorRepository;
    private final IWalletService walletService;

    public PedidoServiceImpl(PedidoRepository pedidoRepository,
                             OfertaRepository ofertaRepository,
                             CompradorRepository compradorRepository,
                             IWalletService walletService) {
        this.pedidoRepository = pedidoRepository;
        this.ofertaRepository = ofertaRepository;
        this.compradorRepository = compradorRepository;
        this.walletService = walletService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponseDto> list() {
        return pedidoRepository.findAll().stream().map(PedidoResponseDto::from).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoResponseDto listId(UUID id) {
        return PedidoResponseDto.from(getPedido(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponseDto> listByComprador(UUID compradorId) {
        return pedidoRepository.findByCompradorId(compradorId).stream()
                .map(PedidoResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PedidoResponseDto crear(PedidoCreateDto dto) {
        Oferta oferta = ofertaRepository.findByIdForUpdate(dto.getOfertaId())
                .orElseThrow(() -> new BusinessException("Oferta no encontrada", HttpStatus.NOT_FOUND));
        Comprador comprador = compradorRepository.findById(dto.getCompradorId())
                .orElseThrow(() -> new BusinessException("Comprador no encontrado", HttpStatus.NOT_FOUND));

        if (!"activa".equals(oferta.getEstado())) {
            throw new BusinessException("La oferta no esta activa", HttpStatus.BAD_REQUEST);
        }
        if (oferta.getCantidadDisponible().compareTo(dto.getCantidadAcordada()) < 0) {
            throw new BusinessException("Stock insuficiente para el pedido", HttpStatus.CONFLICT);
        }

        BigDecimal precio = oferta.getPrecioSugerido();
        BigDecimal subtotal = precio.multiply(dto.getCantidadAcordada()).setScale(2, RoundingMode.HALF_UP);
        BigDecimal costoEnvio = nullToZero(dto.getCostoEnvio());
        BigDecimal comision = subtotal.multiply(COMISION_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.add(costoEnvio).add(comision);

        Pedido pedido = new Pedido();
        pedido.setOferta(oferta);
        pedido.setComprador(comprador);
        pedido.setEstado("pendiente_pago");
        pedido.setPrecioAcordado(precio);
        pedido.setCantidadAcordada(dto.getCantidadAcordada());
        pedido.setSubtotal(subtotal);
        pedido.setCostoEnvio(costoEnvio);
        pedido.setComisionPlataforma(comision);
        pedido.setTotal(total);
        pedido.setMoneda(oferta.getMoneda());
        pedido.setMetodoPago(dto.getMetodoPago());
        pedido.setOrigenDireccion(oferta.getDireccionReferencia());
        pedido.setOrigenLatitud(oferta.getLatitud());
        pedido.setOrigenLongitud(oferta.getLongitud());
        pedido.setDestinoNombre(dto.getDestinoNombre());
        pedido.setDireccionEntrega(dto.getDireccionEntrega());
        pedido.setLatitudEntrega(dto.getLatitudEntrega());
        pedido.setLongitudEntrega(dto.getLongitudEntrega());
        pedido.setFechaEntregaEstimada(dto.getFechaEntregaEstimada());
        pedido.setNotasComprador(dto.getNotasComprador());
        pedido.setCodigoSeguimiento("FG-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        oferta.setCantidadDisponible(oferta.getCantidadDisponible().subtract(dto.getCantidadAcordada()));
        if (oferta.getCantidadDisponible().compareTo(BigDecimal.ZERO) == 0) {
            oferta.setEstado("pausada");
        }
        ofertaRepository.save(oferta);

        return PedidoResponseDto.from(pedidoRepository.save(pedido));
    }

    @Override
    @Transactional
    public PedidoResponseDto actualizarEstado(UUID id, PedidoEstadoUpdateDto dto) {
        Pedido pedido = getPedido(id);
        pedido.setEstado(dto.getEstado());
        if (dto.getGuiaRemisionUrl() != null) pedido.setGuiaRemisionUrl(dto.getGuiaRemisionUrl());
        if (dto.getGuiaRemisionNumero() != null) pedido.setGuiaRemisionNumero(dto.getGuiaRemisionNumero());
        if (dto.getFotoEntregaUrl() != null) pedido.setFotoEntregaUrl(dto.getFotoEntregaUrl());
        if ("entregado_finalizado".equals(dto.getEstado())) {
            pedido.setFechaEntregaReal(OffsetDateTime.now());
        }
        return PedidoResponseDto.from(pedidoRepository.save(pedido));
    }

    @Override
    @Transactional
    public PedidoResponseDto confirmarEntrega(UUID id) {
        Pedido pedido = getPedido(id);
        if (!"en_transito".equals(pedido.getEstado()) && !"pagado_por_enviar".equals(pedido.getEstado())) {
            throw new BusinessException("El pedido no esta listo para confirmar entrega", HttpStatus.BAD_REQUEST);
        }
        walletService.liberarPagoVenta(pedido);
        pedido.setEstado("entregado_finalizado");
        pedido.setFechaEntregaReal(OffsetDateTime.now());
        return PedidoResponseDto.from(pedidoRepository.save(pedido));
    }

    private Pedido getPedido(UUID id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Pedido no encontrado", HttpStatus.NOT_FOUND));
    }

    private BigDecimal nullToZero(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }
}

