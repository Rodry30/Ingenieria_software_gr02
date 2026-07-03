package com.foodgest.reputacion.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class CalificacionCreateDto {

    @NotNull(message = "El pedido_id es obligatorio")
    private UUID pedidoId;

    @NotNull(message = "El calificador_id es obligatorio")
    private UUID calificadorId;

    @NotNull(message = "El calificado_id es obligatorio")
    private UUID calificadoId;

    @NotNull(message = "La puntuacion es obligatoria")
    @Min(value = 1, message = "La puntuacion minima es 1")
    @Max(value = 5, message = "La puntuacion maxima es 5")
    private Integer puntuacion;

    private String comentario;

    @NotBlank(message = "El tipo es obligatorio")
    @Size(max = 30, message = "El tipo no puede superar los 30 caracteres")
    private String tipo;

    public UUID getPedidoId() { return pedidoId; }
    public void setPedidoId(UUID pedidoId) { this.pedidoId = pedidoId; }
    public UUID getCalificadorId() { return calificadorId; }
    public void setCalificadorId(UUID calificadorId) { this.calificadorId = calificadorId; }
    public UUID getCalificadoId() { return calificadoId; }
    public void setCalificadoId(UUID calificadoId) { this.calificadoId = calificadoId; }
    public Integer getPuntuacion() { return puntuacion; }
    public void setPuntuacion(Integer puntuacion) { this.puntuacion = puntuacion; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}

