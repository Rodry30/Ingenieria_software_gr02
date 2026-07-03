package com.foodgest.subastas.subastas.dtos;

import com.foodgest.subastas.subastas.entities.Subasta;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class SubastaResponseDto {

    private UUID id;
    private UUID productoId;
    private UUID agricultorId;
    private BigDecimal cantidadLote;
    private BigDecimal precioBase;
    private BigDecimal precioActual;
    private BigDecimal incrementoMinimo;
    private OffsetDateTime fechaInicio;
    private OffsetDateTime fechaFin;
    private String estado;
    private UUID ganadorId;
    private OffsetDateTime createdAt;

    public static SubastaResponseDto from(Subasta subasta) {
        SubastaResponseDto dto = new SubastaResponseDto();
        dto.id = subasta.getId();
        dto.productoId = subasta.getProducto().getId();
        dto.agricultorId = subasta.getAgricultor().getId();
        dto.cantidadLote = subasta.getCantidadLote();
        dto.precioBase = subasta.getPrecioBase();
        dto.precioActual = subasta.getPrecioActual();
        dto.incrementoMinimo = subasta.getIncrementoMinimo();
        dto.fechaInicio = subasta.getFechaInicio();
        dto.fechaFin = subasta.getFechaFin();
        dto.estado = subasta.getEstado();
        dto.ganadorId = subasta.getGanador() != null ? subasta.getGanador().getId() : null;
        dto.createdAt = subasta.getCreatedAt();
        return dto;
    }

    public UUID getId() { return id; }
    public UUID getProductoId() { return productoId; }
    public UUID getAgricultorId() { return agricultorId; }
    public BigDecimal getCantidadLote() { return cantidadLote; }
    public BigDecimal getPrecioBase() { return precioBase; }
    public BigDecimal getPrecioActual() { return precioActual; }
    public BigDecimal getIncrementoMinimo() { return incrementoMinimo; }
    public OffsetDateTime getFechaInicio() { return fechaInicio; }
    public OffsetDateTime getFechaFin() { return fechaFin; }
    public String getEstado() { return estado; }
    public UUID getGanadorId() { return ganadorId; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
}

