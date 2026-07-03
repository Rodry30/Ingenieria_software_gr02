package com.foodgest.logistica.controllers;

import com.foodgest.logistica.dtos.TrackingCreateDto;
import com.foodgest.logistica.dtos.TrackingResponseDto;
import com.foodgest.logistica.servicesinterfaces.ITrackingService;
import com.foodgest.shared.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/logistica/tracking")
public class TrackingController {

    private final ITrackingService trackingService;

    public TrackingController(ITrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @PostMapping
    @PreAuthorize("hasRole('TRANSPORTISTA') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<TrackingResponseDto>> registrar(@Valid @RequestBody TrackingCreateDto dto) {
        TrackingResponseDto response = trackingService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "Ubicacion registrada.", response));
    }

    @GetMapping("/{pedidoId}")
    public ResponseEntity<List<TrackingResponseDto>> historial(@PathVariable UUID pedidoId) {
        return ResponseEntity.ok(trackingService.historialPorPedido(pedidoId));
    }

    @GetMapping("/{pedidoId}/ultima")
    public ResponseEntity<TrackingResponseDto> ultima(@PathVariable UUID pedidoId) {
        return ResponseEntity.ok(trackingService.ultimaUbicacion(pedidoId));
    }
}

