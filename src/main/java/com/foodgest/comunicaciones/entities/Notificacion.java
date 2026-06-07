package com.foodgest.comunicaciones.entities;

import com.foodgest.users.entities.UserEntities;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "notificaciones")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UserEntities usuario;

    @Column(name = "tipo", length = 50, nullable = false)
    private String tipo;

    @Column(name = "titulo", length = 200, nullable = false)
    private String titulo;

    @Column(name = "mensaje", columnDefinition = "TEXT")
    private String mensaje;

    @Column(name = "leido")
    private Boolean leido = false;

    @Column(name = "data", columnDefinition = "JSONB")
    private String data = "{}";

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime createdAt;

    public Notificacion() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UserEntities getUsuario() { return usuario; }
    public void setUsuario(UserEntities usuario) { this.usuario = usuario; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public Boolean getLeido() { return leido; }
    public void setLeido(Boolean leido) { this.leido = leido; }
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
 

