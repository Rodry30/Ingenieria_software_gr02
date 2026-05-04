package com.ProyectoMercado.grupo2.agricultores.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.GeometryFactory;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
@Table(name = "agricultores")
public class AgricultorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "usuario_id", nullable = false)
    private UUID usuarioId;

    @Column(name = "nombre_finca")
    private String nombreFinca;

    @Column(name = "hectareas", precision = 10, scale = 2)
    private BigDecimal hectareas;

    @Column(name = "tipo_cultivo_principal")
    private String tipoCultivoPrincipal;

    @Column(name = "ubicacion_parcela", columnDefinition = "POINT")
    private String ubicacionParcela; // ver nota abajo

    @Column(name = "direccion_parcela")
    private String direccionParcela;

    @Column(name = "latitud", precision = 10, scale = 7)
    private BigDecimal latitud;

    @Column(name = "longitud", precision = 10, scale = 7)
    private BigDecimal longitud;

    @Column(name = "ruc")
    private String ruc;

    @Column(name = "cuenta_bancaria")
    private String cuentaBancaria;

    @Column(name = "banco")
    private String banco;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}