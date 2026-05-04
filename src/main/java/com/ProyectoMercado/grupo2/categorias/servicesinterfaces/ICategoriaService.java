package com.ProyectoMercado.grupo2.categorias.servicesinterfaces;

import com.ProyectoMercado.grupo2.categorias.entities.Categoria;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICategoriaService {
    List<Categoria> list();
    void insert(Categoria categoria);
    Optional<Categoria> listId(UUID id);
    void update(Categoria categoria);
    void delete(UUID id);
}
