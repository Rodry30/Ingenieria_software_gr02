package com.foodgest.pedidos.controllers;

import com.foodgest.pedidos.dtos.TransaccionCreateDto;
import com.foodgest.pedidos.dtos.TransaccionResponseDto;
import com.foodgest.pedidos.servicesinterfaces.ITransaccionService;
import com.foodgest.shared.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pedidos")
public class TransaccionController {

    private final ITransaccionService transaccionService;

    public TransaccionController(ITransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @GetMapping("/{pedidoId}/transacciones")
    public ResponseEntity<List<TransaccionResponseDto>> listByPedido(@PathVariable UUID pedidoId) {
        return ResponseEntity.ok(transaccionService.listByPedido(pedidoId));
    }

    @PostMapping("/{pedidoId}/pagar")
    @PreAuthorize("hasRole('COMPRADOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<TransaccionResponseDto>> registrarPago(
            @PathVariable UUID pedidoId,
            @Valid @RequestBody TransaccionCreateDto dto) {
        TransaccionResponseDto response = transaccionService.registrarPagoAprobado(pedidoId, dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "Pago aprobado y retenido en escrow.", response));
    }

    @PostMapping("/transacciones/webhook-pagos")
    public ResponseEntity<ApiResponse<String>> webhookPagos(
            @RequestBody String payload,
            @RequestHeader(value = "x-culqi-signature", required = false) String signature) {
        transaccionService.procesarWebhookCulqi(payload, signature);
        return ResponseEntity.ok(ApiResponse.success(200,
                "Webhook recibido y procesado exitosamente.",
                "OK"));
    }
}

