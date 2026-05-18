package com.foodgest.perfiles.agricultores.dtos;

import java.math.BigDecimal;

/**
 * DTO exclusivo para actualizaciones parciales de un agricultor.
 * Todos los campos son opcionales; solo los no-nulos se aplican.
 */
public class AgricultorUpdateDto {

    private String nombreFinca;
    private BigDecimal hectareas;
    private String descripcion;
    private String tipoCultivoPrincipal;
    private String direccionParcela;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private String ruc;
    private String cuentaBancaria;
    private String banco;

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
}
