package com.foodgest.reputacion.dtos;

import com.foodgest.reputacion.entities.Calificacion;

import java.time.OffsetDateTime;
import java.util.UUID;

public class CalificacionResponseDto {

    private UUID id;
    private UUID pedidoId;
    private UUID calificadorId;
    private UUID calificadoId;
    private Integer puntuacion;
    private String comentario;
    private String tipo;
    private Boolean verificado;
    private OffsetDateTime createdAt;

    public static CalificacionResponseDto from(Calificacion calificacion) {
        CalificacionResponseDto dto = new CalificacionResponseDto();
        dto.id = calificacion.getId();
        dto.pedidoId = calificacion.getPedido().getId();
        dto.calificadorId = calificacion.getCalificador().getId();
        dto.calificadoId = calificacion.getCalificado().getId();
        dto.puntuacion = calificacion.getPuntuacion();
        dto.comentario = calificacion.getComentario();
        dto.tipo = calificacion.getTipo();
        dto.verificado = calificacion.getVerificado();
        dto.createdAt = calificacion.getCreatedAt();
        return dto;
    }

    public UUID getId() { return id; }
    public UUID getPedidoId() { return pedidoId; }
    public UUID getCalificadorId() { return calificadorId; }
    public UUID getCalificadoId() { return calificadoId; }
    public Integer getPuntuacion() { return puntuacion; }
    public String getComentario() { return comentario; }
    public String getTipo() { return tipo; }
    public Boolean getVerificado() { return verificado; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
}

