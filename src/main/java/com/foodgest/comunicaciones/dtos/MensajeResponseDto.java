package com.foodgest.comunicaciones.dtos;

import com.foodgest.comunicaciones.entities.Mensaje;

import java.time.OffsetDateTime;
import java.util.UUID;

public class MensajeResponseDto {

    private UUID id;
    private UUID remitenteId;
    private UUID destinatarioId;
    private UUID ofertaId;
    private UUID pedidoId;
    private UUID productoId;
    private String contenido;
    private String tipoArchivo;
    private Boolean esGrupo;
    private String nombreGrupo;
    private Boolean leido;
    private OffsetDateTime createdAt;

    public static MensajeResponseDto from(Mensaje mensaje) {
        MensajeResponseDto dto = new MensajeResponseDto();
        dto.id = mensaje.getId();
        dto.remitenteId = mensaje.getRemitente().getId();
        dto.destinatarioId = mensaje.getDestinatario() != null ? mensaje.getDestinatario().getId() : null;
        dto.ofertaId = mensaje.getOferta() != null ? mensaje.getOferta().getId() : null;
        dto.pedidoId = mensaje.getPedido() != null ? mensaje.getPedido().getId() : null;
        dto.productoId = mensaje.getProducto() != null ? mensaje.getProducto().getId() : null;
        dto.contenido = mensaje.getContenido();
        dto.tipoArchivo = mensaje.getTipoArchivo();
        dto.esGrupo = mensaje.getEsGrupo();
        dto.nombreGrupo = mensaje.getNombreGrupo();
        dto.leido = mensaje.getLeido();
        dto.createdAt = mensaje.getCreatedAt();
        return dto;
    }

    public UUID getId() { return id; }
    public UUID getRemitenteId() { return remitenteId; }
    public UUID getDestinatarioId() { return destinatarioId; }
    public UUID getOfertaId() { return ofertaId; }
    public UUID getPedidoId() { return pedidoId; }
    public UUID getProductoId() { return productoId; }
    public String getContenido() { return contenido; }
    public String getTipoArchivo() { return tipoArchivo; }
    public Boolean getEsGrupo() { return esGrupo; }
    public String getNombreGrupo() { return nombreGrupo; }
    public Boolean getLeido() { return leido; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
}

