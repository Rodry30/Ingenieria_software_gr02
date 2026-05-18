package com.foodgest.marketplace.ofertas.dtos;

import com.foodgest.marketplace.ofertas.entities.Oferta;
import com.foodgest.marketplace.ofertas.enums.CalidadEnum;
import com.foodgest.marketplace.ofertas.enums.EstadoOfertaEnum;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * DTO para actualizar campos de una oferta existente.
 * Solo se aplican los campos que no sean null (actualización parcial).
 * No permite cambiar agricultor_id ni producto_id.
 */
public class OfertaUpdateDto {

    @Size(max = 100, message = "La variedad no puede superar los 100 caracteres")
    private String variedad;

    private CalidadEnum calidad;

    @Positive(message = "La cantidad disponible debe ser mayor a 0")
    private BigDecimal cantidadDisponible;

    @Size(max = 30, message = "La unidad de medida no puede superar los 30 caracteres")
    private String unidadMedida;

    @Positive(message = "El precio sugerido debe ser mayor a 0")
    private BigDecimal precioSugerido;

    @DecimalMin(value = "-90.0",  message = "La latitud minima es -90.0")
    @DecimalMax(value = "90.0",   message = "La latitud maxima es 90.0")
    private BigDecimal latitud;

    @DecimalMin(value = "-180.0", message = "La longitud minima es -180.0")
    @DecimalMax(value = "180.0",  message = "La longitud maxima es 180.0")
    private BigDecimal longitud;

    @Size(max = 255, message = "La direccion de referencia no puede superar los 255 caracteres")
    private String direccionReferencia;

    private Integer radioEntregaKm;

    @Size(max = 30, message = "La condicion de entrega no puede superar los 30 caracteres")
    private String condicionEntrega;

    private EstadoOfertaEnum estado;
    private LocalDate fechaCosecha;
    private OffsetDateTime fechaExpiracion;
    private Boolean aceptaNegociacion;

    @Positive(message = "El precio minimo debe ser mayor a 0")
    private BigDecimal precioMinimo;

    // Certificaciones
    private Boolean tieneSenasa;
    @Size(max = 50) private String numeroCertSenasa;
    private LocalDate vencimientoCertSenasa;

    private Boolean tieneGlobalgap;
    @Size(max = 50) private String numeroCertGlobalgap;
    private LocalDate vencimientoGlobalgap;

    private Boolean esOrganico;
    @Size(max = 50) private String numeroCertOrganico;
    private LocalDate vencimientoCertOrganico;

    private String fotosUrls;
    private Boolean destacada;

    public void applyTo(Oferta o) {
        if (this.variedad != null)            o.setVariedad(this.variedad);
        if (this.calidad != null)             o.setCalidad(this.calidad.name());
        if (this.cantidadDisponible != null)  o.setCantidadDisponible(this.cantidadDisponible);
        if (this.unidadMedida != null)        o.setUnidadMedida(this.unidadMedida);
        if (this.precioSugerido != null)      o.setPrecioSugerido(this.precioSugerido);
        if (this.latitud != null)             o.setLatitud(this.latitud);
        if (this.longitud != null)            o.setLongitud(this.longitud);
        if (this.direccionReferencia != null) o.setDireccionReferencia(this.direccionReferencia);
        if (this.radioEntregaKm != null)      o.setRadioEntregaKm(this.radioEntregaKm);
        if (this.condicionEntrega != null)    o.setCondicionEntrega(this.condicionEntrega);
        if (this.estado != null)              o.setEstado(this.estado.name());
        if (this.fechaCosecha != null)        o.setFechaCosecha(this.fechaCosecha);
        if (this.fechaExpiracion != null)     o.setFechaExpiracion(this.fechaExpiracion);
        if (this.aceptaNegociacion != null)   o.setAceptaNegociacion(this.aceptaNegociacion);
        if (this.precioMinimo != null)        o.setPrecioMinimo(this.precioMinimo);
        if (this.tieneSenasa != null)         o.setTieneSenasa(this.tieneSenasa);
        if (this.numeroCertSenasa != null)    o.setNumeroCertSenasa(this.numeroCertSenasa);
        if (this.vencimientoCertSenasa != null) o.setVencimientoCertSenasa(this.vencimientoCertSenasa);
        if (this.tieneGlobalgap != null)      o.setTieneGlobalgap(this.tieneGlobalgap);
        if (this.numeroCertGlobalgap != null) o.setNumeroCertGlobalgap(this.numeroCertGlobalgap);
        if (this.vencimientoGlobalgap != null) o.setVencimientoGlobalgap(this.vencimientoGlobalgap);
        if (this.esOrganico != null)          o.setEsOrganico(this.esOrganico);
        if (this.numeroCertOrganico != null)  o.setNumeroCertOrganico(this.numeroCertOrganico);
        if (this.vencimientoCertOrganico != null) o.setVencimientoCertOrganico(this.vencimientoCertOrganico);
        if (this.fotosUrls != null)           o.setFotosUrls(this.fotosUrls);
        if (this.destacada != null)           o.setDestacada(this.destacada);
    }

    public String getVariedad() { return variedad; }
    public void setVariedad(String variedad) { this.variedad = variedad; }
    public CalidadEnum getCalidad() { return calidad; }
    public void setCalidad(CalidadEnum calidad) { this.calidad = calidad; }
    public BigDecimal getCantidadDisponible() { return cantidadDisponible; }
    public void setCantidadDisponible(BigDecimal c) { this.cantidadDisponible = c; }
    public String getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(String unidadMedida) { this.unidadMedida = unidadMedida; }
    public BigDecimal getPrecioSugerido() { return precioSugerido; }
    public void setPrecioSugerido(BigDecimal precioSugerido) { this.precioSugerido = precioSugerido; }
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
    public EstadoOfertaEnum getEstado() { return estado; }
    public void setEstado(EstadoOfertaEnum estado) { this.estado = estado; }
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
    public void setNumeroCertSenasa(String numeroCertSenasa) { this.numeroCertSenasa = numeroCertSenasa; }
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
    public Boolean getDestacada() { return destacada; }
    public void setDestacada(Boolean destacada) { this.destacada = destacada; }
}
