package com.foodgest.reputacion.controllers;

import com.foodgest.reputacion.dtos.CalificacionCreateDto;
import com.foodgest.reputacion.dtos.CalificacionResponseDto;
import com.foodgest.reputacion.dtos.ReputacionUsuarioDto;
import com.foodgest.reputacion.servicesinterfaces.ICalificacionService;
import com.foodgest.shared.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/reputacion")
public class CalificacionController {

    private final ICalificacionService calificacionService;

    public CalificacionController(ICalificacionService calificacionService) {
        this.calificacionService = calificacionService;
    }

    @PostMapping("/calificar")
    public ResponseEntity<ApiResponse<CalificacionResponseDto>> calificar(@Valid @RequestBody CalificacionCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "Calificacion registrada.", calificacionService.calificar(dto)));
    }

    @GetMapping("/usuarios/{usuarioId}")
    public ResponseEntity<ApiResponse<ReputacionUsuarioDto>> listarPorUsuario(@PathVariable UUID usuarioId) {
        ReputacionUsuarioDto reputacion = calificacionService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(ApiResponse.success(200, "Reputacion de usuario obtenida.", reputacion));
    }
}

