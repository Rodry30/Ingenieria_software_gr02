package com.foodgest.pedidos.dtos;

import com.foodgest.pedidos.entities.DetallePedido;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public class DetallePedidoDto {

    private UUID id;

    @NotNull(message = "El pedido_id es obligatorio")
    private UUID pedidoId;

    @NotNull(message = "El producto_id es obligatorio")
    private UUID productoId;

    @NotNull(message = "El agricultor_id es obligatorio")
    private UUID agricultorId;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a 0")
    private BigDecimal cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    @Positive(message = "El precio unitario debe ser mayor a 0")
    private BigDecimal precioUnitario;

    private BigDecimal subtotal;
    private String estadoAgricultor;
    private String notas;

    public static DetallePedidoDto from(DetallePedido detalle) {
        DetallePedidoDto dto = new DetallePedidoDto();
        dto.id = detalle.getId();
        dto.pedidoId = detalle.getPedido().getId();
        dto.productoId = detalle.getProducto().getId();
        dto.agricultorId = detalle.getAgricultor().getId();
        dto.cantidad = detalle.getCantidad();
        dto.precioUnitario = detalle.getPrecioUnitario();
        dto.subtotal = detalle.getSubtotal();
        dto.estadoAgricultor = detalle.getEstadoAgricultor();
        dto.notas = detalle.getNotas();
        return dto;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getPedidoId() { return pedidoId; }
    public void setPedidoId(UUID pedidoId) { this.pedidoId = pedidoId; }
    public UUID getProductoId() { return productoId; }
    public void setProductoId(UUID productoId) { this.productoId = productoId; }
    public UUID getAgricultorId() { return agricultorId; }
    public void setAgricultorId(UUID agricultorId) { this.agricultorId = agricultorId; }
    public BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    public String getEstadoAgricultor() { return estadoAgricultor; }
    public void setEstadoAgricultor(String estadoAgricultor) { this.estadoAgricultor = estadoAgricultor; }
    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
}

