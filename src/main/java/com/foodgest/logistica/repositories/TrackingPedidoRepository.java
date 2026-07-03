package com.foodgest.logistica.repositories;

import com.foodgest.logistica.entities.TrackingPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrackingPedidoRepository extends JpaRepository<TrackingPedido, UUID> {
    List<TrackingPedido> findByPedidoIdOrderByCreatedAtDesc(UUID pedidoId);
    Optional<TrackingPedido> findFirstByPedidoIdOrderByCreatedAtDesc(UUID pedidoId);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE tracking_pedido
            SET ubicacion = ST_SetSRID(ST_MakePoint(longitud::double precision, latitud::double precision), 4326)::geography
            WHERE id = :id
            """, nativeQuery = true)
    void sincronizarUbicacion(@Param("id") UUID id);

    @Query(value = "SELECT ST_Distance(" +
            "ST_SetSRID(ST_MakePoint(CAST(:lng1 AS double precision), CAST(:lat1 AS double precision)), 4326)::geography, " +
            "ST_SetSRID(ST_MakePoint(CAST(:lng2 AS double precision), CAST(:lat2 AS double precision)), 4326)::geography" +
            ")", nativeQuery = true)
    double calcularDistanciaMetros(@Param("lat1") double lat1, @Param("lng1") double lng1,
                                  @Param("lat2") double lat2, @Param("lng2") double lng2);
}

