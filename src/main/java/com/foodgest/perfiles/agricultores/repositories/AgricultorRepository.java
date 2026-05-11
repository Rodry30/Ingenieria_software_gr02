package com.foodgest.perfiles.agricultores.repositories;

import com.foodgest.perfiles.agricultores.entities.AgricultorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AgricultorRepository extends JpaRepository<AgricultorEntity, UUID> {
    boolean existsByUsuarioId(UUID usuarioId);
}

