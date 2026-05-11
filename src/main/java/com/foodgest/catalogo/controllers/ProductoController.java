package com.foodgest.catalogo.controllers;

import com.foodgest.catalogo.dtos.ProductoConFotosResponseDto;
import com.foodgest.catalogo.dtos.ProductoCreateDto;
import com.foodgest.catalogo.dtos.ProductoResponseDto;
import com.foodgest.catalogo.servicesinterfaces.ICategoriaService;
import com.foodgest.catalogo.servicesinterfaces.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/catalogo/productos")
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    @Autowired
    private ICategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<ProductoResponseDto>> list() {
        List<ProductoResponseDto> result = productoService.list().stream()
                .map(ProductoResponseDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProductoResponseDto>> listByCategoria(@PathVariable UUID categoriaId) {
        List<ProductoResponseDto> result = productoService.listByCategoria(categoriaId).stream()
                .map(ProductoResponseDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoConFotosResponseDto> listId(@PathVariable UUID id) {
        return productoService.listId(id)
                .map(p -> ResponseEntity.ok(ProductoConFotosResponseDto.from(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDto> insert(@RequestBody ProductoCreateDto dto) {
        return categoriaService.listId(dto.getCategoriaId()).map(categoria -> {
            var producto = dto.toEntity(categoria);
            var saved = productoService.insert(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(ProductoResponseDto.from(saved));
        }).orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDto> update(@PathVariable UUID id, @RequestBody ProductoCreateDto dto) {
        return productoService.listId(id).map(existing ->
            categoriaService.listId(dto.getCategoriaId()).map(categoria -> {
                dto.applyTo(existing, categoria);
                var updated = productoService.update(existing);
                return ResponseEntity.ok(ProductoResponseDto.from(updated));
            }).orElse(ResponseEntity.badRequest().build())
        ).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (productoService.listId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
