package com.foodgest.logistica.servicesimplements;

import com.foodgest.logistica.dtos.TrackingCreateDto;
import com.foodgest.logistica.dtos.TrackingResponseDto;
import com.foodgest.logistica.entities.TrackingPedido;
import com.foodgest.logistica.repositories.TrackingPedidoRepository;
import com.foodgest.logistica.servicesinterfaces.ITrackingService;
import com.foodgest.pedidos.entities.Pedido;
import com.foodgest.pedidos.repositories.PedidoRepository;
import com.foodgest.perfiles.transportistas.entities.TransportistaEntity;
import com.foodgest.perfiles.transportistas.repositories.TransportistaRepository;
import com.foodgest.shared.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TrackingServiceImpl implements ITrackingService {

    private final TrackingPedidoRepository trackingPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final TransportistaRepository transportistaRepository;

    public TrackingServiceImpl(TrackingPedidoRepository trackingPedidoRepository,
                               PedidoRepository pedidoRepository,
                               TransportistaRepository transportistaRepository) {
        this.trackingPedidoRepository = trackingPedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.transportistaRepository = transportistaRepository;
    }

    @Override
    @Transactional
    public TrackingResponseDto registrar(TrackingCreateDto dto) {
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new BusinessException("Pedido no encontrado", HttpStatus.NOT_FOUND));
        TransportistaEntity transportista = transportistaRepository.findById(dto.getTransportistaId())
                .orElseThrow(() -> new BusinessException("Transportista no encontrado", HttpStatus.NOT_FOUND));

        TrackingPedido tracking = new TrackingPedido();
        tracking.setPedido(pedido);
        tracking.setTransportista(transportista);
        tracking.setLatitud(dto.getLatitud());
        tracking.setLongitud(dto.getLongitud());
        tracking.setVelocidadKmh(dto.getVelocidadKmh());
        tracking.setDistanciaRestanteKm(dto.getDistanciaRestanteKm());
        tracking.setEta(dto.getEta());
        tracking.setDescripcion(dto.getDescripcion());

        TrackingPedido saved = trackingPedidoRepository.save(tracking);
        trackingPedidoRepository.sincronizarUbicacion(saved.getId());

        pedido.setTransportista(transportista);
        if ("pagado_por_enviar".equals(pedido.getEstado())) {
            pedido.setEstado("en_transito");
        }
        pedidoRepository.save(pedido);

        transportista.setLatitudActual(dto.getLatitud());
        transportista.setLongitudActual(dto.getLongitud());
        transportistaRepository.save(transportista);

        return TrackingResponseDto.from(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrackingResponseDto> historialPorPedido(UUID pedidoId) {
        return trackingPedidoRepository.findByPedidoIdOrderByCreatedAtDesc(pedidoId).stream()
                .map(TrackingResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TrackingResponseDto ultimaUbicacion(UUID pedidoId) {
        return trackingPedidoRepository.findFirstByPedidoIdOrderByCreatedAtDesc(pedidoId)
                .map(TrackingResponseDto::from)
                .orElseThrow(() -> new BusinessException("No hay tracking para este pedido", HttpStatus.NOT_FOUND));
    }
}

