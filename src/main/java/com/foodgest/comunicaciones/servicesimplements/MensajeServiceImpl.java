package com.foodgest.comunicaciones.servicesimplements;

import com.foodgest.catalogo.repositories.ProductoRepository;
import com.foodgest.comunicaciones.dtos.MensajeCreateDto;
import com.foodgest.comunicaciones.dtos.MensajeResponseDto;
import com.foodgest.comunicaciones.entities.Mensaje;
import com.foodgest.comunicaciones.repositories.MensajeRepository;
import com.foodgest.comunicaciones.servicesinterfaces.IMensajeService;
import com.foodgest.marketplace.ofertas.repositories.OfertaRepository;
import com.foodgest.pedidos.repositories.PedidoRepository;
import com.foodgest.shared.exceptions.BusinessException;
import com.foodgest.users.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MensajeServiceImpl implements IMensajeService {

    private final MensajeRepository mensajeRepository;
    private final UserRepository userRepository;
    private final OfertaRepository ofertaRepository;
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    public MensajeServiceImpl(MensajeRepository mensajeRepository,
                              UserRepository userRepository,
                              OfertaRepository ofertaRepository,
                              PedidoRepository pedidoRepository,
                              ProductoRepository productoRepository) {
        this.mensajeRepository = mensajeRepository;
        this.userRepository = userRepository;
        this.ofertaRepository = ofertaRepository;
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional
    public MensajeResponseDto enviar(MensajeCreateDto dto) {
        Mensaje mensaje = new Mensaje();
        mensaje.setRemitente(userRepository.findById(dto.getRemitenteId())
                .orElseThrow(() -> new BusinessException("Remitente no encontrado", HttpStatus.NOT_FOUND)));
        if (dto.getDestinatarioId() != null) {
            mensaje.setDestinatario(userRepository.findById(dto.getDestinatarioId())
                    .orElseThrow(() -> new BusinessException("Destinatario no encontrado", HttpStatus.NOT_FOUND)));
        }
        if (dto.getOfertaId() != null) {
            mensaje.setOferta(ofertaRepository.findById(dto.getOfertaId())
                    .orElseThrow(() -> new BusinessException("Oferta no encontrada", HttpStatus.NOT_FOUND)));
        }
        if (dto.getPedidoId() != null) {
            mensaje.setPedido(pedidoRepository.findById(dto.getPedidoId())
                    .orElseThrow(() -> new BusinessException("Pedido no encontrado", HttpStatus.NOT_FOUND)));
        }
        if (dto.getProductoId() != null) {
            mensaje.setProducto(productoRepository.findById(dto.getProductoId())
                    .orElseThrow(() -> new BusinessException("Producto no encontrado", HttpStatus.NOT_FOUND)));
        }
        mensaje.setContenido(dto.getContenido());
        mensaje.setTipoArchivo(dto.getTipoArchivo());
        mensaje.setEsGrupo(Boolean.TRUE.equals(dto.getEsGrupo()));
        mensaje.setNombreGrupo(dto.getNombreGrupo());
        mensaje.setLeido(false);
        return MensajeResponseDto.from(mensajeRepository.save(mensaje));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MensajeResponseDto> listarPorPedido(UUID pedidoId) {
        return mensajeRepository.findByPedidoIdOrderByCreatedAtAsc(pedidoId).stream()
                .map(MensajeResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MensajeResponseDto> listarPorUsuario(UUID usuarioId) {
        return mensajeRepository.findByRemitenteIdOrDestinatarioIdOrderByCreatedAtDesc(usuarioId, usuarioId).stream()
                .map(MensajeResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MensajeResponseDto marcarLeido(UUID id) {
        Mensaje mensaje = mensajeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Mensaje no encontrado", HttpStatus.NOT_FOUND));
        mensaje.setLeido(true);
        return MensajeResponseDto.from(mensajeRepository.save(mensaje));
    }
}

