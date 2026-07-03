package com.foodgest.subastas.pujas.dtos;

import com.foodgest.subastas.pujas.entities.PujaSubasta;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class PujaResponseDto {

    private UUID id;
    private UUID subastaId;
    private UUID compradorId;
    private BigDecimal montoPuja;
    private OffsetDateTime createdAt;

    public static PujaResponseDto from(PujaSubasta puja) {
        PujaResponseDto dto = new PujaResponseDto();
        dto.id = puja.getId();
        dto.subastaId = puja.getSubasta().getId();
        dto.compradorId = puja.getComprador().getId();
        dto.montoPuja = puja.getMontoPuja();
        dto.createdAt = puja.getCreatedAt();
        return dto;
    }

    public UUID getId() { return id; }
    public UUID getSubastaId() { return subastaId; }
    public UUID getCompradorId() { return compradorId; }
    public BigDecimal getMontoPuja() { return montoPuja; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
}

