package com.foodgest.perfiles.transportistas.servicesinterfaces;

import com.foodgest.perfiles.transportistas.dtos.TransportistaCreateDto;
import com.foodgest.perfiles.transportistas.dtos.TransportistaResponseDto;
import com.foodgest.perfiles.transportistas.dtos.TransportistaUpdateDto;

import java.util.List;
import java.util.UUID;

public interface ITransportistaService {
    List<TransportistaResponseDto> listar();
    TransportistaResponseDto obtenerPorId(UUID id);
    TransportistaResponseDto crear(TransportistaCreateDto dto);
    TransportistaResponseDto actualizar(UUID id, TransportistaUpdateDto dto);
    void eliminar(UUID id);
}
