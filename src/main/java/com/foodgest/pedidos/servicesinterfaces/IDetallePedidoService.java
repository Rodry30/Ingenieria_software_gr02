package com.foodgest.pedidos.servicesinterfaces;

import com.foodgest.pedidos.dtos.DetallePedidoDto;

import java.util.List;
import java.util.UUID;

public interface IDetallePedidoService {
    List<DetallePedidoDto> listByPedido(UUID pedidoId);
    List<DetallePedidoDto> listByAgricultor(UUID agricultorId);
    DetallePedidoDto crear(DetallePedidoDto dto);
    DetallePedidoDto actualizarEstado(UUID id, String estadoAgricultor);
    void eliminar(UUID id);
}

