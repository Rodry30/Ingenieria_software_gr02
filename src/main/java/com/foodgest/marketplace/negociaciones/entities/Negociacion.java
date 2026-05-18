package com.foodgest.marketplace.negociaciones.entities;

import com.foodgest.marketplace.ofertas.entities.Oferta;
import com.foodgest.perfiles.compradores.entities.Comprador;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "negociaciones")
public class Negociacion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oferta_id", nullable = false)
    private Oferta oferta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comprador_id", nullable = false)
    private Comprador comprador;

    @Column(name = "precio_propuesto", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioPropuesto;

    @Column(name = "cantidad_propuesta", nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidadPropuesta;

    @Column(name = "precio_acordado", precision = 10, scale = 2)
    private BigDecimal precioAcordado;

    @Column(name = "cantidad_acordada", precision = 10, scale = 2)
    private BigDecimal cantidadAcordada;

    // Enum PostgreSQL: estado_negociacion_enum
    @Column(name = "estado", nullable = false)
    @ColumnTransformer(write = "?::estado_negociacion_enum")
    private String estado = "pendiente";

    @Column(name = "mensaje_inicial", columnDefinition = "TEXT")
    private String mensajeInicial;

    @Column(name = "fecha_respuesta", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime fechaRespuesta;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime createdAt;

    public Negociacion() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Oferta getOferta() { return oferta; }
    public void setOferta(Oferta oferta) { this.oferta = oferta; }

    public Comprador getComprador() { return comprador; }
    public void setComprador(Comprador comprador) { this.comprador = comprador; }

    public BigDecimal getPrecioPropuesto() { return precioPropuesto; }
    public void setPrecioPropuesto(BigDecimal precioPropuesto) { this.precioPropuesto = precioPropuesto; }

    public BigDecimal getCantidadPropuesta() { return cantidadPropuesta; }
    public void setCantidadPropuesta(BigDecimal cantidadPropuesta) { this.cantidadPropuesta = cantidadPropuesta; }

    public BigDecimal getPrecioAcordado() { return precioAcordado; }
    public void setPrecioAcordado(BigDecimal precioAcordado) { this.precioAcordado = precioAcordado; }

    public BigDecimal getCantidadAcordada() { return cantidadAcordada; }
    public void setCantidadAcordada(BigDecimal cantidadAcordada) { this.cantidadAcordada = cantidadAcordada; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getMensajeInicial() { return mensajeInicial; }
    public void setMensajeInicial(String mensajeInicial) { this.mensajeInicial = mensajeInicial; }

    public OffsetDateTime getFechaRespuesta() { return fechaRespuesta; }
    public void setFechaRespuesta(OffsetDateTime fechaRespuesta) { this.fechaRespuesta = fechaRespuesta; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
