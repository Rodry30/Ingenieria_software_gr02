package com.foodgest.perfiles.compradores.entities;

import com.foodgest.perfiles.compradores.enums.TipoCompradorEnum;   
import com.foodgest.users.entities.UserEntities;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "compradores")
public class Comprador {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private UserEntities usuario;

    // MEJORA: se usa @ColumnTransformer para castear al tipo PostgreSQL personalizado
    // igual que se hace en UserEntities con tipo_usuario_enum
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_comprador")
    @ColumnTransformer(write = "?::tipo_comprador_enum")
    private TipoCompradorEnum tipoComprador;

    @Column(name = "razon_social", length = 100)
    private String razonSocial;

    @Column(name = "ruc", length = 15)
    private String ruc;

    @Column(name = "direccion_entrega_default", length = 255)
    private String direccionEntregaDefault;

    @Column(name = "latitud_entrega", precision = 9, scale = 6)
    private BigDecimal latitudEntrega;

    @Column(name = "longitud_entrega", precision = 9, scale = 6)
    private BigDecimal longitudEntrega;

    @Column(name = "limite_credito", precision = 12, scale = 2)
    private BigDecimal limiteCredito = BigDecimal.ZERO;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime createdAt;

    public Comprador() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UserEntities getUsuario() { return usuario; }
    public void setUsuario(UserEntities usuario) { this.usuario = usuario; }

    public TipoCompradorEnum getTipoComprador() { return tipoComprador; }
    public void setTipoComprador(TipoCompradorEnum tipoComprador) { this.tipoComprador = tipoComprador; }

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

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
