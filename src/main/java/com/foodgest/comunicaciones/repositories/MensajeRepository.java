package com.foodgest.comunicaciones.repositories;

import com.foodgest.comunicaciones.entities.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, UUID> {
    List<Mensaje> findByPedidoIdOrderByCreatedAtAsc(UUID pedidoId);
    List<Mensaje> findByRemitenteIdOrDestinatarioIdOrderByCreatedAtDesc(UUID remitenteId, UUID destinatarioId);
}

