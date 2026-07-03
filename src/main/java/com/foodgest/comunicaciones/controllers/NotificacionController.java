package com.foodgest.comunicaciones.controllers;

import com.foodgest.comunicaciones.dtos.NotificacionCreateDto;
import com.foodgest.comunicaciones.dtos.NotificacionResponseDto;
import com.foodgest.comunicaciones.servicesinterfaces.INotificacionService;
import com.foodgest.shared.exceptions.BusinessException;
import com.foodgest.shared.response.ApiResponse;
import com.foodgest.users.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comunicaciones/notificaciones")
public class NotificacionController {

    private final INotificacionService notificacionService;
    private final UserRepository userRepository;

    public NotificacionController(INotificacionService notificacionService,
                                  UserRepository userRepository) {
        this.notificacionService = notificacionService;
        this.userRepository = userRepository;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<NotificacionResponseDto>> crear(@Valid @RequestBody NotificacionCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "Notificacion creada.", notificacionService.crear(dto)));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<NotificacionResponseDto>> listarActual(@RequestParam(required = false) Boolean leido,
                                                                      Authentication authentication) {
        String email = authentication.getName();
        UUID usuarioId = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado", HttpStatus.NOT_FOUND))
                .getId();
        return ResponseEntity.ok(notificacionService.listarPorUsuario(usuarioId, leido));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<NotificacionResponseDto>> listarPorUsuario(
            @PathVariable UUID usuarioId,
            @RequestParam(required = false) Boolean leido) {
        return ResponseEntity.ok(notificacionService.listarPorUsuario(usuarioId, leido));
    }

    @PutMapping("/{id}/leida")
    public ResponseEntity<NotificacionResponseDto> marcarLeida(@PathVariable UUID id) {
        return ResponseEntity.ok(notificacionService.marcarLeida(id));
    }
}

