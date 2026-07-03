package com.foodgest.comunicaciones.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class MensajeCreateDto {

    @NotNull(message = "El remitente_id es obligatorio")
    private UUID remitenteId;

    private UUID destinatarioId;
    private UUID ofertaId;
    private UUID pedidoId;
    private UUID productoId;

    @NotBlank(message = "El contenido es obligatorio")
    private String contenido;

    @Size(max = 20, message = "El tipo de archivo no puede superar los 20 caracteres")
    private String tipoArchivo;

    private Boolean esGrupo = false;

    @Size(max = 100, message = "El nombre del grupo no puede superar los 100 caracteres")
    private String nombreGrupo;

    public UUID getRemitenteId() { return remitenteId; }
    public void setRemitenteId(UUID remitenteId) { this.remitenteId = remitenteId; }
    public UUID getDestinatarioId() { return destinatarioId; }
    public void setDestinatarioId(UUID destinatarioId) { this.destinatarioId = destinatarioId; }
    public UUID getOfertaId() { return ofertaId; }
    public void setOfertaId(UUID ofertaId) { this.ofertaId = ofertaId; }
    public UUID getPedidoId() { return pedidoId; }
    public void setPedidoId(UUID pedidoId) { this.pedidoId = pedidoId; }
    public UUID getProductoId() { return productoId; }
    public void setProductoId(UUID productoId) { this.productoId = productoId; }
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    public String getTipoArchivo() { return tipoArchivo; }
    public void setTipoArchivo(String tipoArchivo) { this.tipoArchivo = tipoArchivo; }
    public Boolean getEsGrupo() { return esGrupo; }
    public void setEsGrupo(Boolean esGrupo) { this.esGrupo = esGrupo; }
    public String getNombreGrupo() { return nombreGrupo; }
    public void setNombreGrupo(String nombreGrupo) { this.nombreGrupo = nombreGrupo; }
}

