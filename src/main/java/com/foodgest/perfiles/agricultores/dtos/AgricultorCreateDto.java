package com.foodgest.perfiles.agricultores.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

public class AgricultorCreateDto {

    @NotNull(message = "El usuario es obligatorio")
    private UUID usuarioId;

    @Size(max = 150)
    private String nombreFinca;

    private BigDecimal hectareas;

    @Size(max = 100)
    private String tipoCultivoPrincipal;

    @Size(max = 200)
    private String direccionParcela;

    private BigDecimal latitud;

    private BigDecimal longitud;

    @Size(max = 11)
    private String ruc;

    @Size(max = 50)
    private String cuentaBancaria;

    @Size(max = 100)
    private String banco;

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
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

    public String getTipoCultivoPrincipal() {
        return tipoCultivoPrincipal;
    }

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

}