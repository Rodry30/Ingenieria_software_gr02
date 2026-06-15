package com.foodgest.pedidos.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "transacciones")
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(name = "tipo", length = 30, nullable = false)
    private String tipo;

    @Column(name = "monto", precision = 12, scale = 2, nullable = false)
    private BigDecimal monto;

    @Column(name = "moneda", length = 5)
    private String moneda = "PEN";

    @Column(name = "estado", length = 30)
    private String estado = "pendiente";

    @Column(name = "pasarela_pago", length = 50)
    private String pasarelaPago;

    @Column(name = "referencia_externa", length = 100)
    private String referenciaExterna;

    @Column(name = "codigo_autorizacion", length = 100)
    private String codigoAutorizacion;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "metadata", columnDefinition = "JSONB")
    private String metadata = "{}";

    @CreationTimestamp
    @Column(name = "fecha_transaccion", updatable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime fechaTransaccion;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime createdAt;

    public Transaccion() {}

    // getters / setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getPasarelaPago() { return pasarelaPago; }
    public void setPasarelaPago(String pasarelaPago) { this.pasarelaPago = pasarelaPago; }
    public String getReferenciaExterna() { return referenciaExterna; }
    public void setReferenciaExterna(String referenciaExterna) { this.referenciaExterna = referenciaExterna; }
    public String getCodigoAutorizacion() { return codigoAutorizacion; }
    public void setCodigoAutorizacion(String codigoAutorizacion) { this.codigoAutorizacion = codigoAutorizacion; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getMetadata() { return metadata; }
    public void setMetadata(String metadata) { this.metadata = metadata; }
    public OffsetDateTime getFechaTransaccion() { return fechaTransaccion; }
    public void setFechaTransaccion(OffsetDateTime fechaTransaccion) { this.fechaTransaccion = fechaTransaccion; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
 

