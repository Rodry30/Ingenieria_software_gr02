package com.foodgest.subastas.subastas.entities;

import com.foodgest.catalogo.entities.Producto;
import com.foodgest.perfiles.agricultores.entities.Agricultor;
import com.foodgest.perfiles.compradores.entities.Comprador;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "subastas")
public class Subasta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agricultor_id", nullable = false)
    private Agricultor agricultor;

    @Column(name = "cantidad_lote", precision = 10, scale = 2, nullable = false)
    private BigDecimal cantidadLote;

    @Column(name = "precio_base", precision = 12, scale = 2, nullable = false)
    private BigDecimal precioBase;

    @Column(name = "precio_actual", precision = 12, scale = 2, nullable = false)
    private BigDecimal precioActual;

    @Column(name = "incremento_minimo", precision = 12, scale = 2, nullable = false)
    private BigDecimal incrementoMinimo = new BigDecimal("10.00");

    @Column(name = "fecha_inicio", nullable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime fechaInicio;

    @Column(name = "fecha_fin", nullable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime fechaFin;

    @Column(name = "estado", nullable = false)
    @ColumnTransformer(write = "?::estado_subasta_enum")
    private String estado = "programada";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ganador_id")
    private Comprador ganador;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime createdAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public Agricultor getAgricultor() { return agricultor; }
    public void setAgricultor(Agricultor agricultor) { this.agricultor = agricultor; }
    public BigDecimal getCantidadLote() { return cantidadLote; }
    public void setCantidadLote(BigDecimal cantidadLote) { this.cantidadLote = cantidadLote; }
    public BigDecimal getPrecioBase() { return precioBase; }
    public void setPrecioBase(BigDecimal precioBase) { this.precioBase = precioBase; }
    public BigDecimal getPrecioActual() { return precioActual; }
    public void setPrecioActual(BigDecimal precioActual) { this.precioActual = precioActual; }
    public BigDecimal getIncrementoMinimo() { return incrementoMinimo; }
    public void setIncrementoMinimo(BigDecimal incrementoMinimo) { this.incrementoMinimo = incrementoMinimo; }
    public OffsetDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(OffsetDateTime fechaInicio) { this.fechaInicio = fechaInicio; }
    public OffsetDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(OffsetDateTime fechaFin) { this.fechaFin = fechaFin; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Comprador getGanador() { return ganador; }
    public void setGanador(Comprador ganador) { this.ganador = ganador; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}

