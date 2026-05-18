package com.foodgest.perfiles.agricultores.entities;

import com.foodgest.users.entities.UserEntities;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "agricultores")
public class Agricultor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private UserEntities usuario;

    @Column(name = "nombre_finca", length = 100)
    private String nombreFinca;

    @Column(name = "hectareas", precision = 10, scale = 4)
    private BigDecimal hectareas;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "tipo_cultivo_principal", length = 100)
    private String tipoCultivoPrincipal;

    // ubicacion_parcela es tipo GEOGRAPHY(POINT,4326); se omite mapeo JPA directo
    // y se expone como latitud/longitud en su lugar.

    @Column(name = "direccion_parcela", length = 255)
    private String direccionParcela;

    @Column(name = "latitud", precision = 9, scale = 6)
    private BigDecimal latitud;

    @Column(name = "longitud", precision = 9, scale = 6)
    private BigDecimal longitud;

    @Column(name = "ruc", length = 15)
    private String ruc;

    @Column(name = "cuenta_bancaria", length = 50)
    private String cuentaBancaria;

    @Column(name = "banco", length = 50)
    private String banco;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime createdAt;

    public Agricultor() {
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UserEntities getUsuario() { return usuario; }
    public void setUsuario(UserEntities usuario) { this.usuario = usuario; }

    public String getNombreFinca() { return nombreFinca; }
    public void setNombreFinca(String nombreFinca) { this.nombreFinca = nombreFinca; }

    public BigDecimal getHectareas() { return hectareas; }
    public void setHectareas(BigDecimal hectareas) { this.hectareas = hectareas; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getTipoCultivoPrincipal() { return tipoCultivoPrincipal; }
    public void setTipoCultivoPrincipal(String tipoCultivoPrincipal) { this.tipoCultivoPrincipal = tipoCultivoPrincipal; }

    public String getDireccionParcela() { return direccionParcela; }
    public void setDireccionParcela(String direccionParcela) { this.direccionParcela = direccionParcela; }

    public BigDecimal getLatitud() { return latitud; }
    public void setLatitud(BigDecimal latitud) { this.latitud = latitud; }

    public BigDecimal getLongitud() { return longitud; }
    public void setLongitud(BigDecimal longitud) { this.longitud = longitud; }

    public String getRuc() { return ruc; }
    public void setRuc(String ruc) { this.ruc = ruc; }

    public String getCuentaBancaria() { return cuentaBancaria; }
    public void setCuentaBancaria(String cuentaBancaria) { this.cuentaBancaria = cuentaBancaria; }

    public String getBanco() { return banco; }
    public void setBanco(String banco) { this.banco = banco; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
