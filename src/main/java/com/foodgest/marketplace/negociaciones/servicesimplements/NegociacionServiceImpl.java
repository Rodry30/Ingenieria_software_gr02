package com.foodgest.marketplace.negociaciones.servicesimplements;

import com.foodgest.marketplace.negociaciones.entities.Negociacion;
import com.foodgest.marketplace.negociaciones.repositories.NegociacionRepository;
import com.foodgest.marketplace.negociaciones.servicesinterfaces.INegociacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NegociacionServiceImpl implements INegociacionService {

    @Autowired
    private NegociacionRepository negociacionRepository;

    @Override
    public List<Negociacion> list() { return negociacionRepository.findAll(); }

    @Override
    public Optional<Negociacion> listId(UUID id) { return negociacionRepository.findById(id); }

    @Override
    public List<Negociacion> listByOferta(UUID ofertaId) {
        return negociacionRepository.findByOfertaId(ofertaId);
    }

    @Override
    public List<Negociacion> listByComprador(UUID compradorId) {
        return negociacionRepository.findByCompradorId(compradorId);
    }

    @Override
    public List<Negociacion> listByEstado(String estado) {
        return negociacionRepository.findByEstado(estado);
    }

    @Override
    @Transactional
    public void insert(Negociacion negociacion) { negociacionRepository.save(negociacion); }

    @Override
    @Transactional
    public void update(Negociacion negociacion) { negociacionRepository.save(negociacion); }

    @Override
    @Transactional
    public void delete(UUID id) { negociacionRepository.deleteById(id); }
}
