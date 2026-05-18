package com.foodgest.marketplace.negociaciones.servicesinterfaces;

import com.foodgest.marketplace.negociaciones.entities.Negociacion;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface INegociacionService {
    List<Negociacion> list();
    Optional<Negociacion> listId(UUID id);
    List<Negociacion> listByOferta(UUID ofertaId);
    List<Negociacion> listByComprador(UUID compradorId);
    List<Negociacion> listByEstado(String estado);
    void insert(Negociacion negociacion);
    void update(Negociacion negociacion);
    void delete(UUID id);
}
