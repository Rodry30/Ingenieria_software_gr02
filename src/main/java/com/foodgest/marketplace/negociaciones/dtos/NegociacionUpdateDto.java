package com.foodgest.marketplace.negociaciones.dtos;

import com.foodgest.marketplace.negociaciones.entities.Negociacion;
import com.foodgest.marketplace.negociaciones.enums.EstadoNegociacionEnum;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * DTO para que el agricultor responda a una negociacion:
 * aceptar, rechazar, contraofertar o marcar como expirada.
 * Solo los campos no-null se aplican.
 */
public class NegociacionUpdateDto {

    private EstadoNegociacionEnum estado;

    @Positive(message = "El precio acordado debe ser mayor a 0")
    private BigDecimal precioAcordado;

    @Positive(message = "La cantidad acordada debe ser mayor a 0")
    private BigDecimal cantidadAcordada;

    private OffsetDateTime fechaRespuesta;

    public void applyTo(Negociacion n) {
        if (this.estado != null)          n.setEstado(this.estado.name());
        if (this.precioAcordado != null)  n.setPrecioAcordado(this.precioAcordado);
        if (this.cantidadAcordada != null) n.setCantidadAcordada(this.cantidadAcordada);
        if (this.fechaRespuesta != null)  n.setFechaRespuesta(this.fechaRespuesta);
    }

    public EstadoNegociacionEnum getEstado() { return estado; }
    public void setEstado(EstadoNegociacionEnum estado) { this.estado = estado; }
    public BigDecimal getPrecioAcordado() { return precioAcordado; }
    public void setPrecioAcordado(BigDecimal precioAcordado) { this.precioAcordado = precioAcordado; }
    public BigDecimal getCantidadAcordada() { return cantidadAcordada; }
    public void setCantidadAcordada(BigDecimal cantidadAcordada) { this.cantidadAcordada = cantidadAcordada; }
    public OffsetDateTime getFechaRespuesta() { return fechaRespuesta; }
    public void setFechaRespuesta(OffsetDateTime fechaRespuesta) { this.fechaRespuesta = fechaRespuesta; }
}
