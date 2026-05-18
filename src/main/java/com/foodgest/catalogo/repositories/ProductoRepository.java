package com.foodgest.catalogo.repositories;

import com.foodgest.catalogo.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, UUID> {
    List<Producto> findByCategoriaId(UUID categoriaId);
}
