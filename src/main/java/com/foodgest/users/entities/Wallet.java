package com.foodgest.users.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private UserEntities usuario;

    @Column(name = "saldo_disponible", precision = 12, scale = 2, nullable = false)
    private BigDecimal saldoDisponible = BigDecimal.ZERO;

    @Column(name = "saldo_retenido", precision = 12, scale = 2, nullable = false)
    private BigDecimal saldoRetenido = BigDecimal.ZERO;

    @Column(name = "moneda", length = 5, nullable = false)
    private String moneda = "PEN";

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime updatedAt;

    public Wallet() {}

    // MEJORA: factory method para crear wallet inicial con valores por defecto
    public static Wallet createDefault(UserEntities usuario) {
        Wallet w = new Wallet();
        w.usuario = usuario;
        w.saldoDisponible = BigDecimal.ZERO;
        w.saldoRetenido   = BigDecimal.ZERO;
        w.moneda          = "PEN";
        return w;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UserEntities getUsuario() { return usuario; }
    public void setUsuario(UserEntities usuario) { this.usuario = usuario; }

    public BigDecimal getSaldoDisponible() { return saldoDisponible; }
    public void setSaldoDisponible(BigDecimal saldoDisponible) { this.saldoDisponible = saldoDisponible; }

    public BigDecimal getSaldoRetenido() { return saldoRetenido; }
    public void setSaldoRetenido(BigDecimal saldoRetenido) { this.saldoRetenido = saldoRetenido; }

    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }

    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
