package com.foodgest.marketplace.negociaciones.dtos;

import com.foodgest.marketplace.negociaciones.entities.Negociacion;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class NegociacionResponseDto {

    private UUID id;
    private UUID ofertaId;
    private String productoNombre;
    private UUID compradorId;
    private String compradorRazonSocial;
    private BigDecimal precioPropuesto;
    private BigDecimal cantidadPropuesta;
    private BigDecimal precioAcordado;
    private BigDecimal cantidadAcordada;
    private String estado;
    private String mensajeInicial;
    private OffsetDateTime fechaRespuesta;
    private OffsetDateTime createdAt;

    public static NegociacionResponseDto from(Negociacion n) {
        NegociacionResponseDto dto = new NegociacionResponseDto();
        dto.id                  = n.getId();
        dto.ofertaId            = n.getOferta().getId();
        // Accedemos al nombre del producto a través de la oferta
        dto.productoNombre      = n.getOferta().getProducto().getNombre();
        dto.compradorId         = n.getComprador().getId();
        dto.compradorRazonSocial = n.getComprador().getRazonSocial();
        dto.precioPropuesto     = n.getPrecioPropuesto();
        dto.cantidadPropuesta   = n.getCantidadPropuesta();
        dto.precioAcordado      = n.getPrecioAcordado();
        dto.cantidadAcordada    = n.getCantidadAcordada();
        dto.estado              = n.getEstado();
        dto.mensajeInicial      = n.getMensajeInicial();
        dto.fechaRespuesta      = n.getFechaRespuesta();
        dto.createdAt           = n.getCreatedAt();
        return dto;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getOfertaId() { return ofertaId; }
    public void setOfertaId(UUID ofertaId) { this.ofertaId = ofertaId; }
    public String getProductoNombre() { return productoNombre; }
    public void setProductoNombre(String productoNombre) { this.productoNombre = productoNombre; }
    public UUID getCompradorId() { return compradorId; }
    public void setCompradorId(UUID compradorId) { this.compradorId = compradorId; }
    public String getCompradorRazonSocial() { return compradorRazonSocial; }
    public void setCompradorRazonSocial(String compradorRazonSocial) { this.compradorRazonSocial = compradorRazonSocial; }
    public BigDecimal getPrecioPropuesto() { return precioPropuesto; }
    public void setPrecioPropuesto(BigDecimal precioPropuesto) { this.precioPropuesto = precioPropuesto; }
    public BigDecimal getCantidadPropuesta() { return cantidadPropuesta; }
    public void setCantidadPropuesta(BigDecimal cantidadPropuesta) { this.cantidadPropuesta = cantidadPropuesta; }
    public BigDecimal getPrecioAcordado() { return precioAcordado; }
    public void setPrecioAcordado(BigDecimal precioAcordado) { this.precioAcordado = precioAcordado; }
    public BigDecimal getCantidadAcordada() { return cantidadAcordada; }
    public void setCantidadAcordada(BigDecimal cantidadAcordada) { this.cantidadAcordada = cantidadAcordada; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getMensajeInicial() { return mensajeInicial; }
    public void setMensajeInicial(String mensajeInicial) { this.mensajeInicial = mensajeInicial; }
    public OffsetDateTime getFechaRespuesta() { return fechaRespuesta; }
    public void setFechaRespuesta(OffsetDateTime fechaRespuesta) { this.fechaRespuesta = fechaRespuesta; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
