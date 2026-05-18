package com.foodgest.perfiles.agricultores.dtos;

import com.foodgest.perfiles.agricultores.entities.Agricultor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class AgricultorResponseDto {

    private UUID id;
    private UUID usuarioId;
    private String usuarioNombre;
    private String usuarioEmail;
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
    private OffsetDateTime createdAt;

    public static AgricultorResponseDto from(Agricultor a) {
        AgricultorResponseDto dto = new AgricultorResponseDto();
        dto.id = a.getId();
        if (a.getUsuario() != null) {
            dto.usuarioId    = a.getUsuario().getId();
            dto.usuarioNombre = a.getUsuario().getNombre();
            dto.usuarioEmail  = a.getUsuario().getEmail();
        }
        dto.nombreFinca          = a.getNombreFinca();
        dto.hectareas            = a.getHectareas();
        dto.descripcion          = a.getDescripcion();
        dto.tipoCultivoPrincipal = a.getTipoCultivoPrincipal();
        dto.direccionParcela     = a.getDireccionParcela();
        dto.latitud              = a.getLatitud();
        dto.longitud             = a.getLongitud();
        dto.ruc                  = a.getRuc();
        dto.cuentaBancaria       = a.getCuentaBancaria();
        dto.banco                = a.getBanco();
        dto.createdAt            = a.getCreatedAt();
        return dto;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }
    public String getUsuarioNombre() { return usuarioNombre; }
    public void setUsuarioNombre(String usuarioNombre) { this.usuarioNombre = usuarioNombre; }
    public String getUsuarioEmail() { return usuarioEmail; }
    public void setUsuarioEmail(String usuarioEmail) { this.usuarioEmail = usuarioEmail; }
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
