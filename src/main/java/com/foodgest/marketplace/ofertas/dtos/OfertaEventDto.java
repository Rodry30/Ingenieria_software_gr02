package com.foodgest.marketplace.ofertas.dtos;

import com.foodgest.marketplace.ofertas.entities.Oferta;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Payload publicado por WebSocket (STOMP topic /topic/ofertas) cuando una
 * oferta se crea, actualiza o elimina, para que los mapas conectados se
 * refresquen sin tener que volver a llamar a /api/marketplace/ofertas/mapa.
 */
public class OfertaEventDto {

    private String tipoEvento; // creada | actualizada | eliminada
    private UUID id;
    private UUID agricultorId;
    private UUID productoId;
    private String productoNombre;
    private String agricultorNombre;
    private String nombreFinca;
    private BigDecimal precioSugerido;
    private String moneda;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private String direccionReferencia;
    private String estado;

    public static OfertaEventDto of(String tipoEvento, Oferta o) {
        OfertaEventDto dto = new OfertaEventDto();
        dto.tipoEvento = tipoEvento;
        dto.id = o.getId();
        dto.agricultorId = o.getAgricultor().getId();
        dto.productoId = o.getProducto().getId();
        dto.productoNombre = o.getProducto().getNombre();
        dto.agricultorNombre = o.getAgricultor().getUsuario().getNombre();
        dto.nombreFinca = o.getAgricultor().getNombreFinca();
        dto.precioSugerido = o.getPrecioSugerido();
        dto.moneda = o.getMoneda();
        dto.latitud = o.getLatitud();
        dto.longitud = o.getLongitud();
        dto.direccionReferencia = o.getDireccionReferencia();
        dto.estado = o.getEstado();
        return dto;
    }

    public static OfertaEventDto eliminada(UUID id) {
        OfertaEventDto dto = new OfertaEventDto();
        dto.tipoEvento = "eliminada";
        dto.id = id;
        return dto;
    }

    public String getTipoEvento() { return tipoEvento; }
    public void setTipoEvento(String tipoEvento) { this.tipoEvento = tipoEvento; }
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getAgricultorId() { return agricultorId; }
    public void setAgricultorId(UUID agricultorId) { this.agricultorId = agricultorId; }
    public UUID getProductoId() { return productoId; }
    public void setProductoId(UUID productoId) { this.productoId = productoId; }
    public String getProductoNombre() { return productoNombre; }
    public void setProductoNombre(String productoNombre) { this.productoNombre = productoNombre; }
    public String getAgricultorNombre() { return agricultorNombre; }
    public void setAgricultorNombre(String agricultorNombre) { this.agricultorNombre = agricultorNombre; }
    public String getNombreFinca() { return nombreFinca; }
    public void setNombreFinca(String nombreFinca) { this.nombreFinca = nombreFinca; }
    public BigDecimal getPrecioSugerido() { return precioSugerido; }
    public void setPrecioSugerido(BigDecimal precioSugerido) { this.precioSugerido = precioSugerido; }
    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
    public BigDecimal getLatitud() { return latitud; }
    public void setLatitud(BigDecimal latitud) { this.latitud = latitud; }
    public BigDecimal getLongitud() { return longitud; }
    public void setLongitud(BigDecimal longitud) { this.longitud = longitud; }
    public String getDireccionReferencia() { return direccionReferencia; }
    public void setDireccionReferencia(String direccionReferencia) { this.direccionReferencia = direccionReferencia; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
