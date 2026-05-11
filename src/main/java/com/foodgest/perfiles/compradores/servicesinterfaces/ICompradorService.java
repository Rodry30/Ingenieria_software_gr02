package com.foodgest.perfiles.compradores.servicesinterfaces;

import com.foodgest.perfiles.compradores.dtos.CompradorCreateDto;
import com.foodgest.perfiles.compradores.dtos.CompradorResponseDto;
import com.foodgest.perfiles.compradores.dtos.CompradorUpdateDto;

import java.util.List;
import java.util.UUID;

public interface ICompradorService {
    List<CompradorResponseDto> listar();
    CompradorResponseDto obtenerPorId(UUID id);
    CompradorResponseDto crear(CompradorCreateDto dto);
    CompradorResponseDto actualizar(UUID id, CompradorUpdateDto dto);
    void eliminar(UUID id);
}
