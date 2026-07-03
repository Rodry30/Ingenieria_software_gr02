package com.foodgest.marketplace.precios.servicesinterfaces;

import com.foodgest.marketplace.precios.dtos.PrecioEscalonadoDto;

import java.util.List;
import java.util.UUID;

public interface IPrecioEscalonadoService {
    List<PrecioEscalonadoDto> listByOferta(UUID ofertaId);
    PrecioEscalonadoDto crear(PrecioEscalonadoDto dto);
    PrecioEscalonadoDto actualizar(UUID id, PrecioEscalonadoDto dto);
    void eliminar(UUID id);
}

