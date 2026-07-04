package com.foodgest.perfiles.agricultores.servicesimplements;

import com.foodgest.perfiles.agricultores.entities.Agricultor;
import com.foodgest.perfiles.agricultores.repositories.AgricultorRepository;
import com.foodgest.perfiles.agricultores.servicesinterfaces.IAgricultorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AgricultorServiceImpl implements IAgricultorService {

    @Autowired
    private AgricultorRepository agricultorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Agricultor> list() { return agricultorRepository.findAll(); }

    @Override
    @Transactional(readOnly = true)
    public Optional<Agricultor> listId(UUID id) { return agricultorRepository.findById(id); }
    @Override
    @Transactional(readOnly = true)
    public Optional<Agricultor> findByUsuarioId(UUID usuarioId) { return agricultorRepository.findByUsuarioId(usuarioId); }

    @Override
    public void insert(Agricultor agricultor) { agricultorRepository.save(agricultor); }

    @Override
    public void update(Agricultor agricultor) { agricultorRepository.save(agricultor); }

    @Override
    public void delete(UUID id) { agricultorRepository.deleteById(id); }
}