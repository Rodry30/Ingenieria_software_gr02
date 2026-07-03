package com.foodgest.marketplace.ofertas.controllers;

import com.foodgest.catalogo.entities.Producto;
import com.foodgest.catalogo.repositories.ProductoRepository;
import com.foodgest.marketplace.ofertas.dtos.OfertaCreateDto;
import com.foodgest.marketplace.ofertas.dtos.OfertaMapaResponseDto;
import com.foodgest.marketplace.ofertas.dtos.OfertaResponseDto;
import com.foodgest.marketplace.ofertas.dtos.OfertaUpdateDto;
import com.foodgest.marketplace.ofertas.entities.Oferta;
import com.foodgest.marketplace.ofertas.servicesinterfaces.IOfertaService;
import com.foodgest.perfiles.agricultores.entities.Agricultor;
import com.foodgest.perfiles.agricultores.repositories.AgricultorRepository;
import com.foodgest.shared.exceptions.BusinessException;
import com.foodgest.shared.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/marketplace/ofertas")
public class OfertaController {

    @Autowired private IOfertaService ofertaService;
    @Autowired private AgricultorRepository agricultorRepository;
    @Autowired private ProductoRepository productoRepository;

    /** GET /api/marketplace/ofertas - Lista todas las ofertas */
    @GetMapping
    public ResponseEntity<?> list(
            @RequestParam(required = false) BigDecimal latitud,
            @RequestParam(required = false) BigDecimal longitud,
            @RequestParam(required = false) BigDecimal radioKm) {
        if (latitud != null && longitud != null && radioKm != null) {
            return ResponseEntity.ok(ofertaService.listMapa(latitud, longitud, radioKm));
        }
        List<OfertaResponseDto> result = ofertaService.list().stream()
                .map(OfertaResponseDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    /** GET /api/marketplace/ofertas/estado/{estado} - Filtra por estado */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<OfertaResponseDto>> listByEstado(@PathVariable String estado) {
        List<OfertaResponseDto> result = ofertaService.listByEstado(estado).stream()
                .map(OfertaResponseDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    /** GET /api/marketplace/ofertas/agricultor/{agricultorId} - Ofertas de un agricultor */
    @GetMapping("/agricultor/{agricultorId}")
    public ResponseEntity<List<OfertaResponseDto>> listByAgricultor(@PathVariable UUID agricultorId) {
        List<OfertaResponseDto> result = ofertaService.listByAgricultor(agricultorId).stream()
                .map(OfertaResponseDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    /** GET /api/marketplace/ofertas/mapa - Ofertas activas cercanas usando PostGIS */
    @GetMapping("/mapa")
    public ResponseEntity<List<OfertaMapaResponseDto>> listMapa(
            @RequestParam BigDecimal latitud,
            @RequestParam BigDecimal longitud,
            @RequestParam(defaultValue = "50") BigDecimal radioKm) {
        return ResponseEntity.ok(ofertaService.listMapa(latitud, longitud, radioKm));
    }

    /** GET /api/marketplace/ofertas/{id} - Detalle + incrementa vistas */
    @GetMapping("/{id}")
    public ResponseEntity<OfertaResponseDto> listId(@PathVariable UUID id) {
        return ofertaService.listId(id).map(oferta -> {
            ofertaService.incrementarVistas(id);
            return ResponseEntity.ok(OfertaResponseDto.from(oferta));
        }).orElse(ResponseEntity.notFound().build());
    }

    /** POST /api/marketplace/ofertas - Crea nueva oferta */
    @PostMapping
    public ResponseEntity<ApiResponse<OfertaResponseDto>> insert(@Valid @RequestBody OfertaCreateDto dto) {
        Agricultor agricultor = agricultorRepository.findById(dto.getAgricultorId())
                .orElseThrow(() -> new BusinessException("Agricultor no encontrado", HttpStatus.NOT_FOUND));

        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new BusinessException("Producto no encontrado", HttpStatus.NOT_FOUND));

        Oferta oferta = dto.toEntity(agricultor, producto);
        ofertaService.insert(oferta);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "Oferta creada exitosamente.", OfertaResponseDto.from(oferta)));
    }

    /** PUT /api/marketplace/ofertas/{id} - Actualiza campos de la oferta */
    @PutMapping("/{id}")
    public ResponseEntity<OfertaResponseDto> update(@PathVariable UUID id,
                                                     @Valid @RequestBody OfertaUpdateDto dto) {
        return ofertaService.listId(id).map(existing -> {
            dto.applyTo(existing);
            ofertaService.update(existing);
            return ResponseEntity.ok(OfertaResponseDto.from(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    /** DELETE /api/marketplace/ofertas/{id} - Elimina la oferta */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (ofertaService.listId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ofertaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
