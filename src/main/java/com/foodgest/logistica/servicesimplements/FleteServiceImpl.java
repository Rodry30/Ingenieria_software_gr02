package com.foodgest.logistica.servicesimplements;

import com.foodgest.logistica.repositories.TrackingPedidoRepository;
import com.foodgest.logistica.servicesinterfaces.IFleteService;
import com.foodgest.pedidos.entities.Pedido;
import com.foodgest.pedidos.repositories.PedidoRepository;
import com.foodgest.shared.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Service
public class FleteServiceImpl implements IFleteService {

    private final TrackingPedidoRepository trackingPedidoRepository;
    private final PedidoRepository pedidoRepository;

    public FleteServiceImpl(TrackingPedidoRepository trackingPedidoRepository,
                            PedidoRepository pedidoRepository) {
        this.trackingPedidoRepository = trackingPedidoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calcularFleteEstimado(BigDecimal origenLat, BigDecimal origenLng,
                                             BigDecimal destinoLat, BigDecimal destinoLng,
                                             BigDecimal pesoToneladas, String region) {
        if (origenLat == null || origenLng == null || destinoLat == null || destinoLng == null) {
            throw new BusinessException("Las coordenadas de origen y destino son obligatorias", HttpStatus.BAD_REQUEST);
        }

        // 1. Calcular distancia aérea usando PostGIS
        double distanciaMetros = trackingPedidoRepository.calcularDistanciaMetros(
                origenLat.doubleValue(), origenLng.doubleValue(),
                destinoLat.doubleValue(), destinoLng.doubleValue()
        );

        double distanciaKm = distanciaMetros / 1000.0;
        
        // 2. Distancia terrestre estimada (+30% por carreteras peruanas)
        double distanciaTerrestreKm = distanciaKm * 1.3;

        // 3. Determinar factor de dificultad por región
        double factorDificultad = 1.0;
        if (region != null) {
            String regionUpper = region.trim().toUpperCase();
            if ("SELVA".equals(regionUpper)) {
                factorDificultad = 1.3;
            } else if ("SIERRA".equals(regionUpper)) {
                factorDificultad = 1.5;
            }
        }

        // 4. Aplicar fórmula de flete
        double tarifaBase = 50.0;
        double tarifaPorKm = 2.5;
        double factorCarga = 10.0;
        double peso = pesoToneladas != null ? pesoToneladas.doubleValue() : 0.0;

        double fleteTotal = tarifaBase + (distanciaTerrestreKm * factorDificultad * tarifaPorKm) + (peso * factorCarga);

        return BigDecimal.valueOf(fleteTotal).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calcularFleteParaPedido(UUID pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new BusinessException("Pedido no encontrado", HttpStatus.NOT_FOUND));

        BigDecimal origenLat = pedido.getOrigenLatitud();
        BigDecimal origenLng = pedido.getOrigenLongitud();
        
        // Si el origen del pedido no está definido, intentar recuperarlo de la parcela del agricultor
        if (origenLat == null || origenLng == null) {
            if (pedido.getOferta() != null && pedido.getOferta().getAgricultor() != null) {
                origenLat = pedido.getOferta().getAgricultor().getLatitud();
                origenLng = pedido.getOferta().getAgricultor().getLongitud();
            }
        }

        BigDecimal destinoLat = pedido.getLatitudEntrega();
        BigDecimal destinoLng = pedido.getLongitudEntrega();

        if (origenLat == null || origenLng == null || destinoLat == null || destinoLng == null) {
            throw new BusinessException("El pedido no cuenta con coordenadas completas para calcular el flete", HttpStatus.BAD_REQUEST);
        }

        // Usamos cantidad acordada como peso referencial
        BigDecimal peso = pedido.getCantidadAcordada();
        
        // Región por defecto a determinar según departamento si existe
        String region = "COSTA";
        String depto = pedido.getComprador() != null ? pedido.getComprador().getDireccionEntregaDefault() : null;
        if (depto == null) {
            depto = pedido.getDireccionEntrega();
        }
        
        if (depto != null) {
            String deptoUpper = depto.toUpperCase();
            // Sierra
            if (deptoUpper.contains("CUSCO") || deptoUpper.contains("JUNIN") || deptoUpper.contains("PUNO") || 
                deptoUpper.contains("HUANCAYO") || deptoUpper.contains("AREQUIPA") || deptoUpper.contains("CAJAMARCA") || 
                deptoUpper.contains("HUANUCO") || deptoUpper.contains("AYACUCHO")) {
                region = "SIERRA";
            }
            // Selva
            else if (deptoUpper.contains("LORETO") || deptoUpper.contains("UCAYALI") || deptoUpper.contains("SAN MARTIN") || 
                     deptoUpper.contains("MADRE DE DIOS") || deptoUpper.contains("AMAZONAS")) {
                region = "SELVA";
            }
        }

        return calcularFleteEstimado(origenLat, origenLng, destinoLat, destinoLng, peso, region);
    }
}
