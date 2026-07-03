package com.foodgest.pedidos.dtos;

import com.foodgest.pedidos.entities.Pedido;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class PedidoResumenDto {

    private UUID id;
    private String estado;
    private String codigoSeguimiento;
    private BigDecimal total;
    private String moneda;
    private OffsetDateTime fechaPedido;

    public static PedidoResumenDto from(Pedido pedido) {
        PedidoResumenDto dto = new PedidoResumenDto();
        dto.id = pedido.getId();
        dto.estado = pedido.getEstado();
        dto.codigoSeguimiento = pedido.getCodigoSeguimiento();
        dto.total = pedido.getTotal();
        dto.moneda = pedido.getMoneda();
        dto.fechaPedido = pedido.getFechaPedido();
        return dto;
    }

    public UUID getId() { return id; }
    public String getEstado() { return estado; }
    public String getCodigoSeguimiento() { return codigoSeguimiento; }
    public BigDecimal getTotal() { return total; }
    public String getMoneda() { return moneda; }
    public OffsetDateTime getFechaPedido() { return fechaPedido; }
}

