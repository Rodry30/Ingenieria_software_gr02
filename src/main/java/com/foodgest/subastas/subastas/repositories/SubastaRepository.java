package com.foodgest.subastas.subastas.repositories;

import com.foodgest.subastas.subastas.entities.Subasta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubastaRepository extends JpaRepository<Subasta, UUID> {
    List<Subasta> findByEstado(String estado);
    List<Subasta> findByAgricultorId(UUID agricultorId);
    List<Subasta> findByProductoId(UUID productoId);
}

