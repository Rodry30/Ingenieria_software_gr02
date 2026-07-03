package com.foodgest.subastas.pujas.servicesinterfaces;

import com.foodgest.subastas.pujas.dtos.PujaCreateDto;
import com.foodgest.subastas.pujas.dtos.PujaResponseDto;

import java.util.List;
import java.util.UUID;

public interface IPujaService {
    PujaResponseDto pujar(UUID subastaId, PujaCreateDto dto);
    List<PujaResponseDto> listarPorSubasta(UUID subastaId);
}

