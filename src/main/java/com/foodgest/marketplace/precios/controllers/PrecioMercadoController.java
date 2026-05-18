package com.foodgest.marketplace.precios.controllers;

import com.foodgest.catalogo.entities.Producto;
import com.foodgest.catalogo.repositories.ProductoRepository;
import com.foodgest.marketplace.precios.dtos.PrecioMercadoCreateDto;
import com.foodgest.marketplace.precios.dtos.PrecioMercadoResponseDto;
import com.foodgest.marketplace.precios.entities.PrecioMercado;
import com.foodgest.marketplace.precios.servicesinterfaces.IPrecioMercadoService;
import com.foodgest.shared.exceptions.BusinessException;
import com.foodgest.shared.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/marketplace/precios")
public class PrecioMercadoController {

    @Autowired private IPrecioMercadoService precioMercadoService;
    @Autowired private ProductoRepository productoRepository;

    /** GET /api/marketplace/precios — Lista todos */
    @GetMapping
    public ResponseEntity<List<PrecioMercadoResponseDto>> list() {
        List<PrecioMercadoResponseDto> result = precioMercadoService.list().stream()
                .map(PrecioMercadoResponseDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    /** GET /api/marketplace/precios/{id} — Detalle */
    @GetMapping("/{id}")
    public ResponseEntity<PrecioMercadoResponseDto> listId(@PathVariable UUID id) {
        return precioMercadoService.listId(id)
                .map(pm -> ResponseEntity.ok(PrecioMercadoResponseDto.from(pm)))
                .orElse(ResponseEntity.notFound().build());
    }

    /** GET /api/marketplace/precios/producto/{productoId} — Historial por producto (desc por fecha) */
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<PrecioMercadoResponseDto>> listByProducto(@PathVariable UUID productoId) {
        List<PrecioMercadoResponseDto> result = precioMercadoService.listByProductoOrdenado(productoId).stream()
                .map(PrecioMercadoResponseDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    /** POST /api/marketplace/precios — Registra nuevo precio de mercado */
    @PostMapping
    public ResponseEntity<ApiResponse<PrecioMercadoResponseDto>> insert(@Valid @RequestBody PrecioMercadoCreateDto dto) {
        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new BusinessException("Producto no encontrado", HttpStatus.NOT_FOUND));

        PrecioMercado precio = dto.toEntity(producto);
        precioMercadoService.insert(precio);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "Precio de mercado registrado.", PrecioMercadoResponseDto.from(precio)));
    }

    /** PUT /api/marketplace/precios/{id} — Actualiza precio */
    @PutMapping("/{id}")
    public ResponseEntity<PrecioMercadoResponseDto> update(@PathVariable UUID id,
                                                            @Valid @RequestBody PrecioMercadoCreateDto dto) {
        return precioMercadoService.listId(id).map(existing -> {
            if (dto.getPrecioMin() != null)           existing.setPrecioMin(dto.getPrecioMin());
            if (dto.getPrecioMax() != null)           existing.setPrecioMax(dto.getPrecioMax());
            if (dto.getPrecioPromedio() != null)      existing.setPrecioPromedio(dto.getPrecioPromedio());
            if (dto.getVariacionPorcentaje() != null) existing.setVariacionPorcentaje(dto.getVariacionPorcentaje());
            if (dto.getTendencia() != null)           existing.setTendencia(dto.getTendencia().name());
            precioMercadoService.update(existing);
            return ResponseEntity.ok(PrecioMercadoResponseDto.from(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    /** DELETE /api/marketplace/precios/{id} — Elimina precio */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (precioMercadoService.listId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        precioMercadoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
