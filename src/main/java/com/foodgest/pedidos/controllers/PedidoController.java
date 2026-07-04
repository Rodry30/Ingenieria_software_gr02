package com.foodgest.pedidos.controllers;

import com.foodgest.pedidos.dtos.PedidoCreateDto;
import com.foodgest.pedidos.dtos.PedidoEstadoUpdateDto;
import com.foodgest.pedidos.dtos.PedidoResponseDto;
import com.foodgest.pedidos.servicesinterfaces.IPedidoService;
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
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final IPedidoService pedidoService;

    public PedidoController(IPedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PedidoResponseDto>> list() {
        return ResponseEntity.ok(pedidoService.list());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')"
            + " or authentication.name == @pedidoRepository.findById(#id).orElseThrow().comprador.usuario.email"
            + " or authentication.name == @pedidoRepository.findById(#id).orElseThrow().oferta.agricultor.usuario.email")
    public ResponseEntity<PedidoResponseDto> listId(@PathVariable UUID id) {
        return ResponseEntity.ok(pedidoService.listId(id));
    }

    @GetMapping("/comprador/{compradorId}")
    @PreAuthorize("hasRole('ADMIN') or authentication.name == @compradorRepository.findById(#compradorId).orElseThrow().usuario.email")
    public ResponseEntity<List<PedidoResponseDto>> listByComprador(@PathVariable UUID compradorId) {
        return ResponseEntity.ok(pedidoService.listByComprador(compradorId));
    }

    @PostMapping
    @PreAuthorize("hasRole('COMPRADOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PedidoResponseDto>> crear(@Valid @RequestBody PedidoCreateDto dto) {
        PedidoResponseDto pedido = pedidoService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "Pedido creado. Pendiente de pago.", pedido));
    }

    @PutMapping("/{id}/estado")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGRICULTOR') or hasRole('TRANSPORTISTA')")
    public ResponseEntity<PedidoResponseDto> actualizarEstado(@PathVariable UUID id,
                                                               @Valid @RequestBody PedidoEstadoUpdateDto dto) {
        return ResponseEntity.ok(pedidoService.actualizarEstado(id, dto));
    }

    @PostMapping("/{id}/confirmar-entrega")
    @PreAuthorize("hasRole('COMPRADOR') or hasRole('ADMIN')")
    public ResponseEntity<PedidoResponseDto> confirmarEntrega(@PathVariable UUID id) {
        return ResponseEntity.ok(pedidoService.confirmarEntrega(id));
    }
}

