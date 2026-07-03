package com.foodgest.pedidos.dtos;

import jakarta.validation.constraints.NotBlank;

public class PedidoEstadoUpdateDto {

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    private String guiaRemisionUrl;
    private String guiaRemisionNumero;
    private String fotoEntregaUrl;

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getGuiaRemisionUrl() { return guiaRemisionUrl; }
    public void setGuiaRemisionUrl(String guiaRemisionUrl) { this.guiaRemisionUrl = guiaRemisionUrl; }
    public String getGuiaRemisionNumero() { return guiaRemisionNumero; }
    public void setGuiaRemisionNumero(String guiaRemisionNumero) { this.guiaRemisionNumero = guiaRemisionNumero; }
    public String getFotoEntregaUrl() { return fotoEntregaUrl; }
    public void setFotoEntregaUrl(String fotoEntregaUrl) { this.fotoEntregaUrl = fotoEntregaUrl; }
}

