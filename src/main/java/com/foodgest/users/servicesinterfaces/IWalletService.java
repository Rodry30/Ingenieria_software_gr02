package com.foodgest.users.servicesinterfaces;

import com.foodgest.pedidos.entities.Pedido;
import com.foodgest.users.dtos.MovimientoWalletResponseDto;
import com.foodgest.users.dtos.WalletResponseDto;

import java.util.List;
import java.util.UUID;

public interface IWalletService {
    WalletResponseDto obtenerPorUsuario(UUID usuarioId);
    List<MovimientoWalletResponseDto> listarMovimientos(UUID usuarioId);
    void retenerPagoVenta(Pedido pedido);
    void liberarPagoVenta(Pedido pedido);
}

