package com.foodgest.comunicaciones.servicesimplements;

import com.foodgest.comunicaciones.dtos.NotificacionCreateDto;
import com.foodgest.comunicaciones.dtos.NotificacionResponseDto;
import com.foodgest.comunicaciones.entities.Notificacion;
import com.foodgest.comunicaciones.repositories.NotificacionRepository;
import com.foodgest.comunicaciones.servicesinterfaces.INotificacionService;
import com.foodgest.shared.exceptions.BusinessException;
import com.foodgest.users.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NotificacionServiceImpl implements INotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final UserRepository userRepository;

    public NotificacionServiceImpl(NotificacionRepository notificacionRepository,
                                   UserRepository userRepository) {
        this.notificacionRepository = notificacionRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public NotificacionResponseDto crear(NotificacionCreateDto dto) {
        Notificacion notificacion = new Notificacion();
        notificacion.setUsuario(userRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new BusinessException("Usuario no encontrado", HttpStatus.NOT_FOUND)));
        notificacion.setTipo(dto.getTipo());
        notificacion.setTitulo(dto.getTitulo());
        notificacion.setMensaje(dto.getMensaje());
        notificacion.setData(dto.getData() != null ? dto.getData() : "{}");
        notificacion.setLeido(false);
        return NotificacionResponseDto.from(notificacionRepository.save(notificacion));
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificacionResponseDto> listarPorUsuario(UUID usuarioId, Boolean leido) {
        var result = leido == null
                ? notificacionRepository.findByUsuarioIdOrderByCreatedAtDesc(usuarioId)
                : notificacionRepository.findByUsuarioIdAndLeidoOrderByCreatedAtDesc(usuarioId, leido);
        return result.stream().map(NotificacionResponseDto::from).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public NotificacionResponseDto marcarLeida(UUID id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Notificacion no encontrada", HttpStatus.NOT_FOUND));
        notificacion.setLeido(true);
        return NotificacionResponseDto.from(notificacionRepository.save(notificacion));
    }
}

