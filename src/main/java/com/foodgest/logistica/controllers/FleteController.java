package com.foodgest.logistica.controllers;

import com.foodgest.logistica.servicesinterfaces.IFleteService;
import com.foodgest.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/logistica/flete")
@Tag(name = "Flete", description = "Operaciones de cálculo de flete logístico")
public class FleteController {

    private final IFleteService fleteService;

    public FleteController(IFleteService fleteService) {
        this.fleteService = fleteService;
    }

    @GetMapping
    @Operation(summary = "Calcular flete estimado", description = "Calcula el flete terrestre a partir de coordenadas, peso y región")
    public ResponseEntity<ApiResponse<BigDecimal>> calcularFlete(
            @RequestParam BigDecimal origenLat,
            @RequestParam BigDecimal origenLng,
            @RequestParam BigDecimal destinoLat,
            @RequestParam BigDecimal destinoLng,
            @RequestParam(required = false) BigDecimal pesoToneladas,
            @RequestParam(required = false, defaultValue = "COSTA") String region) {

        BigDecimal costo = fleteService.calcularFleteEstimado(origenLat, origenLng, destinoLat, destinoLng, pesoToneladas, region);
        return ResponseEntity.ok(ApiResponse.success(200, "Cálculo de flete exitoso.", costo));
    }

    @GetMapping("/pedido/{pedidoId}")
    @Operation(summary = "Calcular flete para un pedido", description = "Calcula el flete terrestre para un pedido específico basado en su origen, destino y cantidad acordada")
    public ResponseEntity<ApiResponse<BigDecimal>> calcularFletePedido(@PathVariable UUID pedidoId) {
        BigDecimal costo = fleteService.calcularFleteParaPedido(pedidoId);
        return ResponseEntity.ok(ApiResponse.success(200, "Cálculo de flete de pedido exitoso.", costo));
    }
}
