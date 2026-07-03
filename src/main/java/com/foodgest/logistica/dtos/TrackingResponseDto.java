package com.foodgest.logistica.dtos;

import com.foodgest.logistica.entities.TrackingPedido;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class TrackingResponseDto {

    private UUID id;
    private UUID pedidoId;
    private UUID transportistaId;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private BigDecimal velocidadKmh;
    private BigDecimal distanciaRestanteKm;
    private OffsetDateTime eta;
    private String descripcion;
    private OffsetDateTime createdAt;

    public static TrackingResponseDto from(TrackingPedido tracking) {
        TrackingResponseDto dto = new TrackingResponseDto();
        dto.id = tracking.getId();
        dto.pedidoId = tracking.getPedido().getId();
        dto.transportistaId = tracking.getTransportista().getId();
        dto.latitud = tracking.getLatitud();
        dto.longitud = tracking.getLongitud();
        dto.velocidadKmh = tracking.getVelocidadKmh();
        dto.distanciaRestanteKm = tracking.getDistanciaRestanteKm();
        dto.eta = tracking.getEta();
        dto.descripcion = tracking.getDescripcion();
        dto.createdAt = tracking.getCreatedAt();
        return dto;
    }

    public UUID getId() { return id; }
    public UUID getPedidoId() { return pedidoId; }
    public UUID getTransportistaId() { return transportistaId; }
    public BigDecimal getLatitud() { return latitud; }
    public BigDecimal getLongitud() { return longitud; }
    public BigDecimal getVelocidadKmh() { return velocidadKmh; }
    public BigDecimal getDistanciaRestanteKm() { return distanciaRestanteKm; }
    public OffsetDateTime getEta() { return eta; }
    public String getDescripcion() { return descripcion; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
}

