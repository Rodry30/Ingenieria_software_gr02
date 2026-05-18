package com.foodgest.perfiles.agricultores.controllers;

import com.foodgest.perfiles.agricultores.dtos.AgricultorCreateDto;
import com.foodgest.perfiles.agricultores.dtos.AgricultorResponseDto;
import com.foodgest.perfiles.agricultores.dtos.AgricultorUpdateDto;
import com.foodgest.perfiles.agricultores.entities.Agricultor;
import com.foodgest.perfiles.agricultores.servicesinterfaces.IAgricultorService;
import com.foodgest.users.entities.UserEntities;
import com.foodgest.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/perfiles/agricultores")
public class AgricultorController {

    @Autowired
    private IAgricultorService agricultorService;

    @Autowired
    private UserRepository userRepository;

    /** GET /api/perfiles/agricultores */
    @GetMapping
    public ResponseEntity<List<AgricultorResponseDto>> list() {
        List<AgricultorResponseDto> result = agricultorService.list().stream()
                .map(AgricultorResponseDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    /** GET /api/perfiles/agricultores/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<AgricultorResponseDto> listId(@PathVariable UUID id) {
        return agricultorService.listId(id)
                .map(a -> ResponseEntity.ok(AgricultorResponseDto.from(a)))
                .orElse(ResponseEntity.notFound().build());
    }

    /** GET /api/perfiles/agricultores/usuario/{usuarioId} */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<AgricultorResponseDto> findByUsuario(@PathVariable UUID usuarioId) {
        return agricultorService.findByUsuarioId(usuarioId)
                .map(a -> ResponseEntity.ok(AgricultorResponseDto.from(a)))
                .orElse(ResponseEntity.notFound().build());
    }

    /** POST /api/perfiles/agricultores */
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody AgricultorCreateDto dto) {
        if (dto.getUsuarioId() == null) {
            return ResponseEntity.badRequest().body("El campo usuarioId es obligatorio.");
        }
        UserEntities usuario = userRepository.findById(dto.getUsuarioId()).orElse(null);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }
        Agricultor agricultor = dto.toEntity(usuario);
        agricultorService.insert(agricultor);
        return ResponseEntity.status(HttpStatus.CREATED).body(AgricultorResponseDto.from(agricultor));
    }

    /** PUT /api/perfiles/agricultores/{id} */
    @PutMapping("/{id}")
    public ResponseEntity<AgricultorResponseDto> update(@PathVariable UUID id,
                                                        @RequestBody AgricultorUpdateDto dto) {
        return agricultorService.listId(id).map(existing -> {
            if (dto.getNombreFinca() != null)          existing.setNombreFinca(dto.getNombreFinca());
            if (dto.getHectareas() != null)            existing.setHectareas(dto.getHectareas());
            if (dto.getDescripcion() != null)          existing.setDescripcion(dto.getDescripcion());
            if (dto.getTipoCultivoPrincipal() != null) existing.setTipoCultivoPrincipal(dto.getTipoCultivoPrincipal());
            if (dto.getDireccionParcela() != null)     existing.setDireccionParcela(dto.getDireccionParcela());
            if (dto.getLatitud() != null)              existing.setLatitud(dto.getLatitud());
            if (dto.getLongitud() != null)             existing.setLongitud(dto.getLongitud());
            if (dto.getRuc() != null)                  existing.setRuc(dto.getRuc());
            if (dto.getCuentaBancaria() != null)       existing.setCuentaBancaria(dto.getCuentaBancaria());
            if (dto.getBanco() != null)                existing.setBanco(dto.getBanco());
            agricultorService.update(existing);
            return ResponseEntity.ok(AgricultorResponseDto.from(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    /** DELETE /api/perfiles/agricultores/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (agricultorService.listId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        agricultorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
