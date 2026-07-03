package com.foodgest.logistica.servicesinterfaces;

import com.foodgest.logistica.dtos.TrackingCreateDto;
import com.foodgest.logistica.dtos.TrackingResponseDto;

import java.util.List;
import java.util.UUID;

public interface ITrackingService {
    TrackingResponseDto registrar(TrackingCreateDto dto);
    List<TrackingResponseDto> historialPorPedido(UUID pedidoId);
    TrackingResponseDto ultimaUbicacion(UUID pedidoId);
}

