package com.foodgest.subastas.suscripciones.dtos;

import com.foodgest.subastas.suscripciones.enums.EstadoSuscripcionEnum;
import com.foodgest.subastas.suscripciones.enums.FrecuenciaEnum;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SuscripcionUpdateDto {

    @Positive(message = "La cantidad periodica debe ser mayor a 0")
    private BigDecimal cantidadPeriodica;

    private FrecuenciaEnum frecuencia;
    private EstadoSuscripcionEnum estado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public BigDecimal getCantidadPeriodica() { return cantidadPeriodica; }
    public void setCantidadPeriodica(BigDecimal cantidadPeriodica) { this.cantidadPeriodica = cantidadPeriodica; }
    public FrecuenciaEnum getFrecuencia() { return frecuencia; }
    public void setFrecuencia(FrecuenciaEnum frecuencia) { this.frecuencia = frecuencia; }
    public EstadoSuscripcionEnum getEstado() { return estado; }
    public void setEstado(EstadoSuscripcionEnum estado) { this.estado = estado; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
}

