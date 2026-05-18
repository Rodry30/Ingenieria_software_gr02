package com.foodgest.perfiles.compradores.repositories;

import com.foodgest.perfiles.compradores.entities.Comprador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompradorRepository extends JpaRepository<Comprador, UUID> {
    Optional<Comprador> findByUsuarioId(UUID usuarioId);
    boolean existsByUsuarioId(UUID usuarioId);
}
