package com.foodgest.marketplace.precios.servicesinterfaces;

import com.foodgest.marketplace.precios.entities.PrecioMercado;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPrecioMercadoService {
    List<PrecioMercado> list();
    Optional<PrecioMercado> listId(UUID id);
    List<PrecioMercado> listByProducto(UUID productoId);
    List<PrecioMercado> listByProductoOrdenado(UUID productoId);
    void insert(PrecioMercado precio);
    void update(PrecioMercado precio);
    void delete(UUID id);
}
