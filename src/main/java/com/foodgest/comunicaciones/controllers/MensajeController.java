package com.foodgest.comunicaciones.controllers;

import com.foodgest.comunicaciones.dtos.MensajeCreateDto;
import com.foodgest.comunicaciones.dtos.MensajeResponseDto;
import com.foodgest.comunicaciones.servicesinterfaces.IMensajeService;
import com.foodgest.shared.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comunicaciones")
public class MensajeController {

    private final IMensajeService mensajeService;

    public MensajeController(IMensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    @PostMapping("/mensajes")
    public ResponseEntity<ApiResponse<MensajeResponseDto>> enviar(@Valid @RequestBody MensajeCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "Mensaje enviado.", mensajeService.enviar(dto)));
    }

    @GetMapping("/mensajes/pedido/{pedidoId}")
    public ResponseEntity<List<MensajeResponseDto>> listarPorPedido(@PathVariable UUID pedidoId) {
        return ResponseEntity.ok(mensajeService.listarPorPedido(pedidoId));
    }

    @GetMapping("/mensajes/usuario/{usuarioId}")
    public ResponseEntity<List<MensajeResponseDto>> listarPorUsuario(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(mensajeService.listarPorUsuario(usuarioId));
    }

    @PutMapping("/mensajes/{id}/leido")
    public ResponseEntity<MensajeResponseDto> marcarLeido(@PathVariable UUID id) {
        return ResponseEntity.ok(mensajeService.marcarLeido(id));
    }

    @GetMapping("/chats/{pedidoId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MensajeResponseDto>> chatPorPedido(@PathVariable UUID pedidoId) {
        return ResponseEntity.ok(mensajeService.listarPorPedido(pedidoId));
    }
}

