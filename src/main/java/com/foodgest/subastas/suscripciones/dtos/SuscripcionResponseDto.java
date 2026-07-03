package com.foodgest.subastas.suscripciones.dtos;

import com.foodgest.subastas.suscripciones.entities.Suscripcion;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class SuscripcionResponseDto {

    private UUID id;
    private UUID compradorId;
    private UUID agricultorId;
    private UUID productoId;
    private String productoNombre;
    private BigDecimal cantidadPeriodica;
    private String frecuencia;
    private String estado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private OffsetDateTime createdAt;

    public static SuscripcionResponseDto from(Suscripcion suscripcion) {
        SuscripcionResponseDto dto = new SuscripcionResponseDto();
        dto.id = suscripcion.getId();
        dto.compradorId = suscripcion.getComprador().getId();
        dto.agricultorId = suscripcion.getAgricultor().getId();
        dto.productoId = suscripcion.getProducto().getId();
        dto.productoNombre = suscripcion.getProducto().getNombre();
        dto.cantidadPeriodica = suscripcion.getCantidadPeriodica();
        dto.frecuencia = suscripcion.getFrecuencia();
        dto.estado = suscripcion.getEstado();
        dto.fechaInicio = suscripcion.getFechaInicio();
        dto.fechaFin = suscripcion.getFechaFin();
        dto.createdAt = suscripcion.getCreatedAt();
        return dto;
    }

    public UUID getId() { return id; }
    public UUID getCompradorId() { return compradorId; }
    public UUID getAgricultorId() { return agricultorId; }
    public UUID getProductoId() { return productoId; }
    public String getProductoNombre() { return productoNombre; }
    public BigDecimal getCantidadPeriodica() { return cantidadPeriodica; }
    public String getFrecuencia() { return frecuencia; }
    public String getEstado() { return estado; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
}

