package com.foodgest.perfiles.compradores.repositories;

import com.foodgest.perfiles.compradores.entities.CompradorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompradorRepository extends JpaRepository<CompradorEntity, UUID> {
    boolean existsByUsuarioId(UUID usuarioId);
}
