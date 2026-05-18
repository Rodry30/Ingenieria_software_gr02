package com.foodgest.marketplace.ofertas.dtos;

import com.foodgest.marketplace.ofertas.entities.Oferta;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class OfertaResponseDto {

    private UUID id;
    private UUID agricultorId;
    private UUID productoId;
    private String productoNombre;
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
    private String estado;
    private LocalDate fechaCosecha;
    private OffsetDateTime fechaExpiracion;
    private Boolean aceptaNegociacion;
    private BigDecimal precioMinimo;
    private Boolean tieneSenasa;
    private String numeroCertSenasa;
    private LocalDate vencimientoCertSenasa;
    private Boolean tieneGlobalgap;
    private String numeroCertGlobalgap;
    private LocalDate vencimientoGlobalgap;
    private Boolean esOrganico;
    private String numeroCertOrganico;
    private LocalDate vencimientoCertOrganico;
    private String fotosUrls;
    private Integer vistas;
    private Boolean destacada;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public static OfertaResponseDto from(Oferta o) {
        OfertaResponseDto dto = new OfertaResponseDto();
        dto.id                   = o.getId();
        dto.agricultorId         = o.getAgricultor().getId();
        dto.productoId           = o.getProducto().getId();
        dto.productoNombre       = o.getProducto().getNombre();
        dto.variedad             = o.getVariedad();
        dto.calidad              = o.getCalidad();
        dto.cantidadDisponible   = o.getCantidadDisponible();
        dto.unidadMedida         = o.getUnidadMedida();
        dto.precioSugerido       = o.getPrecioSugerido();
        dto.moneda               = o.getMoneda();
        dto.latitud              = o.getLatitud();
        dto.longitud             = o.getLongitud();
        dto.direccionReferencia  = o.getDireccionReferencia();
        dto.radioEntregaKm       = o.getRadioEntregaKm();
        dto.condicionEntrega     = o.getCondicionEntrega();
        dto.estado               = o.getEstado();
        dto.fechaCosecha         = o.getFechaCosecha();
        dto.fechaExpiracion      = o.getFechaExpiracion();
        dto.aceptaNegociacion    = o.getAceptaNegociacion();
        dto.precioMinimo         = o.getPrecioMinimo();
        dto.tieneSenasa          = o.getTieneSenasa();
        dto.numeroCertSenasa     = o.getNumeroCertSenasa();
        dto.vencimientoCertSenasa = o.getVencimientoCertSenasa();
        dto.tieneGlobalgap       = o.getTieneGlobalgap();
        dto.numeroCertGlobalgap  = o.getNumeroCertGlobalgap();
        dto.vencimientoGlobalgap = o.getVencimientoGlobalgap();
        dto.esOrganico           = o.getEsOrganico();
        dto.numeroCertOrganico   = o.getNumeroCertOrganico();
        dto.vencimientoCertOrganico = o.getVencimientoCertOrganico();
        dto.fotosUrls            = o.getFotosUrls();
        dto.vistas               = o.getVistas();
        dto.destacada            = o.getDestacada();
        dto.createdAt            = o.getCreatedAt();
        dto.updatedAt            = o.getUpdatedAt();
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
    public String getVariedad() { return variedad; }
    public void setVariedad(String variedad) { this.variedad = variedad; }
    public String getCalidad() { return calidad; }
    public void setCalidad(String calidad) { this.calidad = calidad; }
    public BigDecimal getCantidadDisponible() { return cantidadDisponible; }
    public void setCantidadDisponible(BigDecimal c) { this.cantidadDisponible = c; }
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
    public void setDireccionReferencia(String d) { this.direccionReferencia = d; }
    public Integer getRadioEntregaKm() { return radioEntregaKm; }
    public void setRadioEntregaKm(Integer radioEntregaKm) { this.radioEntregaKm = radioEntregaKm; }
    public String getCondicionEntrega() { return condicionEntrega; }
    public void setCondicionEntrega(String condicionEntrega) { this.condicionEntrega = condicionEntrega; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDate getFechaCosecha() { return fechaCosecha; }
    public void setFechaCosecha(LocalDate fechaCosecha) { this.fechaCosecha = fechaCosecha; }
    public OffsetDateTime getFechaExpiracion() { return fechaExpiracion; }
    public void setFechaExpiracion(OffsetDateTime fechaExpiracion) { this.fechaExpiracion = fechaExpiracion; }
    public Boolean getAceptaNegociacion() { return aceptaNegociacion; }
    public void setAceptaNegociacion(Boolean aceptaNegociacion) { this.aceptaNegociacion = aceptaNegociacion; }
    public BigDecimal getPrecioMinimo() { return precioMinimo; }
    public void setPrecioMinimo(BigDecimal precioMinimo) { this.precioMinimo = precioMinimo; }
    public Boolean getTieneSenasa() { return tieneSenasa; }
    public void setTieneSenasa(Boolean tieneSenasa) { this.tieneSenasa = tieneSenasa; }
    public String getNumeroCertSenasa() { return numeroCertSenasa; }
    public void setNumeroCertSenasa(String n) { this.numeroCertSenasa = n; }
    public LocalDate getVencimientoCertSenasa() { return vencimientoCertSenasa; }
    public void setVencimientoCertSenasa(LocalDate v) { this.vencimientoCertSenasa = v; }
    public Boolean getTieneGlobalgap() { return tieneGlobalgap; }
    public void setTieneGlobalgap(Boolean tieneGlobalgap) { this.tieneGlobalgap = tieneGlobalgap; }
    public String getNumeroCertGlobalgap() { return numeroCertGlobalgap; }
    public void setNumeroCertGlobalgap(String n) { this.numeroCertGlobalgap = n; }
    public LocalDate getVencimientoGlobalgap() { return vencimientoGlobalgap; }
    public void setVencimientoGlobalgap(LocalDate v) { this.vencimientoGlobalgap = v; }
    public Boolean getEsOrganico() { return esOrganico; }
    public void setEsOrganico(Boolean esOrganico) { this.esOrganico = esOrganico; }
    public String getNumeroCertOrganico() { return numeroCertOrganico; }
    public void setNumeroCertOrganico(String n) { this.numeroCertOrganico = n; }
    public LocalDate getVencimientoCertOrganico() { return vencimientoCertOrganico; }
    public void setVencimientoCertOrganico(LocalDate v) { this.vencimientoCertOrganico = v; }
    public String getFotosUrls() { return fotosUrls; }
    public void setFotosUrls(String fotosUrls) { this.fotosUrls = fotosUrls; }
    public Integer getVistas() { return vistas; }
    public void setVistas(Integer vistas) { this.vistas = vistas; }
    public Boolean getDestacada() { return destacada; }
    public void setDestacada(Boolean destacada) { this.destacada = destacada; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
