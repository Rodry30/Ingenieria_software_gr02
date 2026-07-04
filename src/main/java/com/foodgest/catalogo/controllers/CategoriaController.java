package com.foodgest.catalogo.controllers;

import com.foodgest.catalogo.dtos.CategoriaCreateDto;
import com.foodgest.catalogo.dtos.CategoriaResponseDto;
import com.foodgest.catalogo.servicesinterfaces.ICategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/catalogo/categorias")
public class CategoriaController {

    @Autowired
    private ICategoriaService categoriaService;

    
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDto>> list() {
        List<CategoriaResponseDto> result = categoriaService.list().stream()
                .map(CategoriaResponseDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> listId(@PathVariable UUID id) {
        return categoriaService.listId(id)
                .map(c -> ResponseEntity.ok(CategoriaResponseDto.from(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDto> insert(@Valid @RequestBody CategoriaCreateDto dto) {
        var categoria = dto.toEntity();
        categoriaService.insert(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(CategoriaResponseDto.from(categoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> update(@PathVariable UUID id, @Valid @RequestBody CategoriaCreateDto dto) {
        return categoriaService.listId(id).map(existing -> {
            dto.applyTo(existing);
            categoriaService.update(existing);
            return ResponseEntity.ok(CategoriaResponseDto.from(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (categoriaService.listId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
