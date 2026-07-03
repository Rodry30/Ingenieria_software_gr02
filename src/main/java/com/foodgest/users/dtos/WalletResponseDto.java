package com.foodgest.users.dtos;

import com.foodgest.users.entities.Wallet;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class WalletResponseDto {

    private UUID id;
    private UUID usuarioId;
    private BigDecimal saldoDisponible;
    private BigDecimal saldoRetenido;
    private String moneda;
    private OffsetDateTime updatedAt;

    public static WalletResponseDto from(Wallet wallet) {
        WalletResponseDto dto = new WalletResponseDto();
        dto.id = wallet.getId();
        dto.usuarioId = wallet.getUsuario().getId();
        dto.saldoDisponible = wallet.getSaldoDisponible();
        dto.saldoRetenido = wallet.getSaldoRetenido();
        dto.moneda = wallet.getMoneda();
        dto.updatedAt = wallet.getUpdatedAt();
        return dto;
    }

    public UUID getId() { return id; }
    public UUID getUsuarioId() { return usuarioId; }
    public BigDecimal getSaldoDisponible() { return saldoDisponible; }
    public BigDecimal getSaldoRetenido() { return saldoRetenido; }
    public String getMoneda() { return moneda; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
}

