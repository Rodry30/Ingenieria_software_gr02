package com.foodgest.subastas.subastas.controllers;

import com.foodgest.shared.response.ApiResponse;
import com.foodgest.subastas.subastas.dtos.SubastaCreateDto;
import com.foodgest.subastas.subastas.dtos.SubastaEstadoDto;
import com.foodgest.subastas.subastas.dtos.SubastaResponseDto;
import com.foodgest.subastas.subastas.servicesinterfaces.ISubastaService;
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
@RequestMapping("/api/subastas")
public class SubastaController {

    private final ISubastaService subastaService;

    public SubastaController(ISubastaService subastaService) {
        this.subastaService = subastaService;
    }

    @GetMapping
    public ResponseEntity<List<SubastaResponseDto>> list(@RequestParam(required = false) String estado) {
        return ResponseEntity.ok(subastaService.list(estado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubastaResponseDto> listId(@PathVariable UUID id) {
        return ResponseEntity.ok(subastaService.listId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('AGRICULTOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SubastaResponseDto>> crear(@Valid @RequestBody SubastaCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "Subasta creada.", subastaService.crear(dto)));
    }

    @PutMapping("/{id}/estado")
    @PreAuthorize("hasRole('AGRICULTOR') or hasRole('ADMIN')")
    public ResponseEntity<SubastaResponseDto> cambiarEstado(@PathVariable UUID id,
                                                             @Valid @RequestBody SubastaEstadoDto dto) {
        return ResponseEntity.ok(subastaService.cambiarEstado(id, dto));
    }
}

