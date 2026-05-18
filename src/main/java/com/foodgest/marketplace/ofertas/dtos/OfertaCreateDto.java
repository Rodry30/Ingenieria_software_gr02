package com.foodgest.marketplace.ofertas.dtos;

import com.foodgest.marketplace.ofertas.entities.Oferta;
import com.foodgest.marketplace.ofertas.enums.CalidadEnum;
import com.foodgest.perfiles.agricultores.entities.Agricultor;
import com.foodgest.catalogo.entities.Producto;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class OfertaCreateDto {

    @NotNull(message = "El agricultor_id es obligatorio")
    private UUID agricultorId;

    @NotNull(message = "El producto_id es obligatorio")
    private UUID productoId;

    @Size(max = 100, message = "La variedad no puede superar los 100 caracteres")
    private String variedad;

    private CalidadEnum calidad;

    @NotNull(message = "La cantidad disponible es obligatoria")
    @Positive(message = "La cantidad disponible debe ser mayor a 0")
    private BigDecimal cantidadDisponible;

    @NotBlank(message = "La unidad de medida es obligatoria")
    @Size(max = 30, message = "La unidad de medida no puede superar los 30 caracteres")
    private String unidadMedida = "kg";

    @NotNull(message = "El precio sugerido es obligatorio")
    @Positive(message = "El precio sugerido debe ser mayor a 0")
    private BigDecimal precioSugerido;

    @Size(max = 5, message = "La moneda no puede superar los 5 caracteres")
    private String moneda = "PEN";

    @DecimalMin(value = "-90.0", message = "La latitud minima es -90.0")
    @DecimalMax(value = "90.0",  message = "La latitud maxima es 90.0")
    private BigDecimal latitud;

    @DecimalMin(value = "-180.0", message = "La longitud minima es -180.0")
    @DecimalMax(value = "180.0",  message = "La longitud maxima es 180.0")
    private BigDecimal longitud;

    @Size(max = 255, message = "La direccion de referencia no puede superar los 255 caracteres")
    private String direccionReferencia;

    private Integer radioEntregaKm = 50;

    @Size(max = 30, message = "La condicion de entrega no puede superar los 30 caracteres")
    private String condicionEntrega = "en_chacra";

    private LocalDate fechaCosecha;
    private OffsetDateTime fechaExpiracion;
    private Boolean aceptaNegociacion = true;

    @Positive(message = "El precio minimo debe ser mayor a 0")
    private BigDecimal precioMinimo;

    // Certificaciones
    private Boolean tieneSenasa = false;
    @Size(max = 50) private String numeroCertSenasa;
    private LocalDate vencimientoCertSenasa;

    private Boolean tieneGlobalgap = false;
    @Size(max = 50) private String numeroCertGlobalgap;
    private LocalDate vencimientoGlobalgap;

    private Boolean esOrganico = false;
    @Size(max = 50) private String numeroCertOrganico;
    private LocalDate vencimientoCertOrganico;

    public Oferta toEntity(Agricultor agricultor, Producto producto) {
        Oferta o = new Oferta();
        o.setAgricultor(agricultor);
        o.setProducto(producto);
        o.setVariedad(this.variedad);
        o.setCalidad(this.calidad != null ? this.calidad.name() : "primera");
        o.setCantidadDisponible(this.cantidadDisponible);
        o.setUnidadMedida(this.unidadMedida != null ? this.unidadMedida : "kg");
        o.setPrecioSugerido(this.precioSugerido);
        o.setMoneda(this.moneda != null ? this.moneda : "PEN");
        o.setLatitud(this.latitud);
        o.setLongitud(this.longitud);
        o.setDireccionReferencia(this.direccionReferencia);
        o.setRadioEntregaKm(this.radioEntregaKm != null ? this.radioEntregaKm : 50);
        o.setCondicionEntrega(this.condicionEntrega != null ? this.condicionEntrega : "en_chacra");
        o.setEstado("activa");
        o.setFechaCosecha(this.fechaCosecha);
        o.setFechaExpiracion(this.fechaExpiracion);
        o.setAceptaNegociacion(this.aceptaNegociacion != null ? this.aceptaNegociacion : true);
        o.setPrecioMinimo(this.precioMinimo);
        o.setTieneSenasa(this.tieneSenasa != null ? this.tieneSenasa : false);
        o.setNumeroCertSenasa(this.numeroCertSenasa);
        o.setVencimientoCertSenasa(this.vencimientoCertSenasa);
        o.setTieneGlobalgap(this.tieneGlobalgap != null ? this.tieneGlobalgap : false);
        o.setNumeroCertGlobalgap(this.numeroCertGlobalgap);
        o.setVencimientoGlobalgap(this.vencimientoGlobalgap);
        o.setEsOrganico(this.esOrganico != null ? this.esOrganico : false);
        o.setNumeroCertOrganico(this.numeroCertOrganico);
        o.setVencimientoCertOrganico(this.vencimientoCertOrganico);
        return o;
    }

    // Getters y Setters
    public UUID getAgricultorId() { return agricultorId; }
    public void setAgricultorId(UUID agricultorId) { this.agricultorId = agricultorId; }
    public UUID getProductoId() { return productoId; }
    public void setProductoId(UUID productoId) { this.productoId = productoId; }
    public String getVariedad() { return variedad; }
    public void setVariedad(String variedad) { this.variedad = variedad; }
    public CalidadEnum getCalidad() { return calidad; }
    public void setCalidad(CalidadEnum calidad) { this.calidad = calidad; }
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
    public void setDireccionReferencia(String d) { this.direccionReferencia = d; }
    public Integer getRadioEntregaKm() { return radioEntregaKm; }
    public void setRadioEntregaKm(Integer radioEntregaKm) { this.radioEntregaKm = radioEntregaKm; }
    public String getCondicionEntrega() { return condicionEntrega; }
    public void setCondicionEntrega(String condicionEntrega) { this.condicionEntrega = condicionEntrega; }
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
    public void setNumeroCertGlobalgap(String numeroCertGlobalgap) { this.numeroCertGlobalgap = numeroCertGlobalgap; }
    public LocalDate getVencimientoGlobalgap() { return vencimientoGlobalgap; }
    public void setVencimientoGlobalgap(LocalDate v) { this.vencimientoGlobalgap = v; }
    public Boolean getEsOrganico() { return esOrganico; }
    public void setEsOrganico(Boolean esOrganico) { this.esOrganico = esOrganico; }
    public String getNumeroCertOrganico() { return numeroCertOrganico; }
    public void setNumeroCertOrganico(String numeroCertOrganico) { this.numeroCertOrganico = numeroCertOrganico; }
    public LocalDate getVencimientoCertOrganico() { return vencimientoCertOrganico; }
    public void setVencimientoCertOrganico(LocalDate v) { this.vencimientoCertOrganico = v; }
}
