package com.foodgest.catalogo.controllers;

import com.foodgest.catalogo.dtos.FotoProductoDto;
import com.foodgest.catalogo.servicesinterfaces.IFotoProductoService;
import com.foodgest.catalogo.servicesinterfaces.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/catalogo/fotos-producto")
public class FotoProductoController {

    @Autowired
    private IFotoProductoService fotoProductoService;

    @Autowired
    private IProductoService productoService;

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<FotoProductoDto>> listByProducto(@PathVariable UUID productoId) {
        List<FotoProductoDto> result = fotoProductoService.listByProducto(productoId).stream()
                .map(FotoProductoDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FotoProductoDto> listId(@PathVariable UUID id) {
        return fotoProductoService.listId(id)
                .map(f -> ResponseEntity.ok(FotoProductoDto.from(f)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FotoProductoDto> insert(@RequestBody FotoProductoDto dto) {
        return productoService.listId(dto.getProductoId()).map(producto -> {
            var foto = dto.toEntity(producto);
            var saved = fotoProductoService.insert(foto);
            return ResponseEntity.status(HttpStatus.CREATED).body(FotoProductoDto.from(saved));
        }).orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (fotoProductoService.listId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        fotoProductoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
