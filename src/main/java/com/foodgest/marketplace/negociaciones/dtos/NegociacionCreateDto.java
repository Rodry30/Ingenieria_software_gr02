package com.foodgest.marketplace.negociaciones.dtos;

import com.foodgest.marketplace.negociaciones.entities.Negociacion;
import com.foodgest.marketplace.ofertas.entities.Oferta;
import com.foodgest.perfiles.compradores.entities.Comprador;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public class NegociacionCreateDto {

    @NotNull(message = "El oferta_id es obligatorio")
    private UUID ofertaId;

    @NotNull(message = "El comprador_id es obligatorio")
    private UUID compradorId;

    @NotNull(message = "El precio propuesto es obligatorio")
    @Positive(message = "El precio propuesto debe ser mayor a 0")
    private BigDecimal precioPropuesto;

    @NotNull(message = "La cantidad propuesta es obligatoria")
    @Positive(message = "La cantidad propuesta debe ser mayor a 0")
    private BigDecimal cantidadPropuesta;

    private String mensajeInicial;

    public Negociacion toEntity(Oferta oferta, Comprador comprador) {
        Negociacion n = new Negociacion();
        n.setOferta(oferta);
        n.setComprador(comprador);
        n.setPrecioPropuesto(this.precioPropuesto);
        n.setCantidadPropuesta(this.cantidadPropuesta);
        n.setMensajeInicial(this.mensajeInicial);
        n.setEstado("pendiente");
        return n;
    }

    public UUID getOfertaId() { return ofertaId; }
    public void setOfertaId(UUID ofertaId) { this.ofertaId = ofertaId; }
    public UUID getCompradorId() { return compradorId; }
    public void setCompradorId(UUID compradorId) { this.compradorId = compradorId; }
    public BigDecimal getPrecioPropuesto() { return precioPropuesto; }
    public void setPrecioPropuesto(BigDecimal precioPropuesto) { this.precioPropuesto = precioPropuesto; }
    public BigDecimal getCantidadPropuesta() { return cantidadPropuesta; }
    public void setCantidadPropuesta(BigDecimal cantidadPropuesta) { this.cantidadPropuesta = cantidadPropuesta; }
    public String getMensajeInicial() { return mensajeInicial; }
    public void setMensajeInicial(String mensajeInicial) { this.mensajeInicial = mensajeInicial; }
}
