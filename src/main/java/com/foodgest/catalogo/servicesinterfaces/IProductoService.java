package com.foodgest.catalogo.servicesinterfaces;

import com.foodgest.catalogo.entities.Producto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProductoService {
    List<Producto> list();
    List<Producto> listByCategoria(UUID categoriaId);
    Optional<Producto> listId(UUID id);
    Producto insert(Producto producto);
    Producto update(Producto producto);
    void delete(UUID id);
}
