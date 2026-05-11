package com.foodgest.catalogo.servicesimplements;

import com.foodgest.catalogo.entities.FotoProducto;
import com.foodgest.catalogo.repositories.FotoProductoRepository;
import com.foodgest.catalogo.servicesinterfaces.IFotoProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FotoProductoServiceImpl implements IFotoProductoService {

    @Autowired
    private FotoProductoRepository fotoProductoRepository;

    @Override
    public List<FotoProducto> listByProducto(UUID productoId) {
        return fotoProductoRepository.findByProductoId(productoId);
    }

    @Override
    public Optional<FotoProducto> listId(UUID id) {
        return fotoProductoRepository.findById(id);
    }

    @Override
    public FotoProducto insert(FotoProducto fotoProducto) {
        return fotoProductoRepository.save(fotoProducto);
    }

    @Override
    public void delete(UUID id) {
        fotoProductoRepository.deleteById(id);
    }
}
