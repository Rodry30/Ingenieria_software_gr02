package com.foodgest.comunicaciones.servicesinterfaces;

import com.foodgest.comunicaciones.dtos.NotificacionCreateDto;
import com.foodgest.comunicaciones.dtos.NotificacionResponseDto;

import java.util.List;
import java.util.UUID;

public interface INotificacionService {
    NotificacionResponseDto crear(NotificacionCreateDto dto);
    List<NotificacionResponseDto> listarPorUsuario(UUID usuarioId, Boolean leido);
    NotificacionResponseDto marcarLeida(UUID id);
}

