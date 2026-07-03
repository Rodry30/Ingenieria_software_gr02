package com.foodgest.subastas.suscripciones.repositories;

import com.foodgest.subastas.suscripciones.entities.Suscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SuscripcionRepository extends JpaRepository<Suscripcion, UUID> {
    List<Suscripcion> findByCompradorId(UUID compradorId);
    List<Suscripcion> findByAgricultorId(UUID agricultorId);
    List<Suscripcion> findByEstado(String estado);
}

