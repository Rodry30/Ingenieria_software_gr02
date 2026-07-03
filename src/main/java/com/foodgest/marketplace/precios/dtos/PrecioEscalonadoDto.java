package com.foodgest.marketplace.precios.dtos;

import com.foodgest.marketplace.precios.entities.PrecioEscalonado;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public class PrecioEscalonadoDto {

    private UUID id;

    @NotNull(message = "La oferta_id es obligatoria")
    private UUID ofertaId;

    @NotNull(message = "La cantidad desde es obligatoria")
    @Positive(message = "La cantidad desde debe ser mayor a 0")
    private BigDecimal cantidadDesde;

    @Positive(message = "La cantidad hasta debe ser mayor a 0")
    private BigDecimal cantidadHasta;

    @NotNull(message = "El precio unitario es obligatorio")
    @Positive(message = "El precio unitario debe ser mayor a 0")
    private BigDecimal precioUnitario;

    private String moneda = "PEN";

    public static PrecioEscalonadoDto from(PrecioEscalonado precio) {
        PrecioEscalonadoDto dto = new PrecioEscalonadoDto();
        dto.id = precio.getId();
        dto.ofertaId = precio.getOferta().getId();
        dto.cantidadDesde = precio.getCantidadDesde();
        dto.cantidadHasta = precio.getCantidadHasta();
        dto.precioUnitario = precio.getPrecioUnitario();
        dto.moneda = precio.getMoneda();
        return dto;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getOfertaId() { return ofertaId; }
    public void setOfertaId(UUID ofertaId) { this.ofertaId = ofertaId; }
    public BigDecimal getCantidadDesde() { return cantidadDesde; }
    public void setCantidadDesde(BigDecimal cantidadDesde) { this.cantidadDesde = cantidadDesde; }
    public BigDecimal getCantidadHasta() { return cantidadHasta; }
    public void setCantidadHasta(BigDecimal cantidadHasta) { this.cantidadHasta = cantidadHasta; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
}

