package com.foodgest.subastas.subastas.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class SubastaCreateDto {

    @NotNull(message = "El producto_id es obligatorio")
    private UUID productoId;

    @NotNull(message = "El agricultor_id es obligatorio")
    private UUID agricultorId;

    @NotNull(message = "La cantidad del lote es obligatoria")
    @Positive(message = "La cantidad del lote debe ser mayor a 0")
    private BigDecimal cantidadLote;

    @NotNull(message = "El precio base es obligatorio")
    @Positive(message = "El precio base debe ser mayor a 0")
    private BigDecimal precioBase;

    @Positive(message = "El incremento minimo debe ser mayor a 0")
    private BigDecimal incrementoMinimo = new BigDecimal("10.00");

    @NotNull(message = "La fecha de inicio es obligatoria")
    private OffsetDateTime fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private OffsetDateTime fechaFin;

    public UUID getProductoId() { return productoId; }
    public void setProductoId(UUID productoId) { this.productoId = productoId; }
    public UUID getAgricultorId() { return agricultorId; }
    public void setAgricultorId(UUID agricultorId) { this.agricultorId = agricultorId; }
    public BigDecimal getCantidadLote() { return cantidadLote; }
    public void setCantidadLote(BigDecimal cantidadLote) { this.cantidadLote = cantidadLote; }
    public BigDecimal getPrecioBase() { return precioBase; }
    public void setPrecioBase(BigDecimal precioBase) { this.precioBase = precioBase; }
    public BigDecimal getIncrementoMinimo() { return incrementoMinimo; }
    public void setIncrementoMinimo(BigDecimal incrementoMinimo) { this.incrementoMinimo = incrementoMinimo; }
    public OffsetDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(OffsetDateTime fechaInicio) { this.fechaInicio = fechaInicio; }
    public OffsetDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(OffsetDateTime fechaFin) { this.fechaFin = fechaFin; }
}

