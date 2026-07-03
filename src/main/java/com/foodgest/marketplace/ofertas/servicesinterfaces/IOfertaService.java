package com.foodgest.marketplace.ofertas.servicesinterfaces;

import com.foodgest.marketplace.ofertas.dtos.OfertaMapaResponseDto;
import com.foodgest.marketplace.ofertas.entities.Oferta;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IOfertaService {
    List<Oferta> list();
    Optional<Oferta> listId(UUID id);
    List<Oferta> listByAgricultor(UUID agricultorId);
    List<Oferta> listByEstado(String estado);
    List<Oferta> listByAgricultorAndEstado(UUID agricultorId, String estado);
    List<OfertaMapaResponseDto> listMapa(BigDecimal latitud, BigDecimal longitud, BigDecimal radioKm);
    void insert(Oferta oferta);
    void update(Oferta oferta);
    void delete(UUID id);
    void incrementarVistas(UUID id);
}
