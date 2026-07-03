package com.foodgest.marketplace.ofertas.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public class OfertaFiltroDto {

    private UUID productoId;
    private UUID agricultorId;
    private String estado;
    private String calidad;
    private BigDecimal precioMin;
    private BigDecimal precioMax;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private BigDecimal radioKm;
    private Boolean tieneSenasa;
    private Boolean esOrganico;

    public UUID getProductoId() { return productoId; }
    public void setProductoId(UUID productoId) { this.productoId = productoId; }
    public UUID getAgricultorId() { return agricultorId; }
    public void setAgricultorId(UUID agricultorId) { this.agricultorId = agricultorId; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getCalidad() { return calidad; }
    public void setCalidad(String calidad) { this.calidad = calidad; }
    public BigDecimal getPrecioMin() { return precioMin; }
    public void setPrecioMin(BigDecimal precioMin) { this.precioMin = precioMin; }
    public BigDecimal getPrecioMax() { return precioMax; }
    public void setPrecioMax(BigDecimal precioMax) { this.precioMax = precioMax; }
    public BigDecimal getLatitud() { return latitud; }
    public void setLatitud(BigDecimal latitud) { this.latitud = latitud; }
    public BigDecimal getLongitud() { return longitud; }
    public void setLongitud(BigDecimal longitud) { this.longitud = longitud; }
    public BigDecimal getRadioKm() { return radioKm; }
    public void setRadioKm(BigDecimal radioKm) { this.radioKm = radioKm; }
    public Boolean getTieneSenasa() { return tieneSenasa; }
    public void setTieneSenasa(Boolean tieneSenasa) { this.tieneSenasa = tieneSenasa; }
    public Boolean getEsOrganico() { return esOrganico; }
    public void setEsOrganico(Boolean esOrganico) { this.esOrganico = esOrganico; }
}

