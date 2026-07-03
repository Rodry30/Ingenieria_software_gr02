package com.foodgest.pedidos.controllers;

import com.foodgest.pedidos.dtos.DetallePedidoDto;
import com.foodgest.pedidos.servicesinterfaces.IDetallePedidoService;
import com.foodgest.shared.response.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/pedidos/detalles")
@Validated
public class DetallePedidoController {

    private final IDetallePedidoService detallePedidoService;

    public DetallePedidoController(IDetallePedidoService detallePedidoService) {
        this.detallePedidoService = detallePedidoService;
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<DetallePedidoDto>> listByPedido(@PathVariable UUID pedidoId) {
        return ResponseEntity.ok(detallePedidoService.listByPedido(pedidoId));
    }

    @GetMapping("/agricultor/{agricultorId}")
    public ResponseEntity<List<DetallePedidoDto>> listByAgricultor(@PathVariable UUID agricultorId) {
        return ResponseEntity.ok(detallePedidoService.listByAgricultor(agricultorId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<DetallePedidoDto>> crear(@Valid @RequestBody DetallePedidoDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "Detalle de pedido creado.", detallePedidoService.crear(dto)));
    }

    @PatchMapping("/{id}/estado-agricultor")
    @PreAuthorize("hasRole('AGRICULTOR') or hasRole('ADMIN')")
    public ResponseEntity<DetallePedidoDto> actualizarEstado(@PathVariable UUID id,
                                                             @RequestBody Map<String, @NotBlank String> body) {
        return ResponseEntity.ok(detallePedidoService.actualizarEstado(id, body.get("estadoAgricultor")));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        detallePedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

