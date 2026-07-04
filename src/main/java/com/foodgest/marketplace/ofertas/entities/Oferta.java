package com.foodgest.marketplace.ofertas.entities;

import com.foodgest.catalogo.entities.Producto;
import com.foodgest.perfiles.agricultores.entities.Agricultor;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "ofertas")
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agricultor_id", nullable = false)
    private Agricultor agricultor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(name = "variedad", length = 100)
    private String variedad;

    // Enum PostgreSQL: calidad_enum — se mapea como String con cast
    @Column(name = "calidad")
    @ColumnTransformer(write = "?::calidad_enum")
    private String calidad = "primera";

    @Column(name = "cantidad_disponible", nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidadDisponible;

    @Column(name = "unidad_medida", nullable = false, length = 30)
    private String unidadMedida = "kg";

    @Column(name = "precio_sugerido", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioSugerido;

    @Column(name = "moneda", nullable = false, length = 5)
    private String moneda = "PEN";

    @Column(name = "latitud", precision = 9, scale = 6)
    private BigDecimal latitud;

    @Column(name = "longitud", precision = 9, scale = 6)
    private BigDecimal longitud;

    // ubicacion_oferta GEOGRAPHY(POINT,4326) -> IGNORADO en JPA

    @Column(name = "direccion_referencia", length = 255)
    private String direccionReferencia;

    @Column(name = "radio_entrega_km")
    private Integer radioEntregaKm = 50;

    @Column(name = "condicion_entrega", length = 30)
    private String condicionEntrega = "en_chacra";

    // Enum PostgreSQL: estado_oferta_enum
    @Column(name = "estado", nullable = false)
    @ColumnTransformer(write = "?::estado_oferta_enum")
    private String estado = "activa";

    @Column(name = "fecha_cosecha")
    private LocalDate fechaCosecha;

    @Column(name = "fecha_expiracion", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime fechaExpiracion;

    @Column(name = "acepta_negociacion")
    private Boolean aceptaNegociacion = true;

    @Column(name = "precio_minimo", precision = 10, scale = 2)
    private BigDecimal precioMinimo;

    // ── Certificaciones SENASA ────────────────────────────────────────────────
    @Column(name = "tiene_senasa")
    private Boolean tieneSenasa = false;

    @Column(name = "numero_cert_senasa", length = 50)
    private String numeroCertSenasa;

    @Column(name = "vencimiento_cert_senasa")
    private LocalDate vencimientoCertSenasa;

    // ── Certificaciones GlobalGAP ─────────────────────────────────────────────
    @Column(name = "tiene_globalgap")
    private Boolean tieneGlobalgap = false;

    @Column(name = "numero_cert_globalgap", length = 50)
    private String numeroCertGlobalgap;

    @Column(name = "vencimiento_globalgap")
    private LocalDate vencimientoGlobalgap;

    // ── Certificación Orgánico ────────────────────────────────────────────────
    @Column(name = "es_organico")
    private Boolean esOrganico = false;

    @Column(name = "numero_cert_organico", length = 50)
    private String numeroCertOrganico;

    @Column(name = "vencimiento_cert_organico")
    private LocalDate vencimientoCertOrganico;

    // ── Metadata ──────────────────────────────────────────────────────────────
    // fotos_urls es JSONB en Postgres; sin el cast explicito Hibernate 6 envia
    // el parametro como varchar y Postgres lo rechaza (mismo patron que calidad/estado).
    @Column(name = "fotos_urls", columnDefinition = "JSONB")
    @ColumnTransformer(write = "?::jsonb")
    private String fotosUrls = "[]";

    @Column(name = "vistas")
    private Integer vistas = 0;

    @Column(name = "destacada")
    private Boolean destacada = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime updatedAt;

    public Oferta() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Agricultor getAgricultor() { return agricultor; }
    public void setAgricultor(Agricultor agricultor) { this.agricultor = agricultor; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

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
    public void setNumeroCertSenasa(String numeroCertSenasa) { this.numeroCertSenasa = numeroCertSenasa; }

    public LocalDate getVencimientoCertSenasa() { return vencimientoCertSenasa; }
    public void setVencimientoCertSenasa(LocalDate vencimientoCertSenasa) { this.vencimientoCertSenasa = vencimientoCertSenasa; }

    public Boolean getTieneGlobalgap() { return tieneGlobalgap; }
    public void setTieneGlobalgap(Boolean tieneGlobalgap) { this.tieneGlobalgap = tieneGlobalgap; }

    public String getNumeroCertGlobalgap() { return numeroCertGlobalgap; }
    public void setNumeroCertGlobalgap(String numeroCertGlobalgap) { this.numeroCertGlobalgap = numeroCertGlobalgap; }

    public LocalDate getVencimientoGlobalgap() { return vencimientoGlobalgap; }
    public void setVencimientoGlobalgap(LocalDate vencimientoGlobalgap) { this.vencimientoGlobalgap = vencimientoGlobalgap; }

    public Boolean getEsOrganico() { return esOrganico; }
    public void setEsOrganico(Boolean esOrganico) { this.esOrganico = esOrganico; }

    public String getNumeroCertOrganico() { return numeroCertOrganico; }
    public void setNumeroCertOrganico(String numeroCertOrganico) { this.numeroCertOrganico = numeroCertOrganico; }

    public LocalDate getVencimientoCertOrganico() { return vencimientoCertOrganico; }
    public void setVencimientoCertOrganico(LocalDate vencimientoCertOrganico) { this.vencimientoCertOrganico = vencimientoCertOrganico; }

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
