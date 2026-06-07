package com.foodgest.perfiles.compradores.dtos;

import com.foodgest.perfiles.compradores.enums.TipoCompradorEnum;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class CompradorUpdateDto {

    private TipoCompradorEnum tipoComprador;

    @Size(max = 150)
    private String razonSocial;

    @Size(max = 15)
    private String ruc;

    @Size(max = 255)
    private String direccionEntregaDefault;

    private BigDecimal latitudEntrega;

    private BigDecimal longitudEntrega;

    private BigDecimal limiteCredito;

    public TipoCompradorEnum getTipoComprador() { return tipoComprador; }
    public void setTipoComprador(TipoCompradorEnum tipoComprador) { this.tipoComprador = tipoComprador; }

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
