package com.foodgest.perfiles.agricultores.dtos;

import com.foodgest.perfiles.agricultores.entities.Agricultor;
import com.foodgest.users.entities.UserEntities;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * DTO para crear el perfil de agricultor.
 * Se usa tanto en el endpoint de registro como en el CRUD de agricultores.
 * El campo usuarioId es opcional aqui porque en el registro viene del
 * UserEntities ya persistido; en el CRUD legacy venia en el body.
 */
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class AgricultorCreateDto {

    // No se valida usuarioId aqui: en registro lo aporta el servicio,
    // en el CRUD propio se valida en el controller.
    private java.util.UUID usuarioId;

    @NotBlank(message = "El nombre de la finca es obligatorio")
    @Size(max = 100, message = "El nombre de la finca no puede superar los 100 caracteres")
    private String nombreFinca;

    @Positive(message = "Las hectareas deben ser un valor positivo")
    private BigDecimal hectareas;

    private String descripcion;

    private String tipoCultivoPrincipal;

    @NotNull(message = "La latitud es obligatoria")
    @DecimalMin(value = "-90.0", message = "La latitud minima es -90.0")
    @DecimalMax(value = "90.0",  message = "La latitud maxima es 90.0")
    private BigDecimal latitud;

    @NotNull(message = "La longitud es obligatoria")
    @DecimalMin(value = "-180.0", message = "La longitud minima es -180.0")
    @DecimalMax(value = "180.0",  message = "La longitud maxima es 180.0")
    private BigDecimal longitud;

    @Size(max = 255, message = "La direccion de la parcela no puede superar los 255 caracteres")
    private String direccionParcela;

    @Size(max = 15, message = "El RUC no puede superar los 15 caracteres")
    private String ruc;

    @Size(max = 50, message = "La cuenta bancaria no puede superar los 50 caracteres")
    private String cuentaBancaria;

    @Size(max = 50, message = "El banco no puede superar los 50 caracteres")
    private String banco;

    public Agricultor toEntity(UserEntities usuario) {
        Agricultor a = new Agricultor();
        a.setUsuario(usuario);
        a.setNombreFinca(this.nombreFinca);
        a.setHectareas(this.hectareas);
        a.setDescripcion(this.descripcion);
        a.setTipoCultivoPrincipal(this.tipoCultivoPrincipal);
        a.setDireccionParcela(this.direccionParcela);
        a.setLatitud(this.latitud);
        a.setLongitud(this.longitud);
        a.setRuc(this.ruc);
        a.setCuentaBancaria(this.cuentaBancaria);
        a.setBanco(this.banco);
        return a;
    }

    public void applyTo(Agricultor a) {
        if (this.nombreFinca != null)          a.setNombreFinca(this.nombreFinca);
        if (this.hectareas != null)            a.setHectareas(this.hectareas);
        if (this.descripcion != null)          a.setDescripcion(this.descripcion);
        if (this.tipoCultivoPrincipal != null) a.setTipoCultivoPrincipal(this.tipoCultivoPrincipal);
        if (this.direccionParcela != null)     a.setDireccionParcela(this.direccionParcela);
        if (this.latitud != null)              a.setLatitud(this.latitud);
        if (this.longitud != null)             a.setLongitud(this.longitud);
        if (this.ruc != null)                  a.setRuc(this.ruc);
        if (this.cuentaBancaria != null)       a.setCuentaBancaria(this.cuentaBancaria);
        if (this.banco != null)                a.setBanco(this.banco);
    }

    public java.util.UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(java.util.UUID usuarioId) { this.usuarioId = usuarioId; }
    public String getNombreFinca() { return nombreFinca; }
    public void setNombreFinca(String nombreFinca) { this.nombreFinca = nombreFinca; }
    public BigDecimal getHectareas() { return hectareas; }
    public void setHectareas(BigDecimal hectareas) { this.hectareas = hectareas; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getTipoCultivoPrincipal() { return tipoCultivoPrincipal; }
    public void setTipoCultivoPrincipal(String t) { this.tipoCultivoPrincipal = t; }
    public BigDecimal getLatitud() { return latitud; }
    public void setLatitud(BigDecimal latitud) { this.latitud = latitud; }
    public BigDecimal getLongitud() { return longitud; }
    public void setLongitud(BigDecimal longitud) { this.longitud = longitud; }
    public String getDireccionParcela() { return direccionParcela; }
    public void setDireccionParcela(String d) { this.direccionParcela = d; }
    public String getRuc() { return ruc; }
    public void setRuc(String ruc) { this.ruc = ruc; }
    public String getCuentaBancaria() { return cuentaBancaria; }
    public void setCuentaBancaria(String c) { this.cuentaBancaria = c; }
    public String getBanco() { return banco; }
    public void setBanco(String banco) { this.banco = banco; }
}
