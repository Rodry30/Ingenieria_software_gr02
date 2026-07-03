package com.foodgest.reputacion.repositories;

import com.foodgest.reputacion.entities.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, UUID> {
    List<Calificacion> findByCalificadoIdOrderByCreatedAtDesc(UUID calificadoId);
    boolean existsByPedidoIdAndCalificadorId(UUID pedidoId, UUID calificadorId);
}

