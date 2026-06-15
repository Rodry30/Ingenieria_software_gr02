package com.foodgest.reputacion.entities;

import com.foodgest.pedidos.entities.Pedido;
import com.foodgest.users.entities.UserEntities;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "calificaciones", uniqueConstraints = @UniqueConstraint(columnNames = {"pedido_id", "calificador_id"}))
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calificador_id", nullable = false)
    private UserEntities calificador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calificado_id", nullable = false)
    private UserEntities calificado;

    @Column(name = "puntuacion", nullable = false)
    private Integer puntuacion;

    @Column(name = "comentario", columnDefinition = "TEXT")
    private String comentario;

    @Column(name = "tipo", length = 30, nullable = false)
    private String tipo;

    @Column(name = "verificado")
    private Boolean verificado = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime createdAt;

    public Calificacion() {}

    // getters / setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
    public UserEntities getCalificador() { return calificador; }
    public void setCalificador(UserEntities calificador) { this.calificador = calificador; }
    public UserEntities getCalificado() { return calificado; }
    public void setCalificado(UserEntities calificado) { this.calificado = calificado; }
    public Integer getPuntuacion() { return puntuacion; }
    public void setPuntuacion(Integer puntuacion) { this.puntuacion = puntuacion; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Boolean getVerificado() { return verificado; }
    public void setVerificado(Boolean verificado) { this.verificado = verificado; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
 

