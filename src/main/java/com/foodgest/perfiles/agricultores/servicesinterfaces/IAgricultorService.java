package com.foodgest.perfiles.agricultores.servicesinterfaces;

import com.foodgest.perfiles.agricultores.dtos.AgricultorCreateDto;
import com.foodgest.perfiles.agricultores.dtos.AgricultorResponseDto;
import com.foodgest.perfiles.agricultores.dtos.AgricultorUpdateDto;

import java.util.List;
import java.util.UUID;

public interface IAgricultorService {
    List<AgricultorResponseDto> listar();
    AgricultorResponseDto obtenerPorId(UUID id);
    AgricultorResponseDto crear(AgricultorCreateDto dto);
    AgricultorResponseDto actualizar(UUID id, AgricultorUpdateDto dto);
    void eliminar(UUID id);
}

