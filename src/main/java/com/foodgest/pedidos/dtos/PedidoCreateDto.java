package com.foodgest.pedidos.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class PedidoCreateDto {

    @NotNull(message = "La oferta_id es obligatoria")
    private UUID ofertaId;

    @NotNull(message = "El comprador_id es obligatorio")
    private UUID compradorId;

    @NotNull(message = "La cantidad acordada es obligatoria")
    @Positive(message = "La cantidad acordada debe ser mayor a 0")
    private BigDecimal cantidadAcordada;

    @Positive(message = "El costo de envio debe ser mayor a 0")
    private BigDecimal costoEnvio = BigDecimal.ZERO;

    @Size(max = 30, message = "El metodo de pago no puede superar los 30 caracteres")
    private String metodoPago;

    @NotBlank(message = "La direccion de entrega es obligatoria")
    private String direccionEntrega;

    @Size(max = 100, message = "El nombre de destino no puede superar los 100 caracteres")
    private String destinoNombre;

    private BigDecimal latitudEntrega;
    private BigDecimal longitudEntrega;
    private OffsetDateTime fechaEntregaEstimada;
    private String notasComprador;

    public UUID getOfertaId() { return ofertaId; }
    public void setOfertaId(UUID ofertaId) { this.ofertaId = ofertaId; }
    public UUID getCompradorId() { return compradorId; }
    public void setCompradorId(UUID compradorId) { this.compradorId = compradorId; }
    public BigDecimal getCantidadAcordada() { return cantidadAcordada; }
    public void setCantidadAcordada(BigDecimal cantidadAcordada) { this.cantidadAcordada = cantidadAcordada; }
    public BigDecimal getCostoEnvio() { return costoEnvio; }
    public void setCostoEnvio(BigDecimal costoEnvio) { this.costoEnvio = costoEnvio; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public String getDireccionEntrega() { return direccionEntrega; }
    public void setDireccionEntrega(String direccionEntrega) { this.direccionEntrega = direccionEntrega; }
    public String getDestinoNombre() { return destinoNombre; }
    public void setDestinoNombre(String destinoNombre) { this.destinoNombre = destinoNombre; }
    public BigDecimal getLatitudEntrega() { return latitudEntrega; }
    public void setLatitudEntrega(BigDecimal latitudEntrega) { this.latitudEntrega = latitudEntrega; }
    public BigDecimal getLongitudEntrega() { return longitudEntrega; }
    public void setLongitudEntrega(BigDecimal longitudEntrega) { this.longitudEntrega = longitudEntrega; }
    public OffsetDateTime getFechaEntregaEstimada() { return fechaEntregaEstimada; }
    public void setFechaEntregaEstimada(OffsetDateTime fechaEntregaEstimada) { this.fechaEntregaEstimada = fechaEntregaEstimada; }
    public String getNotasComprador() { return notasComprador; }
    public void setNotasComprador(String notasComprador) { this.notasComprador = notasComprador; }
}

