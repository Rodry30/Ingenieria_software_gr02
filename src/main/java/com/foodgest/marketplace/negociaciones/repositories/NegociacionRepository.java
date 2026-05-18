package com.foodgest.marketplace.negociaciones.repositories;

import com.foodgest.marketplace.negociaciones.entities.Negociacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NegociacionRepository extends JpaRepository<Negociacion, UUID> {

    List<Negociacion> findByOfertaId(UUID ofertaId);

    List<Negociacion> findByCompradorId(UUID compradorId);

    List<Negociacion> findByEstado(String estado);

    List<Negociacion> findByOfertaIdAndEstado(UUID ofertaId, String estado);

    List<Negociacion> findByCompradorIdAndEstado(UUID compradorId, String estado);
}
