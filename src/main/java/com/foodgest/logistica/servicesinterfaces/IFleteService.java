package com.foodgest.logistica.servicesinterfaces;

import java.math.BigDecimal;
import java.util.UUID;

public interface IFleteService {
    BigDecimal calcularFleteEstimado(BigDecimal origenLat, BigDecimal origenLng,
                                     BigDecimal destinoLat, BigDecimal destinoLng,
                                     BigDecimal pesoToneladas, String region);
                                     
    BigDecimal calcularFleteParaPedido(UUID pedidoId);
}
