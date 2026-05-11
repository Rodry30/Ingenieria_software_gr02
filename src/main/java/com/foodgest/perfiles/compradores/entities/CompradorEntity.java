package com.foodgest.perfiles.compradores.entities;

import com.foodgest.users.entities.UserEntities;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "compradores")
public class CompradorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UserEntities usuario;

    @Column(name = "tipo_comprador")
    private String tipoComprador;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "ruc", length = 11)
    private String ruc;

    @Column(name = "direccion_entrega_default")
    private String direccionEntregaDefault;

    @Column(name = "latitud_entrega", precision = 10, scale = 7)
    private BigDecimal latitudEntrega;

    @Column(name = "longitud_entrega", precision = 10, scale = 7)
    private BigDecimal longitudEntrega;

    @Column(name = "limite_credito", precision = 10, scale = 2)
    private BigDecimal limiteCredito;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UserEntities getUsuario() { return usuario; }
    public void setUsuario(UserEntities usuario) { this.usuario = usuario; }

    public String getTipoComprador() { return tipoComprador; }
    public void setTipoComprador(String tipoComprador) { this.tipoComprador = tipoComprador; }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public String getRuc() { return ruc; }
    public void setRuc(String ruc) { this.ruc = ruc; }

    public String getDireccionEntregaDefault() { return direccionEntregaDefault; }
    public void setDireccionEntregaDefault(String direccionEntregaDefault) { this.direccionEntregaDefault = direccionEntregaDefault; }

    public BigDecimal getLatitudEntrega() { return latitudEntrega; }
    public void setLatitudEntrega(BigDecimal latitudEntrega) { this.latitudEntrega = latitudEntrega; }

    public BigDecimal getLongitudEntrega() { return longitudEntrega; }
    public void setLongitudEntrega(BigDecimal longitudEntrega) { this.longitudEntrega = longitudEntrega; }

    public BigDecimal getLimiteCredito() { return limiteCredito; }
    public void setLimiteCredito(BigDecimal limiteCredito) { this.limiteCredito = limiteCredito; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}