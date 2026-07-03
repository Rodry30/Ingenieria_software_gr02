package com.foodgest.pedidos.repositories;

import com.foodgest.pedidos.entities.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, UUID> {
    List<DetallePedido> findByPedidoId(UUID pedidoId);
    List<DetallePedido> findByAgricultorId(UUID agricultorId);
}

