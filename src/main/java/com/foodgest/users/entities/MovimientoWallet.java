package com.foodgest.users.entities;

import com.foodgest.pedidos.entities.Pedido;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "movimientos_wallet")
public class MovimientoWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @Column(name = "tipo", nullable = false)
    @ColumnTransformer(write = "?::tipo_movimiento_enum")
    private String tipo;

    @Column(name = "monto", precision = 12, scale = 2, nullable = false)
    private BigDecimal monto;

    @Column(name = "saldo_anterior", precision = 12, scale = 2, nullable = false)
    private BigDecimal saldoAnterior;

    @Column(name = "saldo_posterior", precision = 12, scale = 2, nullable = false)
    private BigDecimal saldoPosterior;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime createdAt;

    public MovimientoWallet() {}

    // getters / setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Wallet getWallet() { return wallet; }
    public void setWallet(Wallet wallet) { this.wallet = wallet; }
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public BigDecimal getSaldoAnterior() { return saldoAnterior; }
    public void setSaldoAnterior(BigDecimal saldoAnterior) { this.saldoAnterior = saldoAnterior; }
    public BigDecimal getSaldoPosterior() { return saldoPosterior; }
    public void setSaldoPosterior(BigDecimal saldoPosterior) { this.saldoPosterior = saldoPosterior; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
 

