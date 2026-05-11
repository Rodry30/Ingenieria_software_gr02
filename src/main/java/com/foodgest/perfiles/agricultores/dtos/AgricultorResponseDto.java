package com.foodgest.perfiles.agricultores.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class AgricultorResponseDto {

    private UUID id;
    private String nombreFinca;
    private BigDecimal hectareas;
    private String tipoCultivoPrincipal;
    private String direccionParcela;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private String ruc;
    private String cuentaBancaria;
    private String banco;
    private LocalDateTime createdAt;

    private UsuarioResumen usuario; // objeto anidado

    // Clase interna para el resumen del usuario
    public static class UsuarioResumen {
        private UUID id;
        private String nombre;
        private String email;
        private String telefono;

        public UUID getId() { return id; }
        public void setId(UUID id) { this.id = id; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getTelefono() { return telefono; }
        public void setTelefono(String telefono) { this.telefono = telefono; }
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombreFinca() { return nombreFinca; }
    public void setNombreFinca(String nombreFinca) { this.nombreFinca = nombreFinca; }

    public BigDecimal getHectareas() { return hectareas; }
    public void setHectareas(BigDecimal hectareas) { this.hectareas = hectareas; }

    public String getTipoCultivoPrincipal() { return tipoCultivoPrincipal; }
    public void setTipoCultivoPrincipal(String tipoCultivoPrincipal) {
        this.tipoCultivoPrincipal = tipoCultivoPrincipal;
    }

    public String getDireccionParcela() {
        return direccionParcela;
    }
    public void setDireccionParcela(String direccionParcela) {
        this.direccionParcela = direccionParcela;
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
    public UsuarioResumen getUsuario() {
        return usuario;
    }
    public void setUsuario(UsuarioResumen usuario) {
        this.usuario = usuario;
    }
}