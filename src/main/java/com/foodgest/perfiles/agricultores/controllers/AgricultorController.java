package com.foodgest.perfiles.agricultores.controllers;

import com.foodgest.perfiles.agricultores.dtos.AgricultorCreateDto;
import com.foodgest.perfiles.agricultores.dtos.AgricultorResponseDto;
import com.foodgest.perfiles.agricultores.dtos.AgricultorUpdateDto;
import com.foodgest.perfiles.agricultores.servicesinterfaces.IAgricultorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/agricultores")
@Tag(name = "Agricultores", description = "Gestión de perfiles de agricultores")
public class AgricultorController {

    private final IAgricultorService agricultorService;

    public AgricultorController(IAgricultorService agricultorService) {
        this.agricultorService = agricultorService;
    }

    @GetMapping
    @Operation(summary = "Listar agricultores")
    public List<AgricultorResponseDto> listar() {
        return agricultorService.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener agricultor por ID")
    public AgricultorResponseDto obtener(@PathVariable UUID id) {
        return agricultorService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear perfil de agricultor")
    public AgricultorResponseDto crear(@Valid @RequestBody AgricultorCreateDto dto) {
        return agricultorService.crear(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar agricultor")
    public AgricultorResponseDto actualizar(@PathVariable UUID id,
                                            @Valid @RequestBody AgricultorUpdateDto dto) {
        return agricultorService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar agricultor")
    public void eliminar(@PathVariable UUID id) {
        agricultorService.eliminar(id);
    }
}