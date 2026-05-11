package com.foodgest.perfiles.agricultores.entities;

import com.foodgest.users.entities.UserEntities;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "agricultores")
public class AgricultorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UserEntities usuario;

    @Column(name = "nombre_finca")
    private String nombreFinca;

    @Column(name = "hectareas", precision = 10, scale = 2)
    private BigDecimal hectareas;

    @Column(name = "tipo_cultivo_principal")
    private String tipoCultivoPrincipal;

    @Column(name = "ubicacion_parcela", columnDefinition = "POINT")
    private String ubicacionParcela;

    @Column(name = "direccion_parcela")
    private String direccionParcela;

    @Column(name = "latitud", precision = 10, scale = 7)
    private BigDecimal latitud;

    @Column(name = "longitud", precision = 10, scale = 7)
    private BigDecimal longitud;

    @Column(name = "ruc", length = 11)
    private String ruc;

    @Column(name = "cuenta_bancaria")
    private String cuentaBancaria;

    @Column(name = "banco")
    private String banco;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }


    public AgricultorEntity(){

    }

    public AgricultorEntity(UUID id, UserEntities usuario, String nombreFinca, BigDecimal hectareas, String tipoCultivoPrincipal, String ubicacionParcela, String direccionParcela, BigDecimal latitud, BigDecimal longitud, String ruc, String cuentaBancaria, String banco, LocalDateTime createdAt) {
        this.id = id;
        this.usuario = usuario;
        this.nombreFinca = nombreFinca;
        this.hectareas = hectareas;
        this.tipoCultivoPrincipal = tipoCultivoPrincipal;
        this.ubicacionParcela = ubicacionParcela;
        this.direccionParcela = direccionParcela;
        this.latitud = latitud;
        this.longitud = longitud;
        this.ruc = ruc;
        this.cuentaBancaria = cuentaBancaria;
        this.banco = banco;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserEntities getUsuario() {
        return usuario;
    }

    public void setUsuario(UserEntities usuario) {
        this.usuario = usuario;
    }

    public String getNombreFinca() {
        return nombreFinca;
    }

    public void setNombreFinca(String nombreFinca) {
        this.nombreFinca = nombreFinca;
    }

    public BigDecimal getHectareas() {
        return hectareas;
    }

    public void setHectareas(BigDecimal hectareas) {
        this.hectareas = hectareas;
    }

    public String getDireccionParcela() {
        return direccionParcela;
    }

    public void setDireccionParcela(String direccionParcela) {
        this.direccionParcela = direccionParcela;
    }

    public String getUbicacionParcela() {
        return ubicacionParcela;
    }

    public void setUbicacionParcela(String ubicacionParcela) {
        this.ubicacionParcela = ubicacionParcela;
    }

    public String getTipoCultivoPrincipal() {
        return tipoCultivoPrincipal;
    }

    public void setTipoCultivoPrincipal(String tipoCultivoPrincipal) {
        this.tipoCultivoPrincipal = tipoCultivoPrincipal;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(String cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}