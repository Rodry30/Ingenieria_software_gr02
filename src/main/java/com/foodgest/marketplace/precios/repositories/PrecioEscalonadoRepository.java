package com.foodgest.marketplace.precios.repositories;

import com.foodgest.marketplace.precios.entities.PrecioEscalonado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrecioEscalonadoRepository extends JpaRepository<PrecioEscalonado, UUID> {
    List<PrecioEscalonado> findByOfertaIdOrderByCantidadDesdeAsc(UUID ofertaId);
}

