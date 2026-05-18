package com.foodgest.marketplace.ofertas.servicesinterfaces;

import com.foodgest.marketplace.ofertas.entities.Oferta;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IOfertaService {
    List<Oferta> list();
    Optional<Oferta> listId(UUID id);
    List<Oferta> listByAgricultor(UUID agricultorId);
    List<Oferta> listByEstado(String estado);
    List<Oferta> listByAgricultorAndEstado(UUID agricultorId, String estado);
    void insert(Oferta oferta);
    void update(Oferta oferta);
    void delete(UUID id);
    void incrementarVistas(UUID id);
}
