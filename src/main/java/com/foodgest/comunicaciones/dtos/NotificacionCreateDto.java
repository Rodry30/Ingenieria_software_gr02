package com.foodgest.comunicaciones.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class NotificacionCreateDto {

    @NotNull(message = "El usuario_id es obligatorio")
    private UUID usuarioId;

    @NotBlank(message = "El tipo es obligatorio")
    @Size(max = 50, message = "El tipo no puede superar los 50 caracteres")
    private String tipo;

    @NotBlank(message = "El titulo es obligatorio")
    @Size(max = 200, message = "El titulo no puede superar los 200 caracteres")
    private String titulo;

    private String mensaje;
    private String data = "{}";

    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
}

