package com.foodgest.marketplace.negociaciones.dtos;

import com.foodgest.marketplace.negociaciones.enums.EstadoNegociacionEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class NegociacionRespuestaDto {

    @NotNull(message = "El estado es obligatorio")
    private EstadoNegociacionEnum estado;

    @Positive(message = "El precio acordado debe ser mayor a 0")
    private BigDecimal precioAcordado;

    @Positive(message = "La cantidad acordada debe ser mayor a 0")
    private BigDecimal cantidadAcordada;

    private String mensaje;

    public EstadoNegociacionEnum getEstado() { return estado; }
    public void setEstado(EstadoNegociacionEnum estado) { this.estado = estado; }
    public BigDecimal getPrecioAcordado() { return precioAcordado; }
    public void setPrecioAcordado(BigDecimal precioAcordado) { this.precioAcordado = precioAcordado; }
    public BigDecimal getCantidadAcordada() { return cantidadAcordada; }
    public void setCantidadAcordada(BigDecimal cantidadAcordada) { this.cantidadAcordada = cantidadAcordada; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}

