package com.foodgest.subastas.subastas.servicesinterfaces;

import com.foodgest.subastas.subastas.dtos.SubastaCreateDto;
import com.foodgest.subastas.subastas.dtos.SubastaEstadoDto;
import com.foodgest.subastas.subastas.dtos.SubastaResponseDto;

import java.util.List;
import java.util.UUID;

public interface ISubastaService {
    List<SubastaResponseDto> list(String estado);
    SubastaResponseDto listId(UUID id);
    SubastaResponseDto crear(SubastaCreateDto dto);
    SubastaResponseDto cambiarEstado(UUID id, SubastaEstadoDto dto);
}

