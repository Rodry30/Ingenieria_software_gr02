package com.foodgest.catalogo.servicesinterfaces;

import com.foodgest.catalogo.entities.Categoria;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICategoriaService {
    List<Categoria> list();
    Optional<Categoria> listId(UUID id);
    void insert(Categoria categoria);
    void update(Categoria categoria);
    void delete(UUID id);
}
