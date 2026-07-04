package com.foodgest.marketplace.ofertas.servicesimplements;

import com.foodgest.marketplace.ofertas.dtos.OfertaMapaResponseDto;
import com.foodgest.marketplace.ofertas.entities.Oferta;
import com.foodgest.marketplace.ofertas.repositories.OfertaRepository;
import com.foodgest.marketplace.ofertas.servicesinterfaces.IOfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OfertaServiceImpl implements IOfertaService {

    @Autowired
    private OfertaRepository ofertaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Oferta> list() { return ofertaRepository.findAll(); }

    @Override
    @Transactional(readOnly = true)
    public Optional<Oferta> listId(UUID id) { return ofertaRepository.findById(id); }

    @Override
    @Transactional(readOnly = true)
    public List<Oferta> listByAgricultor(UUID agricultorId) {
        return ofertaRepository.findByAgricultorId(agricultorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Oferta> listByEstado(String estado) {
        return ofertaRepository.findByEstado(estado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Oferta> listByAgricultorAndEstado(UUID agricultorId, String estado) {
        return ofertaRepository.findByAgricultorIdAndEstado(agricultorId, estado);
    }

    @Override
    public List<OfertaMapaResponseDto> listMapa(BigDecimal latitud, BigDecimal longitud, BigDecimal radioKm) {
        return ofertaRepository.findOfertasMapa(latitud, longitud, radioKm).stream()
                .map(OfertaMapaResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void insert(Oferta oferta) {
        Oferta saved = ofertaRepository.save(oferta);
        ofertaRepository.sincronizarUbicacionOferta(saved.getId());
    }

    @Override
    @Transactional
    public void update(Oferta oferta) {
        Oferta saved = ofertaRepository.save(oferta);
        ofertaRepository.sincronizarUbicacionOferta(saved.getId());
    }

    @Override
    @Transactional
    public void delete(UUID id) { ofertaRepository.deleteById(id); }

    @Override
    @Transactional
    public void incrementarVistas(UUID id) { ofertaRepository.incrementarVistas(id); }
}
