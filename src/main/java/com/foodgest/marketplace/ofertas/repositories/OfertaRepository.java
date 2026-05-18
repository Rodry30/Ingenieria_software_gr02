package com.foodgest.marketplace.ofertas.repositories;

import com.foodgest.marketplace.ofertas.entities.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, UUID> {

    List<Oferta> findByAgricultorId(UUID agricultorId);

    List<Oferta> findByProductoId(UUID productoId);

    List<Oferta> findByEstado(String estado);

    List<Oferta> findByAgricultorIdAndEstado(UUID agricultorId, String estado);

    @Modifying
    @Transactional
    @Query("UPDATE Oferta o SET o.vistas = o.vistas + 1 WHERE o.id = :id")
    void incrementarVistas(@Param("id") UUID id);
}
