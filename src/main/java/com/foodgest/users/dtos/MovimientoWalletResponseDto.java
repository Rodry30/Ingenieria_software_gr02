package com.foodgest.users.dtos;

import com.foodgest.users.entities.MovimientoWallet;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class MovimientoWalletResponseDto {

    private UUID id;
    private UUID walletId;
    private UUID pedidoId;
    private String tipo;
    private BigDecimal monto;
    private BigDecimal saldoAnterior;
    private BigDecimal saldoPosterior;
    private String descripcion;
    private OffsetDateTime createdAt;

    public static MovimientoWalletResponseDto from(MovimientoWallet movimiento) {
        MovimientoWalletResponseDto dto = new MovimientoWalletResponseDto();
        dto.id = movimiento.getId();
        dto.walletId = movimiento.getWallet().getId();
        dto.pedidoId = movimiento.getPedido() != null ? movimiento.getPedido().getId() : null;
        dto.tipo = movimiento.getTipo();
        dto.monto = movimiento.getMonto();
        dto.saldoAnterior = movimiento.getSaldoAnterior();
        dto.saldoPosterior = movimiento.getSaldoPosterior();
        dto.descripcion = movimiento.getDescripcion();
        dto.createdAt = movimiento.getCreatedAt();
        return dto;
    }

    public UUID getId() { return id; }
    public UUID getWalletId() { return walletId; }
    public UUID getPedidoId() { return pedidoId; }
    public String getTipo() { return tipo; }
    public BigDecimal getMonto() { return monto; }
    public BigDecimal getSaldoAnterior() { return saldoAnterior; }
    public BigDecimal getSaldoPosterior() { return saldoPosterior; }
    public String getDescripcion() { return descripcion; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
}

