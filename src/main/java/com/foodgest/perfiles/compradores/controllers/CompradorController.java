package com.foodgest.perfiles.compradores.controllers;

import com.foodgest.perfiles.compradores.dtos.CompradorCreateDto;
import com.foodgest.perfiles.compradores.dtos.CompradorResponseDto;
import com.foodgest.perfiles.compradores.dtos.CompradorUpdateDto;
import com.foodgest.perfiles.compradores.servicesinterfaces.ICompradorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/perfiles/compradores")
@Tag(name = "Compradores", description = "Gestión de perfiles de compradores")
public class CompradorController {

    private final ICompradorService compradorService;

    public CompradorController(ICompradorService compradorService) {
        this.compradorService = compradorService;
    }

    @GetMapping
    @Operation(summary = "Listar compradores")
    public List<CompradorResponseDto> listar() {
        return compradorService.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener comprador por ID")
    public CompradorResponseDto obtener(@PathVariable UUID id) {
        return compradorService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear perfil de comprador")
    public CompradorResponseDto crear(@Valid @RequestBody CompradorCreateDto dto) {
        return compradorService.crear(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar comprador")
    public CompradorResponseDto actualizar(@PathVariable UUID id,
                                           @Valid @RequestBody CompradorUpdateDto dto) {
        return compradorService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar comprador")
    public void eliminar(@PathVariable UUID id) {
        compradorService.eliminar(id);
    }
}
