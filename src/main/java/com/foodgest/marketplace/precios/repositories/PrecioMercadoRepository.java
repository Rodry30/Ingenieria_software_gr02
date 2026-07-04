package com.foodgest.marketplace.precios.repositories;

import com.foodgest.marketplace.precios.entities.PrecioMercado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PrecioMercadoRepository extends JpaRepository<PrecioMercado, UUID> {

    // JOIN FETCH evita el N+1 que disparaba PrecioMercadoResponseDto.from()
    // al acceder a producto.getNombre() por cada fila.
    @Override
    @Query("SELECT p FROM PrecioMercado p JOIN FETCH p.producto")
    List<PrecioMercado> findAll();

    @Query("SELECT p FROM PrecioMercado p JOIN FETCH p.producto WHERE p.producto.id = :productoId")
    List<PrecioMercado> findByProductoId(@Param("productoId") UUID productoId);

    @Query("SELECT p FROM PrecioMercado p JOIN FETCH p.producto WHERE p.producto.id = :productoId ORDER BY p.fechaPrecio DESC")
    List<PrecioMercado> findByProductoIdOrderByFechaPrecioDesc(@Param("productoId") UUID productoId);

    Optional<PrecioMercado> findByProductoIdAndFuenteAndFechaPrecio(UUID productoId, String fuente, LocalDate fechaPrecio);

    List<PrecioMercado> findByFuente(String fuente);
}
