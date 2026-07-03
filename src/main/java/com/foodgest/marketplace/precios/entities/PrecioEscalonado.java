package com.foodgest.marketplace.precios.entities;

import com.foodgest.marketplace.ofertas.entities.Oferta;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "precios_escalonados")
public class PrecioEscalonado {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oferta_id", nullable = false)
    private Oferta oferta;

    @Column(name = "cantidad_desde", precision = 10, scale = 2, nullable = false)
    private BigDecimal cantidadDesde;

    @Column(name = "cantidad_hasta", precision = 10, scale = 2)
    private BigDecimal cantidadHasta;

    @Column(name = "precio_unitario", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioUnitario;

    @Column(name = "moneda", length = 5, nullable = false)
    private String moneda = "PEN";

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Oferta getOferta() { return oferta; }
    public void setOferta(Oferta oferta) { this.oferta = oferta; }
    public BigDecimal getCantidadDesde() { return cantidadDesde; }
    public void setCantidadDesde(BigDecimal cantidadDesde) { this.cantidadDesde = cantidadDesde; }
    public BigDecimal getCantidadHasta() { return cantidadHasta; }
    public void setCantidadHasta(BigDecimal cantidadHasta) { this.cantidadHasta = cantidadHasta; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
}

