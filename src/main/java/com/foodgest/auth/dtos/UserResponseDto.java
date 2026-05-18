package com.foodgest.auth.dtos;

import com.foodgest.users.entities.UserEntities;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO de respuesta para datos publicos del usuario tras el registro.
 * NOTA DE SEGURIDAD: no incluye passwordHash, cuentaBancaria ni banco.
 */
public class UserResponseDto {

    private UUID id;
    private String nombre;
    private String email;
    private String tipoUsuario;
    private String estado;
    private Boolean verificado;
    private LocalDateTime createdAt;

    public static UserResponseDto from(UserEntities u) {
        UserResponseDto dto = new UserResponseDto();
        dto.id          = u.getId();
        dto.nombre      = u.getNombre();
        dto.email       = u.getEmail();
        dto.tipoUsuario = u.getTipoUsuario();
        dto.estado      = u.getEstado();
        dto.verificado  = u.getVerificado();
        dto.createdAt   = u.getCreatedAt();
        return dto;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Boolean getVerificado() { return verificado; }
    public void setVerificado(Boolean verificado) { this.verificado = verificado; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
