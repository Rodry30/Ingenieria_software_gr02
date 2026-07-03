package com.foodgest.pedidos.servicesinterfaces;

import com.foodgest.pedidos.dtos.PedidoCreateDto;
import com.foodgest.pedidos.dtos.PedidoEstadoUpdateDto;
import com.foodgest.pedidos.dtos.PedidoResponseDto;

import java.util.List;
import java.util.UUID;

public interface IPedidoService {
    List<PedidoResponseDto> list();
    PedidoResponseDto listId(UUID id);
    List<PedidoResponseDto> listByComprador(UUID compradorId);
    PedidoResponseDto crear(PedidoCreateDto dto);
    PedidoResponseDto actualizarEstado(UUID id, PedidoEstadoUpdateDto dto);
    PedidoResponseDto confirmarEntrega(UUID id);
}

