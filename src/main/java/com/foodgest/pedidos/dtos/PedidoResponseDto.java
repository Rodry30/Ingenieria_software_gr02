package com.foodgest.pedidos.dtos;

import com.foodgest.pedidos.entities.Pedido;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class PedidoResponseDto {

    private UUID id;
    private UUID ofertaId;
    private UUID compradorId;
    private UUID transportistaId;
    private String estado;
    private BigDecimal precioAcordado;
    private BigDecimal cantidadAcordada;
    private BigDecimal subtotal;
    private BigDecimal costoEnvio;
    private BigDecimal comisionPlataforma;
    private BigDecimal total;
    private String moneda;
    private String metodoPago;
    private String codigoSeguimiento;
    private String direccionEntrega;
    private OffsetDateTime fechaPedido;
    private OffsetDateTime fechaConfirmacion;
    private OffsetDateTime fechaEntregaEstimada;
    private OffsetDateTime fechaEntregaReal;

    public static PedidoResponseDto from(Pedido pedido) {
        PedidoResponseDto dto = new PedidoResponseDto();
        dto.id = pedido.getId();
        dto.ofertaId = pedido.getOferta().getId();
        dto.compradorId = pedido.getComprador().getId();
        dto.transportistaId = pedido.getTransportista() != null ? pedido.getTransportista().getId() : null;
        dto.estado = pedido.getEstado();
        dto.precioAcordado = pedido.getPrecioAcordado();
        dto.cantidadAcordada = pedido.getCantidadAcordada();
        dto.subtotal = pedido.getSubtotal();
        dto.costoEnvio = pedido.getCostoEnvio();
        dto.comisionPlataforma = pedido.getComisionPlataforma();
        dto.total = pedido.getTotal();
        dto.moneda = pedido.getMoneda();
        dto.metodoPago = pedido.getMetodoPago();
        dto.codigoSeguimiento = pedido.getCodigoSeguimiento();
        dto.direccionEntrega = pedido.getDireccionEntrega();
        dto.fechaPedido = pedido.getFechaPedido();
        dto.fechaConfirmacion = pedido.getFechaConfirmacion();
        dto.fechaEntregaEstimada = pedido.getFechaEntregaEstimada();
        dto.fechaEntregaReal = pedido.getFechaEntregaReal();
        return dto;
    }

    public UUID getId() { return id; }
    public UUID getOfertaId() { return ofertaId; }
    public UUID getCompradorId() { return compradorId; }
    public UUID getTransportistaId() { return transportistaId; }
    public String getEstado() { return estado; }
    public BigDecimal getPrecioAcordado() { return precioAcordado; }
    public BigDecimal getCantidadAcordada() { return cantidadAcordada; }
    public BigDecimal getSubtotal() { return subtotal; }
    public BigDecimal getCostoEnvio() { return costoEnvio; }
    public BigDecimal getComisionPlataforma() { return comisionPlataforma; }
    public BigDecimal getTotal() { return total; }
    public String getMoneda() { return moneda; }
    public String getMetodoPago() { return metodoPago; }
    public String getCodigoSeguimiento() { return codigoSeguimiento; }
    public String getDireccionEntrega() { return direccionEntrega; }
    public OffsetDateTime getFechaPedido() { return fechaPedido; }
    public OffsetDateTime getFechaConfirmacion() { return fechaConfirmacion; }
    public OffsetDateTime getFechaEntregaEstimada() { return fechaEntregaEstimada; }
    public OffsetDateTime getFechaEntregaReal() { return fechaEntregaReal; }
}

