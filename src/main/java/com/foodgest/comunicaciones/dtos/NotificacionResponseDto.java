package com.foodgest.comunicaciones.dtos;

import com.foodgest.comunicaciones.entities.Notificacion;

import java.time.OffsetDateTime;
import java.util.UUID;

public class NotificacionResponseDto {

    private UUID id;
    private UUID usuarioId;
    private String tipo;
    private String titulo;
    private String mensaje;
    private Boolean leido;
    private String data;
    private OffsetDateTime createdAt;

    public static NotificacionResponseDto from(Notificacion notificacion) {
        NotificacionResponseDto dto = new NotificacionResponseDto();
        dto.id = notificacion.getId();
        dto.usuarioId = notificacion.getUsuario().getId();
        dto.tipo = notificacion.getTipo();
        dto.titulo = notificacion.getTitulo();
        dto.mensaje = notificacion.getMensaje();
        dto.leido = notificacion.getLeido();
        dto.data = notificacion.getData();
        dto.createdAt = notificacion.getCreatedAt();
        return dto;
    }

    public UUID getId() { return id; }
    public UUID getUsuarioId() { return usuarioId; }
    public String getTipo() { return tipo; }
    public String getTitulo() { return titulo; }
    public String getMensaje() { return mensaje; }
    public Boolean getLeido() { return leido; }
    public String getData() { return data; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
}

