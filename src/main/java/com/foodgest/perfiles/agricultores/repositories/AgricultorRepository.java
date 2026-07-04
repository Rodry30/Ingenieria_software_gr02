package com.foodgest.perfiles.agricultores.repositories;

import com.foodgest.perfiles.agricultores.entities.Agricultor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AgricultorRepository extends JpaRepository<Agricultor, UUID> {

    // JOIN FETCH evita el N+1 que disparaba AgricultorResponseDto.from(), que
    // accede a usuario.getNombre()/getEmail() por cada fila.
    @Override
    @Query("SELECT a FROM Agricultor a JOIN FETCH a.usuario")
    List<Agricultor> findAll();

    Optional<Agricultor> findByUsuarioId(UUID usuarioId);
    boolean existsByUsuarioId(UUID usuarioId);
}

