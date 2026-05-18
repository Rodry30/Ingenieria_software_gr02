package com.foodgest.marketplace.precios.dtos;

import com.foodgest.catalogo.entities.Producto;
import com.foodgest.marketplace.precios.entities.PrecioMercado;
import com.foodgest.marketplace.precios.enums.TendenciaEnum;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class PrecioMercadoCreateDto {

    @NotNull(message = "El producto_id es obligatorio")
    private UUID productoId;

    @NotBlank(message = "La fuente es obligatoria")
    @Size(max = 20, message = "La fuente no puede superar los 20 caracteres")
    private String fuente;

    private BigDecimal precioMin;
    private BigDecimal precioMax;
    private BigDecimal precioPromedio;

    @DecimalMin(value = "-999.99", message = "La variacion porcentaje fuera de rango")
    @DecimalMax(value = "999.99",  message = "La variacion porcentaje fuera de rango")
    private BigDecimal variacionPorcentaje;

    private TendenciaEnum tendencia;

    @NotNull(message = "La fecha de precio es obligatoria")
    private LocalDate fechaPrecio;

    public PrecioMercado toEntity(Producto producto) {
        PrecioMercado pm = new PrecioMercado();
        pm.setProducto(producto);
        pm.setFuente(this.fuente);
        pm.setPrecioMin(this.precioMin);
        pm.setPrecioMax(this.precioMax);
        pm.setPrecioPromedio(this.precioPromedio);
        pm.setVariacionPorcentaje(this.variacionPorcentaje);
        pm.setTendencia(this.tendencia != null ? this.tendencia.name() : null);
        pm.setFechaPrecio(this.fechaPrecio);
        return pm;
    }

    public UUID getProductoId() { return productoId; }
    public void setProductoId(UUID productoId) { this.productoId = productoId; }
    public String getFuente() { return fuente; }
    public void setFuente(String fuente) { this.fuente = fuente; }
    public BigDecimal getPrecioMin() { return precioMin; }
    public void setPrecioMin(BigDecimal precioMin) { this.precioMin = precioMin; }
    public BigDecimal getPrecioMax() { return precioMax; }
    public void setPrecioMax(BigDecimal precioMax) { this.precioMax = precioMax; }
    public BigDecimal getPrecioPromedio() { return precioPromedio; }
    public void setPrecioPromedio(BigDecimal precioPromedio) { this.precioPromedio = precioPromedio; }
    public BigDecimal getVariacionPorcentaje() { return variacionPorcentaje; }
    public void setVariacionPorcentaje(BigDecimal variacionPorcentaje) { this.variacionPorcentaje = variacionPorcentaje; }
    public TendenciaEnum getTendencia() { return tendencia; }
    public void setTendencia(TendenciaEnum tendencia) { this.tendencia = tendencia; }
    public LocalDate getFechaPrecio() { return fechaPrecio; }
    public void setFechaPrecio(LocalDate fechaPrecio) { this.fechaPrecio = fechaPrecio; }
}
