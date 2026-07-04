package com.foodgest.marketplace.ofertas.repositories;

import com.foodgest.marketplace.ofertas.entities.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, UUID> {

    // JOIN FETCH sobre producto evita el N+1 que disparaba OfertaResponseDto.from()
    // (accede a producto.getNombre(), no solo al id) al mapear listas completas.
    @Override
    @Query("SELECT o FROM Oferta o JOIN FETCH o.producto")
    List<Oferta> findAll();

    @Query("SELECT o FROM Oferta o JOIN FETCH o.producto WHERE o.agricultor.id = :agricultorId")
    List<Oferta> findByAgricultorId(@Param("agricultorId") UUID agricultorId);

    List<Oferta> findByProductoId(UUID productoId);

    @Query("SELECT o FROM Oferta o JOIN FETCH o.producto WHERE o.estado = :estado")
    List<Oferta> findByEstado(@Param("estado") String estado);

    @Query("SELECT o FROM Oferta o JOIN FETCH o.producto WHERE o.agricultor.id = :agricultorId AND o.estado = :estado")
    List<Oferta> findByAgricultorIdAndEstado(@Param("agricultorId") UUID agricultorId, @Param("estado") String estado);

    @Query(value = """
            SELECT
                o.id AS "id",
                o.agricultor_id AS "agricultorId",
                o.producto_id AS "productoId",
                p.nombre AS "productoNombre",
                u.nombre AS "agricultorNombre",
                a.nombre_finca AS "nombreFinca",
                o.variedad AS "variedad",
                o.calidad::text AS "calidad",
                o.cantidad_disponible AS "cantidadDisponible",
                o.unidad_medida AS "unidadMedida",
                o.precio_sugerido AS "precioSugerido",
                o.moneda AS "moneda",
                ST_Y(geo.ubicacion::geometry) AS "latitud",
                ST_X(geo.ubicacion::geometry) AS "longitud",
                o.direccion_referencia AS "direccionReferencia",
                o.radio_entrega_km AS "radioEntregaKm",
                o.condicion_entrega AS "condicionEntrega",
                o.acepta_negociacion AS "aceptaNegociacion",
                o.tiene_senasa AS "tieneSenasa",
                o.tiene_globalgap AS "tieneGlobalgap",
                o.es_organico AS "esOrganico",
                o.destacada AS "destacada",
                ST_Distance(
                    geo.ubicacion,
                    ST_SetSRID(ST_MakePoint(CAST(:longitud AS double precision), CAST(:latitud AS double precision)), 4326)::geography
                ) / 1000.0 AS "distanciaKm"
            FROM ofertas o
            JOIN productos p ON o.producto_id = p.id
            JOIN agricultores a ON o.agricultor_id = a.id
            JOIN users u ON a.usuario_id = u.id
            CROSS JOIN LATERAL (
                SELECT COALESCE(
                    o.ubicacion_oferta,
                    CASE
                        WHEN o.latitud IS NOT NULL AND o.longitud IS NOT NULL
                            THEN ST_SetSRID(ST_MakePoint(o.longitud::double precision, o.latitud::double precision), 4326)::geography
                        ELSE NULL
                    END
                ) AS ubicacion
            ) geo
            WHERE o.estado = 'activa'
              AND geo.ubicacion IS NOT NULL
              AND (o.fecha_expiracion IS NULL OR o.fecha_expiracion > NOW())
              AND ST_DWithin(
                    geo.ubicacion,
                    ST_SetSRID(ST_MakePoint(CAST(:longitud AS double precision), CAST(:latitud AS double precision)), 4326)::geography,
                    CAST(:radioKm AS double precision) * 1000.0
              )
            ORDER BY "distanciaKm" ASC
            """, nativeQuery = true)
    List<OfertaMapaProjection> findOfertasMapa(@Param("latitud") BigDecimal latitud,
                                               @Param("longitud") BigDecimal longitud,
                                               @Param("radioKm") BigDecimal radioKm);

    @Modifying
    @Transactional
    @Query("UPDATE Oferta o SET o.vistas = o.vistas + 1 WHERE o.id = :id")
    void incrementarVistas(@Param("id") UUID id);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE ofertas
            SET ubicacion_oferta = CASE
                WHEN latitud IS NOT NULL AND longitud IS NOT NULL
                    THEN ST_SetSRID(ST_MakePoint(longitud::double precision, latitud::double precision), 4326)::geography
                ELSE NULL
            END
            WHERE id = :id
            """, nativeQuery = true)
    void sincronizarUbicacionOferta(@Param("id") UUID id);
}
