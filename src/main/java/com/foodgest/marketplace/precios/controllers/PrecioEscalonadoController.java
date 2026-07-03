package com.foodgest.marketplace.precios.controllers;

import com.foodgest.marketplace.precios.dtos.PrecioEscalonadoDto;
import com.foodgest.marketplace.precios.servicesinterfaces.IPrecioEscalonadoService;
import com.foodgest.shared.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/marketplace/precios-escalonados")
public class PrecioEscalonadoController {

    private final IPrecioEscalonadoService precioEscalonadoService;

    public PrecioEscalonadoController(IPrecioEscalonadoService precioEscalonadoService) {
        this.precioEscalonadoService = precioEscalonadoService;
    }

    @GetMapping("/oferta/{ofertaId}")
    public ResponseEntity<List<PrecioEscalonadoDto>> listByOferta(@PathVariable UUID ofertaId) {
        return ResponseEntity.ok(precioEscalonadoService.listByOferta(ofertaId));
    }

    @PostMapping
    @PreAuthorize("hasRole('AGRICULTOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PrecioEscalonadoDto>> crear(@Valid @RequestBody PrecioEscalonadoDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "Precio escalonado creado.", precioEscalonadoService.crear(dto)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('AGRICULTOR') or hasRole('ADMIN')")
    public ResponseEntity<PrecioEscalonadoDto> actualizar(@PathVariable UUID id,
                                                          @Valid @RequestBody PrecioEscalonadoDto dto) {
        return ResponseEntity.ok(precioEscalonadoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('AGRICULTOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        precioEscalonadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

