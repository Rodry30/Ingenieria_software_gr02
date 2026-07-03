package com.foodgest.subastas.subastas.dtos;

import jakarta.validation.constraints.NotBlank;

public class SubastaEstadoDto {

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}

