package com.foodgest.catalogo.servicesinterfaces;

import com.foodgest.catalogo.entities.FotoProducto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IFotoProductoService {
    List<FotoProducto> listByProducto(UUID productoId);
    Optional<FotoProducto> listId(UUID id);
    FotoProducto insert(FotoProducto fotoProducto);
    void delete(UUID id);
}
