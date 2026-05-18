package com.foodgest.marketplace.ofertas.servicesimplements;

import com.foodgest.marketplace.ofertas.entities.Oferta;
import com.foodgest.marketplace.ofertas.repositories.OfertaRepository;
import com.foodgest.marketplace.ofertas.servicesinterfaces.IOfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OfertaServiceImpl implements IOfertaService {

    @Autowired
    private OfertaRepository ofertaRepository;

    @Override
    public List<Oferta> list() { return ofertaRepository.findAll(); }

    @Override
    public Optional<Oferta> listId(UUID id) { return ofertaRepository.findById(id); }

    @Override
    public List<Oferta> listByAgricultor(UUID agricultorId) {
        return ofertaRepository.findByAgricultorId(agricultorId);
    }

    @Override
    public List<Oferta> listByEstado(String estado) {
        return ofertaRepository.findByEstado(estado);
    }

    @Override
    public List<Oferta> listByAgricultorAndEstado(UUID agricultorId, String estado) {
        return ofertaRepository.findByAgricultorIdAndEstado(agricultorId, estado);
    }

    @Override
    @Transactional
    public void insert(Oferta oferta) { ofertaRepository.save(oferta); }

    @Override
    @Transactional
    public void update(Oferta oferta) { ofertaRepository.save(oferta); }

    @Override
    @Transactional
    public void delete(UUID id) { ofertaRepository.deleteById(id); }

    @Override
    @Transactional
    public void incrementarVistas(UUID id) { ofertaRepository.incrementarVistas(id); }
}
