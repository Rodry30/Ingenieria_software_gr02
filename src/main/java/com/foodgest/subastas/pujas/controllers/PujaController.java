package com.foodgest.subastas.pujas.controllers;

import com.foodgest.shared.response.ApiResponse;
import com.foodgest.subastas.pujas.dtos.PujaCreateDto;
import com.foodgest.subastas.pujas.dtos.PujaResponseDto;
import com.foodgest.subastas.pujas.servicesinterfaces.IPujaService;
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
@RequestMapping("/api/subastas")
public class PujaController {

    private final IPujaService pujaService;

    public PujaController(IPujaService pujaService) {
        this.pujaService = pujaService;
    }

    @PostMapping("/{id}/pujar")
    @PreAuthorize("hasRole('COMPRADOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PujaResponseDto>> pujar(@PathVariable UUID id,
                                                               @Valid @RequestBody PujaCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "Puja registrada.", pujaService.pujar(id, dto)));
    }

    @GetMapping("/{id}/pujas")
    public ResponseEntity<List<PujaResponseDto>> listarPorSubasta(@PathVariable UUID id) {
        return ResponseEntity.ok(pujaService.listarPorSubasta(id));
    }
}

