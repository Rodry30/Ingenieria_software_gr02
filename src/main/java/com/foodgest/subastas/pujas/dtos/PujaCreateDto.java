package com.foodgest.subastas.pujas.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public class PujaCreateDto {

    @NotNull(message = "El comprador_id es obligatorio")
    private UUID compradorId;

    @NotNull(message = "El monto de la puja es obligatorio")
    @Positive(message = "El monto de la puja debe ser mayor a 0")
    private BigDecimal montoPuja;

    public UUID getCompradorId() { return compradorId; }
    public void setCompradorId(UUID compradorId) { this.compradorId = compradorId; }
    public BigDecimal getMontoPuja() { return montoPuja; }
    public void setMontoPuja(BigDecimal montoPuja) { this.montoPuja = montoPuja; }
}

