package com.foodgest.comunicaciones.repositories;

import com.foodgest.comunicaciones.entities.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, UUID> {
    List<Notificacion> findByUsuarioIdOrderByCreatedAtDesc(UUID usuarioId);
    List<Notificacion> findByUsuarioIdAndLeidoOrderByCreatedAtDesc(UUID usuarioId, Boolean leido);
}

