package com.foodgest.perfiles.transportistas.controllers;

import com.foodgest.perfiles.transportistas.dtos.TransportistaCreateDto;
import com.foodgest.perfiles.transportistas.dtos.TransportistaResponseDto;
import com.foodgest.perfiles.transportistas.dtos.TransportistaUpdateDto;
import com.foodgest.perfiles.transportistas.servicesinterfaces.ITransportistaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transportistas")
@Tag(name = "Transportistas", description = "Gestión de perfiles de transportistas")
public class TransportistaController {

    private final ITransportistaService transportistaService;

    public TransportistaController(ITransportistaService transportistaService) {
        this.transportistaService = transportistaService;
    }

    @GetMapping
    @Operation(summary = "Listar transportistas")
    public List<TransportistaResponseDto> listar() {
        return transportistaService.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener transportista por ID")
    public TransportistaResponseDto obtener(@PathVariable UUID id) {
        return transportistaService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear perfil de transportista")
    public TransportistaResponseDto crear(@Valid @RequestBody TransportistaCreateDto dto) {
        return transportistaService.crear(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar transportista")
    public TransportistaResponseDto actualizar(@PathVariable UUID id,
                                               @Valid @RequestBody TransportistaUpdateDto dto) {
        return transportistaService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar transportista")
    public void eliminar(@PathVariable UUID id) {
        transportistaService.eliminar(id);
    }
}
