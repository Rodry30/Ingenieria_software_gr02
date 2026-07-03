package com.foodgest.subastas.suscripciones.dtos;

import com.foodgest.subastas.suscripciones.enums.FrecuenciaEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class SuscripcionCreateDto {

    @NotNull(message = "El comprador_id es obligatorio")
    private UUID compradorId;

    @NotNull(message = "El agricultor_id es obligatorio")
    private UUID agricultorId;

    @NotNull(message = "El producto_id es obligatorio")
    private UUID productoId;

    @NotNull(message = "La cantidad periodica es obligatoria")
    @Positive(message = "La cantidad periodica debe ser mayor a 0")
    private BigDecimal cantidadPeriodica;

    @NotNull(message = "La frecuencia es obligatoria")
    private FrecuenciaEnum frecuencia = FrecuenciaEnum.mensual;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    public UUID getCompradorId() { return compradorId; }
    public void setCompradorId(UUID compradorId) { this.compradorId = compradorId; }
    public UUID getAgricultorId() { return agricultorId; }
    public void setAgricultorId(UUID agricultorId) { this.agricultorId = agricultorId; }
    public UUID getProductoId() { return productoId; }
    public void setProductoId(UUID productoId) { this.productoId = productoId; }
    public BigDecimal getCantidadPeriodica() { return cantidadPeriodica; }
    public void setCantidadPeriodica(BigDecimal cantidadPeriodica) { this.cantidadPeriodica = cantidadPeriodica; }
    public FrecuenciaEnum getFrecuencia() { return frecuencia; }
    public void setFrecuencia(FrecuenciaEnum frecuencia) { this.frecuencia = frecuencia; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
}

