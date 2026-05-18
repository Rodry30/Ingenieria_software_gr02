package com.foodgest.marketplace.precios.entities;

import com.foodgest.catalogo.entities.Producto;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
    name = "precios_mercado",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "idx_precios_uniq",
            columnNames = {"producto_id", "fuente", "fecha_precio"}
        )
    }
)
public class PrecioMercado {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(name = "fuente", nullable = false, length = 20)
    private String fuente;

    @Column(name = "precio_min", precision = 10, scale = 2)
    private BigDecimal precioMin;

    @Column(name = "precio_max", precision = 10, scale = 2)
    private BigDecimal precioMax;

    @Column(name = "precio_promedio", precision = 10, scale = 2)
    private BigDecimal precioPromedio;

    @Column(name = "variacion_porcentaje", precision = 5, scale = 2)
    private BigDecimal variacionPorcentaje;

    // Enum PostgreSQL: tendencia_enum
    @Column(name = "tendencia")
    @ColumnTransformer(write = "?::tendencia_enum")
    private String tendencia;

    @Column(name = "fecha_precio", nullable = false)
    private LocalDate fechaPrecio;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime createdAt;

    public PrecioMercado() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

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

    public String getTendencia() { return tendencia; }
    public void setTendencia(String tendencia) { this.tendencia = tendencia; }

    public LocalDate getFechaPrecio() { return fechaPrecio; }
    public void setFechaPrecio(LocalDate fechaPrecio) { this.fechaPrecio = fechaPrecio; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
