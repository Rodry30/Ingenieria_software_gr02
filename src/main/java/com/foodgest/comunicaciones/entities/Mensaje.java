package com.foodgest.comunicaciones.entities;

import com.foodgest.marketplace.ofertas.entities.Oferta;
import com.foodgest.pedidos.entities.Pedido;
import com.foodgest.catalogo.entities.Producto;
import com.foodgest.users.entities.UserEntities;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "mensajes")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "remitente_id", nullable = false)
    private UserEntities remitente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destinatario_id")
    private UserEntities destinatario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oferta_id")
    private Oferta oferta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @Column(name = "contenido", columnDefinition = "TEXT")
    private String contenido;

    @Column(name = "tipo_archivo", length = 20)
    private String tipoArchivo;

    @Column(name = "es_grupo")
    private Boolean esGrupo = false;

    @Column(name = "nombre_grupo", length = 100)
    private String nombreGrupo;

    @Column(name = "leido")
    private Boolean leido = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime createdAt;

    public Mensaje() {}

    // getters / setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UserEntities getRemitente() { return remitente; }
    public void setRemitente(UserEntities remitente) { this.remitente = remitente; }
    public UserEntities getDestinatario() { return destinatario; }
    public void setDestinatario(UserEntities destinatario) { this.destinatario = destinatario; }
    public Oferta getOferta() { return oferta; }
    public void setOferta(Oferta oferta) { this.oferta = oferta; }
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    public String getTipoArchivo() { return tipoArchivo; }
    public void setTipoArchivo(String tipoArchivo) { this.tipoArchivo = tipoArchivo; }
    public Boolean getEsGrupo() { return esGrupo; }
    public void setEsGrupo(Boolean esGrupo) { this.esGrupo = esGrupo; }
    public String getNombreGrupo() { return nombreGrupo; }
    public void setNombreGrupo(String nombreGrupo) { this.nombreGrupo = nombreGrupo; }
    public Boolean getLeido() { return leido; }
    public void setLeido(Boolean leido) { this.leido = leido; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
 

