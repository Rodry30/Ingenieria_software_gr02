package com.foodgest.reputacion.dtos;

import java.util.List;

public class ReputacionUsuarioDto {

    private Double promedioEstrellas;
    private Integer totalResenas;
    private List<CalificacionResponseDto> calificaciones;

    public static ReputacionUsuarioDto of(Double promedioEstrellas, Integer totalResenas,
                                           List<CalificacionResponseDto> calificaciones) {
        ReputacionUsuarioDto dto = new ReputacionUsuarioDto();
        dto.promedioEstrellas = promedioEstrellas;
        dto.totalResenas = totalResenas;
        dto.calificaciones = calificaciones;
        return dto;
    }

    public Double getPromedioEstrellas() {
        return promedioEstrellas;
    }

    public Integer getTotalResenas() {
        return totalResenas;
    }

    public List<CalificacionResponseDto> getCalificaciones() {
        return calificaciones;
    }
}
