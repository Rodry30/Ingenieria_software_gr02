package com.foodgest.marketplace.precios.repositories;

import com.foodgest.marketplace.precios.entities.PrecioMercado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PrecioMercadoRepository extends JpaRepository<PrecioMercado, UUID> {

    List<PrecioMercado> findByProductoId(UUID productoId);

    List<PrecioMercado> findByProductoIdOrderByFechaPrecioDesc(UUID productoId);

    Optional<PrecioMercado> findByProductoIdAndFuenteAndFechaPrecio(UUID productoId, String fuente, LocalDate fechaPrecio);

    List<PrecioMercado> findByFuente(String fuente);
}
