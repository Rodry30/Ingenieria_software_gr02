package com.foodgest.catalogo.servicesimplements;

import com.foodgest.catalogo.entities.Categoria;
import com.foodgest.catalogo.repositories.CategoriaRepository;
import com.foodgest.catalogo.servicesinterfaces.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoriaServiceImpl implements ICategoriaService {

    @Autowired
    private CategoriaRepository cR;

    @Override public List<Categoria> list() { return cR.findAll(); }
    @Override public void insert(Categoria categoria) { cR.save(categoria); }
    @Override public Optional<Categoria> listId(UUID id) { return cR.findById(id); }
    @Override public void update(Categoria categoria) { cR.save(categoria); }
    @Override public void delete(UUID id) { cR.deleteById(id); }
}
