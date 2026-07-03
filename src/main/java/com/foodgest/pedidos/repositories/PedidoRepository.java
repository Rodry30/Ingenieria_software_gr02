package com.foodgest.pedidos.repositories;

import com.foodgest.pedidos.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
    List<Pedido> findByCompradorId(UUID compradorId);
    List<Pedido> findByOfertaAgricultorId(UUID agricultorId);
    Optional<Pedido> findByCodigoSeguimiento(String codigoSeguimiento);
}

