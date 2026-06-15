package com.foodgest.pedidos.entities;

import com.foodgest.marketplace.ofertas.entities.Oferta;
import com.foodgest.perfiles.compradores.entities.Comprador;
import com.foodgest.perfiles.transportistas.entities.TransportistaEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oferta_id", nullable = false)
    private Oferta oferta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comprador_id", nullable = false)
    private Comprador comprador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transportista_id")
    private TransportistaEntity transportista;

    @Column(name = "estado", nullable = false)
    @ColumnTransformer(write = "?::estado_pedido_enum")
    private String estado = "pendiente_pago";

    @Column(name = "precio_acordado", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioAcordado;

    @Column(name = "cantidad_acordada", precision = 10, scale = 2, nullable = false)
    private BigDecimal cantidadAcordada;

    @Column(name = "subtotal", precision = 12, scale = 2, nullable = false)
    private BigDecimal subtotal;

    @Column(name = "costo_envio", precision = 12, scale = 2)
    private BigDecimal costoEnvio = BigDecimal.ZERO;

    @Column(name = "comision_plataforma", precision = 12, scale = 2)
    private BigDecimal comisionPlataforma = BigDecimal.ZERO;

    @Column(name = "total", precision = 12, scale = 2, nullable = false)
    private BigDecimal total;

    @Column(name = "moneda", length = 5)
    private String moneda = "PEN";

    @Column(name = "metodo_pago", length = 30)
    private String metodoPago;

    @Column(name = "origen_direccion", length = 255)
    private String origenDireccion;

    @Column(name = "origen_latitud", precision = 9, scale = 6)
    private BigDecimal origenLatitud;

    @Column(name = "origen_longitud", precision = 9, scale = 6)
    private BigDecimal origenLongitud;

    @Column(name = "destino_nombre", length = 100)
    private String destinoNombre;

    @Column(name = "direccion_entrega", columnDefinition = "TEXT")
    private String direccionEntrega;

    @Column(name = "latitud_entrega", precision = 9, scale = 6)
    private BigDecimal latitudEntrega;

    @Column(name = "longitud_entrega", precision = 9, scale = 6)
    private BigDecimal longitudEntrega;

    @Column(name = "guia_remision_url", length = 255)
    private String guiaRemisionUrl;

    @Column(name = "guia_remision_numero", length = 50)
    private String guiaRemisionNumero;

    @Column(name = "foto_entrega_url", length = 255)
    private String fotoEntregaUrl;

    @Column(name = "codigo_seguimiento", length = 30, unique = true)
    private String codigoSeguimiento;

    @CreationTimestamp
    @Column(name = "fecha_pedido", updatable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime fechaPedido;

    @Column(name = "fecha_confirmacion", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime fechaConfirmacion;

    @Column(name = "fecha_entrega_estimada", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime fechaEntregaEstimada;

    @Column(name = "fecha_entrega_real", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime fechaEntregaReal;

    @Column(name = "notas_comprador", columnDefinition = "TEXT")
    private String notasComprador;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime updatedAt;

    public Pedido() {}

    // getters / setters omitted for brevity (IDE will generate)
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Oferta getOferta() { return oferta; }
    public void setOferta(Oferta oferta) { this.oferta = oferta; }

    public Comprador getComprador() { return comprador; }
    public void setComprador(Comprador comprador) { this.comprador = comprador; }

    public TransportistaEntity getTransportista() { return transportista; }
    public void setTransportista(TransportistaEntity transportista) { this.transportista = transportista; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public BigDecimal getPrecioAcordado() { return precioAcordado; }
    public void setPrecioAcordado(BigDecimal precioAcordado) { this.precioAcordado = precioAcordado; }

    public BigDecimal getCantidadAcordada() { return cantidadAcordada; }
    public void setCantidadAcordada(BigDecimal cantidadAcordada) { this.cantidadAcordada = cantidadAcordada; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getCostoEnvio() { return costoEnvio; }
    public void setCostoEnvio(BigDecimal costoEnvio) { this.costoEnvio = costoEnvio; }

    public BigDecimal getComisionPlataforma() { return comisionPlataforma; }
    public void setComisionPlataforma(BigDecimal comisionPlataforma) { this.comisionPlataforma = comisionPlataforma; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getOrigenDireccion() { return origenDireccion; }
    public void setOrigenDireccion(String origenDireccion) { this.origenDireccion = origenDireccion; }

    public BigDecimal getOrigenLatitud() { return origenLatitud; }
    public void setOrigenLatitud(BigDecimal origenLatitud) { this.origenLatitud = origenLatitud; }

    public BigDecimal getOrigenLongitud() { return origenLongitud; }
    public void setOrigenLongitud(BigDecimal origenLongitud) { this.origenLongitud = origenLongitud; }

    public String getDestinoNombre() { return destinoNombre; }
    public void setDestinoNombre(String destinoNombre) { this.destinoNombre = destinoNombre; }

    public String getDireccionEntrega() { return direccionEntrega; }
    public void setDireccionEntrega(String direccionEntrega) { this.direccionEntrega = direccionEntrega; }

    public BigDecimal getLatitudEntrega() { return latitudEntrega; }
    public void setLatitudEntrega(BigDecimal latitudEntrega) { this.latitudEntrega = latitudEntrega; }

    public BigDecimal getLongitudEntrega() { return longitudEntrega; }
    public void setLongitudEntrega(BigDecimal longitudEntrega) { this.longitudEntrega = longitudEntrega; }

    public String getGuiaRemisionUrl() { return guiaRemisionUrl; }
    public void setGuiaRemisionUrl(String guiaRemisionUrl) { this.guiaRemisionUrl = guiaRemisionUrl; }

    public String getGuiaRemisionNumero() { return guiaRemisionNumero; }
    public void setGuiaRemisionNumero(String guiaRemisionNumero) { this.guiaRemisionNumero = guiaRemisionNumero; }

    public String getFotoEntregaUrl() { return fotoEntregaUrl; }
    public void setFotoEntregaUrl(String fotoEntregaUrl) { this.fotoEntregaUrl = fotoEntregaUrl; }

    public String getCodigoSeguimiento() { return codigoSeguimiento; }
    public void setCodigoSeguimiento(String codigoSeguimiento) { this.codigoSeguimiento = codigoSeguimiento; }

    public OffsetDateTime getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(OffsetDateTime fechaPedido) { this.fechaPedido = fechaPedido; }

    public OffsetDateTime getFechaConfirmacion() { return fechaConfirmacion; }
    public void setFechaConfirmacion(OffsetDateTime fechaConfirmacion) { this.fechaConfirmacion = fechaConfirmacion; }

    public OffsetDateTime getFechaEntregaEstimada() { return fechaEntregaEstimada; }
    public void setFechaEntregaEstimada(OffsetDateTime fechaEntregaEstimada) { this.fechaEntregaEstimada = fechaEntregaEstimada; }

    public OffsetDateTime getFechaEntregaReal() { return fechaEntregaReal; }
    public void setFechaEntregaReal(OffsetDateTime fechaEntregaReal) { this.fechaEntregaReal = fechaEntregaReal; }

    public String getNotasComprador() { return notasComprador; }
    public void setNotasComprador(String notasComprador) { this.notasComprador = notasComprador; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
 

