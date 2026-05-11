package com.foodgest.perfiles.compradores.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

public class CompradorCreateDto {

    @NotNull(message = "El usuario es obligatorio")
    private UUID usuarioId;

    @NotNull(message = "El tipo de comprador es obligatorio")
    @Pattern(regexp = "particular|empresa|restaurante|supermercado", message = "Tipo de comprador no válido")
    private String tipoComprador;

    @Size(max = 150)
    private String razonSocial;

    @Size(max = 11)
    private String ruc;

    @Size(max = 200)
    private String direccionEntregaDefault;

    private BigDecimal latitudEntrega;

    private BigDecimal longitudEntrega;

    private BigDecimal limiteCredito;

    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }

    public String getTipoComprador() { return tipoComprador; }
    public void setTipoComprador(String tipoComprador) { this.tipoComprador = tipoComprador; }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public String getRuc() { return ruc; }
    public void setRuc(String ruc) { this.ruc = ruc; }

    public String getDireccionEntregaDefault() { return direccionEntregaDefault; }
    public void setDireccionEntregaDefault(String direccionEntregaDefault) { this.direccionEntregaDefault = direccionEntregaDefault; }

    public BigDecimal getLatitudEntrega() { return latitudEntrega; }
    public void setLatitudEntrega(BigDecimal latitudEntrega) { this.latitudEntrega = latitudEntrega; }

    public BigDecimal getLongitudEntrega() { return longitudEntrega; }
    public void setLongitudEntrega(BigDecimal longitudEntrega) { this.longitudEntrega = longitudEntrega; }

    public BigDecimal getLimiteCredito() { return limiteCredito; }
    public void setLimiteCredito(BigDecimal limiteCredito) { this.limiteCredito = limiteCredito; }
}
