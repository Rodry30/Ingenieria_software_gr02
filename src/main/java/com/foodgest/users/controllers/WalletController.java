package com.foodgest.users.controllers;

import com.foodgest.users.dtos.MovimientoWalletResponseDto;
import com.foodgest.users.dtos.WalletResponseDto;
import com.foodgest.users.servicesinterfaces.IWalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users/{usuarioId}/wallet")
public class WalletController {

    private final IWalletService walletService;

    public WalletController(IWalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or authentication.name == @userRepository.findById(#usuarioId).orElseThrow().email")
    public ResponseEntity<WalletResponseDto> obtenerWallet(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(walletService.obtenerPorUsuario(usuarioId));
    }

    @GetMapping("/movimientos")
    @PreAuthorize("hasRole('ADMIN') or authentication.name == @userRepository.findById(#usuarioId).orElseThrow().email")
    public ResponseEntity<List<MovimientoWalletResponseDto>> listarMovimientos(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(walletService.listarMovimientos(usuarioId));
    }
}

