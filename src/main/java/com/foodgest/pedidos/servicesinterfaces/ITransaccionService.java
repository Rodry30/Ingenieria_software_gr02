package com.foodgest.pedidos.servicesinterfaces;

import com.foodgest.pedidos.dtos.TransaccionCreateDto;
import com.foodgest.pedidos.dtos.TransaccionResponseDto;

import java.util.List;
import java.util.UUID;

public interface ITransaccionService {
    List<TransaccionResponseDto> listByPedido(UUID pedidoId);
    TransaccionResponseDto registrarPagoAprobado(UUID pedidoId, TransaccionCreateDto dto);
    void procesarWebhookCulqi(String payload, String signature);
}

