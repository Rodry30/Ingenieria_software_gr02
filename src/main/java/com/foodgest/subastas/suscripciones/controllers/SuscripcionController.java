package com.foodgest.subastas.suscripciones.controllers;

import com.foodgest.shared.response.ApiResponse;
import com.foodgest.subastas.suscripciones.dtos.SuscripcionCreateDto;
import com.foodgest.subastas.suscripciones.dtos.SuscripcionResponseDto;
import com.foodgest.subastas.suscripciones.dtos.SuscripcionUpdateDto;
import com.foodgest.subastas.suscripciones.servicesinterfaces.ISuscripcionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/subastas/suscripciones")
public class SuscripcionController {

    private final ISuscripcionService suscripcionService;

    public SuscripcionController(ISuscripcionService suscripcionService) {
        this.suscripcionService = suscripcionService;
    }

    @GetMapping
    public ResponseEntity<List<SuscripcionResponseDto>> list(@RequestParam(required = false) String estado) {
        return ResponseEntity.ok(suscripcionService.list(estado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuscripcionResponseDto> listId(@PathVariable UUID id) {
        return ResponseEntity.ok(suscripcionService.listId(id));
    }

    @GetMapping("/comprador/{compradorId}")
    public ResponseEntity<List<SuscripcionResponseDto>> listByComprador(@PathVariable UUID compradorId) {
        return ResponseEntity.ok(suscripcionService.listByComprador(compradorId));
    }

    @GetMapping("/agricultor/{agricultorId}")
    public ResponseEntity<List<SuscripcionResponseDto>> listByAgricultor(@PathVariable UUID agricultorId) {
        return ResponseEntity.ok(suscripcionService.listByAgricultor(agricultorId));
    }

    @PostMapping
    @PreAuthorize("hasRole('COMPRADOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SuscripcionResponseDto>> crear(@Valid @RequestBody SuscripcionCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "Suscripcion creada.", suscripcionService.crear(dto)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COMPRADOR') or hasRole('ADMIN')")
    public ResponseEntity<SuscripcionResponseDto> actualizar(@PathVariable UUID id,
                                                             @Valid @RequestBody SuscripcionUpdateDto dto) {
        return ResponseEntity.ok(suscripcionService.actualizar(id, dto));
    }

    @PutMapping("/{id}/cancelar")
    @PreAuthorize("hasRole('COMPRADOR') or hasRole('ADMIN')")
    public ResponseEntity<SuscripcionResponseDto> cancelar(@PathVariable UUID id) {
        return ResponseEntity.ok(suscripcionService.cancelar(id));
    }
}

