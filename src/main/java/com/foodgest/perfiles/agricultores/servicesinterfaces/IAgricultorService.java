package com.foodgest.perfiles.agricultores.servicesinterfaces;

import com.foodgest.perfiles.agricultores.entities.Agricultor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAgricultorService {
    List<Agricultor> list();
    Optional<Agricultor> listId(UUID id);
    Optional<Agricultor> findByUsuarioId(UUID usuarioId);
    void insert(Agricultor agricultor);
    void update(Agricultor agricultor);
    void delete(UUID id);
}

