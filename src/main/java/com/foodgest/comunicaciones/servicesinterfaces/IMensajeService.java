package com.foodgest.comunicaciones.servicesinterfaces;

import com.foodgest.comunicaciones.dtos.MensajeCreateDto;
import com.foodgest.comunicaciones.dtos.MensajeResponseDto;

import java.util.List;
import java.util.UUID;

public interface IMensajeService {
    MensajeResponseDto enviar(MensajeCreateDto dto);
    List<MensajeResponseDto> listarPorPedido(UUID pedidoId);
    List<MensajeResponseDto> listarPorUsuario(UUID usuarioId);
    MensajeResponseDto marcarLeido(UUID id);
}

