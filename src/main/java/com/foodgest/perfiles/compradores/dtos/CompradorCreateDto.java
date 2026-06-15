package com.foodgest.perfiles.compradores.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.foodgest.perfiles.compradores.entities.Comprador;
import com.foodgest.perfiles.compradores.enums.TipoCompradorEnum;
import com.foodgest.users.entities.UserEntities;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompradorCreateDto {

    @NotNull(message = "El tipo de comprador es obligatorio")
    private TipoCompradorEnum tipoComprador;

    private String razonSocial;

    @Size(max = 15, message = "El RUC no puede superar los 15 caracteres")
    private String ruc;

    @Size(max = 255, message = "La direccion de entrega no puede superar los 255 caracteres")
    private String direccionEntregaDefault;

    @DecimalMin(value = "-90.0",  message = "La latitud minima es -90.0")
    @DecimalMax(value = "90.0",   message = "La latitud maxima es 90.0")
    private BigDecimal latitudEntrega;

    @DecimalMin(value = "-180.0", message = "La longitud minima es -180.0")
    @DecimalMax(value = "180.0",  message = "La longitud maxima es 180.0")
    private BigDecimal longitudEntrega;

    private UUID usuarioId;

    private BigDecimal limiteCredito;

    public Comprador toEntity(UserEntities usuario) {
        Comprador c = new Comprador();
        c.setUsuario(usuario);
        c.setTipoComprador(this.tipoComprador);
        c.setRazonSocial(this.razonSocial);
        c.setRuc(this.ruc);
        c.setDireccionEntregaDefault(this.direccionEntregaDefault);
        c.setLatitudEntrega(this.latitudEntrega);
        c.setLongitudEntrega(this.longitudEntrega);
        c.setLimiteCredito(this.limiteCredito == null ? null : this.limiteCredito);
        return c;
    }

    public void applyTo(Comprador c) {
        if (this.tipoComprador != null)          c.setTipoComprador(this.tipoComprador);
        if (this.razonSocial != null)            c.setRazonSocial(this.razonSocial);
        if (this.ruc != null)                    c.setRuc(this.ruc);
        if (this.direccionEntregaDefault != null) c.setDireccionEntregaDefault(this.direccionEntregaDefault);
        if (this.latitudEntrega != null)         c.setLatitudEntrega(this.latitudEntrega);
        if (this.longitudEntrega != null)        c.setLongitudEntrega(this.longitudEntrega);
    }

    public TipoCompradorEnum getTipoComprador() { return tipoComprador; }
    public void setTipoComprador(TipoCompradorEnum tipoComprador) { this.tipoComprador = tipoComprador; }
    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
    public String getRuc() { return ruc; }
    public void setRuc(String ruc) { this.ruc = ruc; }
    public String getDireccionEntregaDefault() { return direccionEntregaDefault; }
    public void setDireccionEntregaDefault(String d) { this.direccionEntregaDefault = d; }
    public BigDecimal getLatitudEntrega() { return latitudEntrega; }
    public void setLatitudEntrega(BigDecimal latitudEntrega) { this.latitudEntrega = latitudEntrega; }
    public BigDecimal getLongitudEntrega() { return longitudEntrega; }
    public void setLongitudEntrega(BigDecimal longitudEntrega) { this.longitudEntrega = longitudEntrega; }

    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }

    public BigDecimal getLimiteCredito() { return limiteCredito; }
    public void setLimiteCredito(BigDecimal limiteCredito) { this.limiteCredito = limiteCredito; }
}
