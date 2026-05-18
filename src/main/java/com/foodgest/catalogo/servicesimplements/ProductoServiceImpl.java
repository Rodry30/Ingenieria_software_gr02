package com.foodgest.catalogo.servicesimplements;

import com.foodgest.catalogo.entities.Producto;
import com.foodgest.catalogo.repositories.ProductoRepository;
import com.foodgest.catalogo.servicesinterfaces.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductoServiceImpl implements IProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> list() {
        return productoRepository.findAll();
    }

    @Override
    public List<Producto> listByCategoria(UUID categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId);
    }

    @Override
    public Optional<Producto> listId(UUID id) {
        return productoRepository.findById(id);
    }

    @Override
    public Producto insert(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Producto update(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public void delete(UUID id) {
        productoRepository.deleteById(id);
    }
}
