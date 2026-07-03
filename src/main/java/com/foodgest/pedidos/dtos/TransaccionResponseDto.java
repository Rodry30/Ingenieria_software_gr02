package com.foodgest.pedidos.dtos;

import com.foodgest.pedidos.entities.Transaccion;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class TransaccionResponseDto {

    private UUID id;
    private UUID pedidoId;
    private String tipo;
    private BigDecimal monto;
    private String moneda;
    private String estado;
    private String pasarelaPago;
    private String referenciaExterna;
    private String codigoAutorizacion;
    private OffsetDateTime fechaTransaccion;

    public static TransaccionResponseDto from(Transaccion transaccion) {
        TransaccionResponseDto dto = new TransaccionResponseDto();
        dto.id = transaccion.getId();
        dto.pedidoId = transaccion.getPedido().getId();
        dto.tipo = transaccion.getTipo();
        dto.monto = transaccion.getMonto();
        dto.moneda = transaccion.getMoneda();
        dto.estado = transaccion.getEstado();
        dto.pasarelaPago = transaccion.getPasarelaPago();
        dto.referenciaExterna = transaccion.getReferenciaExterna();
        dto.codigoAutorizacion = transaccion.getCodigoAutorizacion();
        dto.fechaTransaccion = transaccion.getFechaTransaccion();
        return dto;
    }

    public UUID getId() { return id; }
    public UUID getPedidoId() { return pedidoId; }
    public String getTipo() { return tipo; }
    public BigDecimal getMonto() { return monto; }
    public String getMoneda() { return moneda; }
    public String getEstado() { return estado; }
    public String getPasarelaPago() { return pasarelaPago; }
    public String getReferenciaExterna() { return referenciaExterna; }
    public String getCodigoAutorizacion() { return codigoAutorizacion; }
    public OffsetDateTime getFechaTransaccion() { return fechaTransaccion; }
}

