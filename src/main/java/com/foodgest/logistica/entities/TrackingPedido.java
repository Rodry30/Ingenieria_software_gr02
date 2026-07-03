package com.foodgest.logistica.entities;

import com.foodgest.pedidos.entities.Pedido;
import com.foodgest.perfiles.transportistas.entities.TransportistaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tracking_pedido")
public class TrackingPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transportista_id", nullable = false)
    private TransportistaEntity transportista;

    @Column(name = "latitud", precision = 9, scale = 6, nullable = false)
    private BigDecimal latitud;

    @Column(name = "longitud", precision = 9, scale = 6, nullable = false)
    private BigDecimal longitud;

    @Column(name = "velocidad_kmh", precision = 5, scale = 2)
    private BigDecimal velocidadKmh;

    @Column(name = "distancia_restante_km", precision = 8, scale = 2)
    private BigDecimal distanciaRestanteKm;

    @Column(name = "eta", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime eta;

    @Column(name = "descripcion", length = 100)
    private String descripcion;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime createdAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
    public TransportistaEntity getTransportista() { return transportista; }
    public void setTransportista(TransportistaEntity transportista) { this.transportista = transportista; }
    public BigDecimal getLatitud() { return latitud; }
    public void setLatitud(BigDecimal latitud) { this.latitud = latitud; }
    public BigDecimal getLongitud() { return longitud; }
    public void setLongitud(BigDecimal longitud) { this.longitud = longitud; }
    public BigDecimal getVelocidadKmh() { return velocidadKmh; }
    public void setVelocidadKmh(BigDecimal velocidadKmh) { this.velocidadKmh = velocidadKmh; }
    public BigDecimal getDistanciaRestanteKm() { return distanciaRestanteKm; }
    public void setDistanciaRestanteKm(BigDecimal distanciaRestanteKm) { this.distanciaRestanteKm = distanciaRestanteKm; }
    public OffsetDateTime getEta() { return eta; }
    public void setEta(OffsetDateTime eta) { this.eta = eta; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}

