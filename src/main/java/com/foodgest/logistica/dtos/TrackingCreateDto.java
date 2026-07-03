package com.foodgest.logistica.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class TrackingCreateDto {

    @NotNull(message = "El pedido_id es obligatorio")
    private UUID pedidoId;

    @NotNull(message = "El transportista_id es obligatorio")
    private UUID transportistaId;

    @NotNull(message = "La latitud es obligatoria")
    private BigDecimal latitud;

    @NotNull(message = "La longitud es obligatoria")
    private BigDecimal longitud;

    @PositiveOrZero(message = "La velocidad no puede ser negativa")
    private BigDecimal velocidadKmh;

    @PositiveOrZero(message = "La distancia restante no puede ser negativa")
    private BigDecimal distanciaRestanteKm;

    private OffsetDateTime eta;

    @Size(max = 100, message = "La descripcion no puede superar los 100 caracteres")
    private String descripcion;

    public UUID getPedidoId() { return pedidoId; }
    public void setPedidoId(UUID pedidoId) { this.pedidoId = pedidoId; }
    public UUID getTransportistaId() { return transportistaId; }
    public void setTransportistaId(UUID transportistaId) { this.transportistaId = transportistaId; }
    public BigDecimal getLatitud() { return latitud; }
    public void setLatitud(BigDecimal latitud) { this.latitud = latitud; }
    public BigDecimal getLongitud() { return longitud; }
    public void setLongitud(BigDecimal longitud) { this.longitud = longitud; }
    public BigDecimal getVelocidadKmh() { return velocidadKmh; }
    public void setVelocidadKmh(BigDecimal velocidadKmh) { this.velocidadKmh = velocidadKmh; }
    public BigDecimal getDistanciaRestanteKm() { return distanciaRestanteKm; }
    public void setDistanciaRestanteKm(BigDecimal distanciaRestanteKm) { this.distanciaRestanteKm = distanciaRestanteKm; }
    public OffsetDateTime getEta() { return eta; }
    public void setEta(OffsetDateTime eta) { this.eta = eta; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}

