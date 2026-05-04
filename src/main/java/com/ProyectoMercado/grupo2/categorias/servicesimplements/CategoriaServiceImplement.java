package com.ProyectoMercado.grupo2.categorias.servicesimplements;

import com.ProyectoMercado.grupo2.categorias.entities.Categoria;
import com.ProyectoMercado.grupo2.categorias.repositories.ICategoriaRepository;
import com.ProyectoMercado.grupo2.categorias.servicesinterfaces.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoriaServiceImplement implements ICategoriaService {

    @Autowired
    private ICategoriaRepository cR;

    @Override
    public List<Categoria> list() {
        return cR.findAll();
    }

    @Override
    public void insert(Categoria categoria) {
        cR.save(categoria);
    }

    @Override
    public Optional<Categoria> listId(UUID id) {
        return cR.findById(id);
    }

    @Override
    public void update(Categoria categoria) {
        cR.save(categoria);
    }

    @Override
    public void delete(UUID id) {
        cR.deleteById(id);
    }
}
