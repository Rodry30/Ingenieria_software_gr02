package com.foodgest.marketplace.precios.dtos;

import com.foodgest.marketplace.precios.entities.PrecioMercado;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class PrecioMercadoResponseDto {

    private UUID id;
    private UUID productoId;
    private String productoNombre;
    private String fuente;
    private BigDecimal precioMin;
    private BigDecimal precioMax;
    private BigDecimal precioPromedio;
    private BigDecimal variacionPorcentaje;
    private String tendencia;
    private LocalDate fechaPrecio;
    private OffsetDateTime createdAt;

    public static PrecioMercadoResponseDto from(PrecioMercado pm) {
        PrecioMercadoResponseDto dto = new PrecioMercadoResponseDto();
        dto.id                  = pm.getId();
        dto.productoId          = pm.getProducto().getId();
        dto.productoNombre      = pm.getProducto().getNombre();
        dto.fuente              = pm.getFuente();
        dto.precioMin           = pm.getPrecioMin();
        dto.precioMax           = pm.getPrecioMax();
        dto.precioPromedio      = pm.getPrecioPromedio();
        dto.variacionPorcentaje = pm.getVariacionPorcentaje();
        dto.tendencia           = pm.getTendencia();
        dto.fechaPrecio         = pm.getFechaPrecio();
        dto.createdAt           = pm.getCreatedAt();
        return dto;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getProductoId() { return productoId; }
    public void setProductoId(UUID productoId) { this.productoId = productoId; }
    public String getProductoNombre() { return productoNombre; }
    public void setProductoNombre(String productoNombre) { this.productoNombre = productoNombre; }
    public String getFuente() { return fuente; }
    public void setFuente(String fuente) { this.fuente = fuente; }
    public BigDecimal getPrecioMin() { return precioMin; }
    public void setPrecioMin(BigDecimal precioMin) { this.precioMin = precioMin; }
    public BigDecimal getPrecioMax() { return precioMax; }
    public void setPrecioMax(BigDecimal precioMax) { this.precioMax = precioMax; }
    public BigDecimal getPrecioPromedio() { return precioPromedio; }
    public void setPrecioPromedio(BigDecimal precioPromedio) { this.precioPromedio = precioPromedio; }
    public BigDecimal getVariacionPorcentaje() { return variacionPorcentaje; }
    public void setVariacionPorcentaje(BigDecimal variacionPorcentaje) { this.variacionPorcentaje = variacionPorcentaje; }
    public String getTendencia() { return tendencia; }
    public void setTendencia(String tendencia) { this.tendencia = tendencia; }
    public LocalDate getFechaPrecio() { return fechaPrecio; }
    public void setFechaPrecio(LocalDate fechaPrecio) { this.fechaPrecio = fechaPrecio; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
