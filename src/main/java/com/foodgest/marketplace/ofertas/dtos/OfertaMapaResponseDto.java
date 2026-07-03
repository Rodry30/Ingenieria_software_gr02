package com.foodgest.marketplace.ofertas.dtos;

import com.foodgest.marketplace.ofertas.repositories.OfertaMapaProjection;

import java.math.BigDecimal;
import java.util.UUID;

public class OfertaMapaResponseDto {

    private UUID id;
    private UUID agricultorId;
    private UUID productoId;
    private String productoNombre;
    private String agricultorNombre;
    private String nombreFinca;
    private String variedad;
    private String calidad;
    private BigDecimal cantidadDisponible;
    private String unidadMedida;
    private BigDecimal precioSugerido;
    private String moneda;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private String direccionReferencia;
    private Integer radioEntregaKm;
    private String condicionEntrega;
    private Boolean aceptaNegociacion;
    private Boolean tieneSenasa;
    private Boolean tieneGlobalgap;
    private Boolean esOrganico;
    private Boolean destacada;
    private Double distanciaKm;

    public static OfertaMapaResponseDto from(OfertaMapaProjection projection) {
        OfertaMapaResponseDto dto = new OfertaMapaResponseDto();
        dto.id = projection.getId();
        dto.agricultorId = projection.getAgricultorId();
        dto.productoId = projection.getProductoId();
        dto.productoNombre = projection.getProductoNombre();
        dto.agricultorNombre = projection.getAgricultorNombre();
        dto.nombreFinca = projection.getNombreFinca();
        dto.variedad = projection.getVariedad();
        dto.calidad = projection.getCalidad();
        dto.cantidadDisponible = projection.getCantidadDisponible();
        dto.unidadMedida = projection.getUnidadMedida();
        dto.precioSugerido = projection.getPrecioSugerido();
        dto.moneda = projection.getMoneda();
        dto.latitud = projection.getLatitud();
        dto.longitud = projection.getLongitud();
        dto.direccionReferencia = projection.getDireccionReferencia();
        dto.radioEntregaKm = projection.getRadioEntregaKm();
        dto.condicionEntrega = projection.getCondicionEntrega();
        dto.aceptaNegociacion = projection.getAceptaNegociacion();
        dto.tieneSenasa = projection.getTieneSenasa();
        dto.tieneGlobalgap = projection.getTieneGlobalgap();
        dto.esOrganico = projection.getEsOrganico();
        dto.destacada = projection.getDestacada();
        dto.distanciaKm = projection.getDistanciaKm();
        return dto;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getAgricultorId() { return agricultorId; }
    public void setAgricultorId(UUID agricultorId) { this.agricultorId = agricultorId; }
    public UUID getProductoId() { return productoId; }
    public void setProductoId(UUID productoId) { this.productoId = productoId; }
    public String getProductoNombre() { return productoNombre; }
    public void setProductoNombre(String productoNombre) { this.productoNombre = productoNombre; }
    public String getAgricultorNombre() { return agricultorNombre; }
    public void setAgricultorNombre(String agricultorNombre) { this.agricultorNombre = agricultorNombre; }
    public String getNombreFinca() { return nombreFinca; }
    public void setNombreFinca(String nombreFinca) { this.nombreFinca = nombreFinca; }
    public String getVariedad() { return variedad; }
    public void setVariedad(String variedad) { this.variedad = variedad; }
    public String getCalidad() { return calidad; }
    public void setCalidad(String calidad) { this.calidad = calidad; }
    public BigDecimal getCantidadDisponible() { return cantidadDisponible; }
    public void setCantidadDisponible(BigDecimal cantidadDisponible) { this.cantidadDisponible = cantidadDisponible; }
    public String getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(String unidadMedida) { this.unidadMedida = unidadMedida; }
    public BigDecimal getPrecioSugerido() { return precioSugerido; }
    public void setPrecioSugerido(BigDecimal precioSugerido) { this.precioSugerido = precioSugerido; }
    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
    public BigDecimal getLatitud() { return latitud; }
    public void setLatitud(BigDecimal latitud) { this.latitud = latitud; }
    public BigDecimal getLongitud() { return longitud; }
    public void setLongitud(BigDecimal longitud) { this.longitud = longitud; }
    public String getDireccionReferencia() { return direccionReferencia; }
    public void setDireccionReferencia(String direccionReferencia) { this.direccionReferencia = direccionReferencia; }
    public Integer getRadioEntregaKm() { return radioEntregaKm; }
    public void setRadioEntregaKm(Integer radioEntregaKm) { this.radioEntregaKm = radioEntregaKm; }
    public String getCondicionEntrega() { return condicionEntrega; }
    public void setCondicionEntrega(String condicionEntrega) { this.condicionEntrega = condicionEntrega; }
    public Boolean getAceptaNegociacion() { return aceptaNegociacion; }
    public void setAceptaNegociacion(Boolean aceptaNegociacion) { this.aceptaNegociacion = aceptaNegociacion; }
    public Boolean getTieneSenasa() { return tieneSenasa; }
    public void setTieneSenasa(Boolean tieneSenasa) { this.tieneSenasa = tieneSenasa; }
    public Boolean getTieneGlobalgap() { return tieneGlobalgap; }
    public void setTieneGlobalgap(Boolean tieneGlobalgap) { this.tieneGlobalgap = tieneGlobalgap; }
    public Boolean getEsOrganico() { return esOrganico; }
    public void setEsOrganico(Boolean esOrganico) { this.esOrganico = esOrganico; }
    public Boolean getDestacada() { return destacada; }
    public void setDestacada(Boolean destacada) { this.destacada = destacada; }
    public Double getDistanciaKm() { return distanciaKm; }
    public void setDistanciaKm(Double distanciaKm) { this.distanciaKm = distanciaKm; }
}

