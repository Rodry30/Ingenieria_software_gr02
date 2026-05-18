package com.foodgest.perfiles.agricultores.repositories;

import com.foodgest.perfiles.agricultores.entities.Agricultor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AgricultorRepository extends JpaRepository<Agricultor, UUID> {
    Optional<Agricultor> findByUsuarioId(UUID usuarioId);
    boolean existsByUsuarioId(UUID usuarioId);
}

