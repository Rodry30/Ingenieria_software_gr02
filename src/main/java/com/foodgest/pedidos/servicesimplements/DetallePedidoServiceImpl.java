package com.foodgest.pedidos.servicesimplements;

import com.foodgest.catalogo.repositories.ProductoRepository;
import com.foodgest.pedidos.dtos.DetallePedidoDto;
import com.foodgest.pedidos.entities.DetallePedido;
import com.foodgest.pedidos.repositories.DetallePedidoRepository;
import com.foodgest.pedidos.repositories.PedidoRepository;
import com.foodgest.pedidos.servicesinterfaces.IDetallePedidoService;
import com.foodgest.perfiles.agricultores.repositories.AgricultorRepository;
import com.foodgest.shared.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DetallePedidoServiceImpl implements IDetallePedidoService {

    private final DetallePedidoRepository detallePedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final AgricultorRepository agricultorRepository;

    public DetallePedidoServiceImpl(DetallePedidoRepository detallePedidoRepository,
                                    PedidoRepository pedidoRepository,
                                    ProductoRepository productoRepository,
                                    AgricultorRepository agricultorRepository) {
        this.detallePedidoRepository = detallePedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
        this.agricultorRepository = agricultorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetallePedidoDto> listByPedido(UUID pedidoId) {
        return detallePedidoRepository.findByPedidoId(pedidoId).stream()
                .map(DetallePedidoDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetallePedidoDto> listByAgricultor(UUID agricultorId) {
        return detallePedidoRepository.findByAgricultorId(agricultorId).stream()
                .map(DetallePedidoDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DetallePedidoDto crear(DetallePedidoDto dto) {
        DetallePedido detalle = new DetallePedido();
        detalle.setPedido(pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new BusinessException("Pedido no encontrado", HttpStatus.NOT_FOUND)));
        detalle.setProducto(productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new BusinessException("Producto no encontrado", HttpStatus.NOT_FOUND)));
        detalle.setAgricultor(agricultorRepository.findById(dto.getAgricultorId())
                .orElseThrow(() -> new BusinessException("Agricultor no encontrado", HttpStatus.NOT_FOUND)));
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(dto.getPrecioUnitario());
        detalle.setSubtotal(dto.getCantidad().multiply(dto.getPrecioUnitario()).setScale(2, RoundingMode.HALF_UP));
        detalle.setEstadoAgricultor(dto.getEstadoAgricultor() != null ? dto.getEstadoAgricultor() : "pendiente");
        detalle.setNotas(dto.getNotas());
        return DetallePedidoDto.from(detallePedidoRepository.save(detalle));
    }

    @Override
    @Transactional
    public DetallePedidoDto actualizarEstado(UUID id, String estadoAgricultor) {
        DetallePedido detalle = getDetalle(id);
        detalle.setEstadoAgricultor(estadoAgricultor);
        return DetallePedidoDto.from(detallePedidoRepository.save(detalle));
    }

    @Override
    @Transactional
    public void eliminar(UUID id) {
        if (!detallePedidoRepository.existsById(id)) {
            throw new BusinessException("Detalle de pedido no encontrado", HttpStatus.NOT_FOUND);
        }
        detallePedidoRepository.deleteById(id);
    }

    private DetallePedido getDetalle(UUID id) {
        return detallePedidoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Detalle de pedido no encontrado", HttpStatus.NOT_FOUND));
    }
}

