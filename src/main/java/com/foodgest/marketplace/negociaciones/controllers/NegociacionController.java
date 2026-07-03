package com.foodgest.marketplace.negociaciones.controllers;

import com.foodgest.marketplace.negociaciones.dtos.NegociacionCreateDto;
import com.foodgest.marketplace.negociaciones.dtos.NegociacionResponseDto;
import com.foodgest.marketplace.negociaciones.dtos.NegociacionUpdateDto;
import com.foodgest.marketplace.negociaciones.entities.Negociacion;
import com.foodgest.marketplace.negociaciones.servicesinterfaces.INegociacionService;
import com.foodgest.marketplace.ofertas.entities.Oferta;
import com.foodgest.marketplace.ofertas.repositories.OfertaRepository;
import com.foodgest.perfiles.compradores.entities.Comprador;
import com.foodgest.perfiles.compradores.repositories.CompradorRepository;
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
@RequestMapping("/api/marketplace/negociaciones")
public class NegociacionController {

    @Autowired private INegociacionService negociacionService;
    @Autowired private OfertaRepository ofertaRepository;
    @Autowired private CompradorRepository compradorRepository;

    /** GET /api/marketplace/negociaciones */
    @GetMapping
    public ResponseEntity<List<NegociacionResponseDto>> list() {
        List<NegociacionResponseDto> result = negociacionService.list().stream()
                .map(NegociacionResponseDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    /** GET /api/marketplace/negociaciones/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<NegociacionResponseDto> listId(@PathVariable UUID id) {
        return negociacionService.listId(id)
                .map(n -> ResponseEntity.ok(NegociacionResponseDto.from(n)))
                .orElse(ResponseEntity.notFound().build());
    }

    /** GET /api/marketplace/negociaciones/oferta/{ofertaId} */
    @GetMapping("/oferta/{ofertaId}")
    public ResponseEntity<List<NegociacionResponseDto>> listByOferta(@PathVariable UUID ofertaId) {
        List<NegociacionResponseDto> result = negociacionService.listByOferta(ofertaId).stream()
                .map(NegociacionResponseDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    /** GET /api/marketplace/negociaciones/comprador/{compradorId} */
    @GetMapping("/comprador/{compradorId}")
    public ResponseEntity<List<NegociacionResponseDto>> listByComprador(@PathVariable UUID compradorId) {
        List<NegociacionResponseDto> result = negociacionService.listByComprador(compradorId).stream()
                .map(NegociacionResponseDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    /**
     * POST /api/marketplace/negociaciones
     * Reglas de negocio:
     * 1. La oferta debe existir
     * 2. La oferta debe estar en estado 'activa'
     * 3. La oferta debe aceptar negociaciones
     * 4. El comprador debe existir
     */
    @PostMapping
    public ResponseEntity<ApiResponse<NegociacionResponseDto>> insert(@Valid @RequestBody NegociacionCreateDto dto) {
        Oferta oferta = ofertaRepository.findById(dto.getOfertaId())
                .orElseThrow(() -> new BusinessException("Oferta no encontrada", HttpStatus.NOT_FOUND));

        if (!"activa".equals(oferta.getEstado())) {
            throw new BusinessException("La oferta no esta disponible para negociar", HttpStatus.BAD_REQUEST);
        }

        if (Boolean.FALSE.equals(oferta.getAceptaNegociacion())) {
            throw new BusinessException("Esta oferta no acepta negociaciones", HttpStatus.BAD_REQUEST);
        }

        Comprador comprador = compradorRepository.findById(dto.getCompradorId())
                .orElseThrow(() -> new BusinessException("Comprador no encontrado", HttpStatus.NOT_FOUND));

        Negociacion negociacion = dto.toEntity(oferta, comprador);
        negociacionService.insert(negociacion);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "Negociacion iniciada exitosamente.", NegociacionResponseDto.from(negociacion)));
    }

    /**
    /** PUT /api/marketplace/negociaciones/{id}/responder
     * Permite al agricultor responder: aceptar, rechazar o contraofertar.
     */
    @PutMapping("/{id}/responder")
    public ResponseEntity<NegociacionResponseDto> update(@PathVariable UUID id,
                                                          @Valid @RequestBody NegociacionUpdateDto dto) {
        return negociacionService.listId(id).map(existing -> {
            dto.applyTo(existing);
            negociacionService.update(existing);
            return ResponseEntity.ok(NegociacionResponseDto.from(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    /** DELETE /api/marketplace/negociaciones/{id} — Cancela negociacion */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (negociacionService.listId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        negociacionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
