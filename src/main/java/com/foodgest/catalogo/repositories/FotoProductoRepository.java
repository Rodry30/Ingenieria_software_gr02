package com.foodgest.catalogo.repositories;

import com.foodgest.catalogo.entities.FotoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FotoProductoRepository extends JpaRepository<FotoProducto, UUID> {
    List<FotoProducto> findByProductoId(UUID productoId);
}
