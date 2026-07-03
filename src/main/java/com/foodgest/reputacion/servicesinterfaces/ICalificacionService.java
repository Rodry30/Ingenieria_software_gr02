package com.foodgest.reputacion.servicesinterfaces;

import com.foodgest.reputacion.dtos.CalificacionCreateDto;
import com.foodgest.reputacion.dtos.CalificacionResponseDto;
import com.foodgest.reputacion.dtos.ReputacionUsuarioDto;

import java.util.UUID;

public interface ICalificacionService {
    CalificacionResponseDto calificar(CalificacionCreateDto dto);
    ReputacionUsuarioDto listarPorUsuario(UUID usuarioId);
}

