package com.foodgest.pedidos.repositories;

import com.foodgest.pedidos.entities.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, UUID> {
    List<Transaccion> findByPedidoIdOrderByFechaTransaccionDesc(UUID pedidoId);
    Optional<Transaccion> findByReferenciaExterna(String referenciaExterna);
}

