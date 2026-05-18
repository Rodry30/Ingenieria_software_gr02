package com.foodgest.marketplace.precios.servicesimplements;

import com.foodgest.marketplace.precios.entities.PrecioMercado;
import com.foodgest.marketplace.precios.repositories.PrecioMercadoRepository;
import com.foodgest.marketplace.precios.servicesinterfaces.IPrecioMercadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PrecioMercadoServiceImpl implements IPrecioMercadoService {

    @Autowired
    private PrecioMercadoRepository precioMercadoRepository;

    @Override
    public List<PrecioMercado> list() { return precioMercadoRepository.findAll(); }

    @Override
    public Optional<PrecioMercado> listId(UUID id) { return precioMercadoRepository.findById(id); }

    @Override
    public List<PrecioMercado> listByProducto(UUID productoId) {
        return precioMercadoRepository.findByProductoId(productoId);
    }

    @Override
    public List<PrecioMercado> listByProductoOrdenado(UUID productoId) {
        return precioMercadoRepository.findByProductoIdOrderByFechaPrecioDesc(productoId);
    }

    @Override
    @Transactional
    public void insert(PrecioMercado precio) { precioMercadoRepository.save(precio); }

    @Override
    @Transactional
    public void update(PrecioMercado precio) { precioMercadoRepository.save(precio); }

    @Override
    @Transactional
    public void delete(UUID id) { precioMercadoRepository.deleteById(id); }
}
