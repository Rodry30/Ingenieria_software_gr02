package com.foodgest.subastas.suscripciones.servicesinterfaces;

import com.foodgest.subastas.suscripciones.dtos.SuscripcionCreateDto;
import com.foodgest.subastas.suscripciones.dtos.SuscripcionResponseDto;
import com.foodgest.subastas.suscripciones.dtos.SuscripcionUpdateDto;

import java.util.List;
import java.util.UUID;

public interface ISuscripcionService {
    List<SuscripcionResponseDto> list(String estado);
    SuscripcionResponseDto listId(UUID id);
    List<SuscripcionResponseDto> listByComprador(UUID compradorId);
    List<SuscripcionResponseDto> listByAgricultor(UUID agricultorId);
    SuscripcionResponseDto crear(SuscripcionCreateDto dto);
    SuscripcionResponseDto actualizar(UUID id, SuscripcionUpdateDto dto);
    SuscripcionResponseDto cancelar(UUID id);
}

