package com.foodgest.catalogo.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "fotos_producto")
public class FotoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(name = "url", length = 255, nullable = false)
    private String url;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Column(name = "es_principal", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean esPrincipal = false;

    @Column(name = "orden", columnDefinition = "INT DEFAULT 0")
    private Integer orden = 0;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    public FotoProducto() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Boolean getEsPrincipal() { return esPrincipal; }
    public void setEsPrincipal(Boolean esPrincipal) { this.esPrincipal = esPrincipal; }

    public Integer getOrden() {return orden;}

    public void setOrden(Integer orden) {this.orden = orden;}

    public ZonedDateTime getCreatedAt() {return createdAt;}

    public void setCreatedAt(ZonedDateTime createdAt) {this.createdAt = createdAt;}
}
