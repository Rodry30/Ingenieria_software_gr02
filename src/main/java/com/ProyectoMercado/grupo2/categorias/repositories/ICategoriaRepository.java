package com.ProyectoMercado.grupo2.categorias.repositories;

import com.ProyectoMercado.grupo2.categorias.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, UUID> {
}
