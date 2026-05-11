package com.foodgest.perfiles.compradores.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CompradorResponseDto {

    private UUID id;
    private String tipoComprador;
    private String razonSocial;
    private String ruc;
    private String direccionEntregaDefault;
    private BigDecimal latitudEntrega;
    private BigDecimal longitudEntrega;
    private BigDecimal limiteCredito;
    private LocalDateTime createdAt;

    private UsuarioResumen usuario;

    public static class UsuarioResumen {
        private UUID id;
        private String nombre;
        private String email;
        private String telefono;

        public UUID getId() { return id; }
        public void setId(UUID id) { this.id = id; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getTelefono() { return telefono; }
        public void setTelefono(String telefono) { this.telefono = telefono; }
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTipoComprador() { return tipoComprador; }
    public void setTipoComprador(String tipoComprador) { this.tipoComprador = tipoComprador; }

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

    public BigDecimal getLimiteCredito() { return limiteCredito; }
    public void setLimiteCredito(BigDecimal limiteCredito) { this.limiteCredito = limiteCredito; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public UsuarioResumen getUsuario() { return usuario; }
    public void setUsuario(UsuarioResumen usuario) { this.usuario = usuario; }
}
