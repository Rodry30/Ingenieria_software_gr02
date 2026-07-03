package com.foodgest.reputacion.servicesimplements;

import com.foodgest.pedidos.entities.Pedido;
import com.foodgest.pedidos.repositories.PedidoRepository;
import com.foodgest.reputacion.dtos.CalificacionCreateDto;
import com.foodgest.reputacion.dtos.CalificacionResponseDto;
import com.foodgest.reputacion.dtos.ReputacionUsuarioDto;
import com.foodgest.reputacion.entities.Calificacion;
import com.foodgest.reputacion.repositories.CalificacionRepository;
import com.foodgest.reputacion.servicesinterfaces.ICalificacionService;
import com.foodgest.shared.exceptions.BusinessException;
import com.foodgest.users.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CalificacionServiceImpl implements ICalificacionService {

    private final CalificacionRepository calificacionRepository;
    private final PedidoRepository pedidoRepository;
    private final UserRepository userRepository;

    public CalificacionServiceImpl(CalificacionRepository calificacionRepository,
                                   PedidoRepository pedidoRepository,
                                   UserRepository userRepository) {
        this.calificacionRepository = calificacionRepository;
        this.pedidoRepository = pedidoRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CalificacionResponseDto calificar(CalificacionCreateDto dto) {
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new BusinessException("Pedido no encontrado", HttpStatus.NOT_FOUND));
        if (!"entregado_finalizado".equals(pedido.getEstado())) {
            throw new BusinessException("Solo se puede calificar un pedido entregado", HttpStatus.BAD_REQUEST);
        }
        if (calificacionRepository.existsByPedidoIdAndCalificadorId(dto.getPedidoId(), dto.getCalificadorId())) {
            throw new BusinessException("Este usuario ya califico el pedido", HttpStatus.CONFLICT);
        }

        Calificacion calificacion = new Calificacion();
        calificacion.setPedido(pedido);
        calificacion.setCalificador(userRepository.findById(dto.getCalificadorId())
                .orElseThrow(() -> new BusinessException("Calificador no encontrado", HttpStatus.NOT_FOUND)));
        calificacion.setCalificado(userRepository.findById(dto.getCalificadoId())
                .orElseThrow(() -> new BusinessException("Calificado no encontrado", HttpStatus.NOT_FOUND)));
        calificacion.setPuntuacion(dto.getPuntuacion());
        calificacion.setComentario(dto.getComentario());
        calificacion.setTipo(dto.getTipo());
        calificacion.setVerificado(true);
        return CalificacionResponseDto.from(calificacionRepository.save(calificacion));
    }

    @Override
    @Transactional(readOnly = true)
    public ReputacionUsuarioDto listarPorUsuario(UUID usuarioId) {
        List<CalificacionResponseDto> calificaciones = calificacionRepository.findByCalificadoIdOrderByCreatedAtDesc(usuarioId).stream()
                .map(CalificacionResponseDto::from)
                .collect(Collectors.toList());

        int totalResenas = calificaciones.size();
        double promedio = totalResenas == 0 ? 0.0 : calificaciones.stream()
                .mapToInt(CalificacionResponseDto::getPuntuacion)
                .average()
                .orElse(0.0);

        BigDecimal promedioEstrellas = BigDecimal.valueOf(promedio)
                .setScale(2, RoundingMode.HALF_UP);

        return ReputacionUsuarioDto.of(promedioEstrellas.doubleValue(), totalResenas, calificaciones);
    }
}

