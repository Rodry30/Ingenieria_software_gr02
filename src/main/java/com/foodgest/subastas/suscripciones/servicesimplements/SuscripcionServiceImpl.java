package com.foodgest.subastas.suscripciones.servicesimplements;

import com.foodgest.catalogo.repositories.ProductoRepository;
import com.foodgest.perfiles.agricultores.repositories.AgricultorRepository;
import com.foodgest.perfiles.compradores.repositories.CompradorRepository;
import com.foodgest.shared.exceptions.BusinessException;
import com.foodgest.subastas.suscripciones.dtos.SuscripcionCreateDto;
import com.foodgest.subastas.suscripciones.dtos.SuscripcionResponseDto;
import com.foodgest.subastas.suscripciones.dtos.SuscripcionUpdateDto;
import com.foodgest.subastas.suscripciones.entities.Suscripcion;
import com.foodgest.subastas.suscripciones.repositories.SuscripcionRepository;
import com.foodgest.subastas.suscripciones.servicesinterfaces.ISuscripcionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SuscripcionServiceImpl implements ISuscripcionService {

    private final SuscripcionRepository suscripcionRepository;
    private final CompradorRepository compradorRepository;
    private final AgricultorRepository agricultorRepository;
    private final ProductoRepository productoRepository;

    public SuscripcionServiceImpl(SuscripcionRepository suscripcionRepository,
                                  CompradorRepository compradorRepository,
                                  AgricultorRepository agricultorRepository,
                                  ProductoRepository productoRepository) {
        this.suscripcionRepository = suscripcionRepository;
        this.compradorRepository = compradorRepository;
        this.agricultorRepository = agricultorRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SuscripcionResponseDto> list(String estado) {
        var suscripciones = estado == null ? suscripcionRepository.findAll() : suscripcionRepository.findByEstado(estado);
        return suscripciones.stream().map(SuscripcionResponseDto::from).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SuscripcionResponseDto listId(UUID id) {
        return SuscripcionResponseDto.from(getSuscripcion(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SuscripcionResponseDto> listByComprador(UUID compradorId) {
        return suscripcionRepository.findByCompradorId(compradorId).stream()
                .map(SuscripcionResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SuscripcionResponseDto> listByAgricultor(UUID agricultorId) {
        return suscripcionRepository.findByAgricultorId(agricultorId).stream()
                .map(SuscripcionResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SuscripcionResponseDto crear(SuscripcionCreateDto dto) {
        validarFechas(dto.getFechaInicio(), dto.getFechaFin());
        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setComprador(compradorRepository.findById(dto.getCompradorId())
                .orElseThrow(() -> new BusinessException("Comprador no encontrado", HttpStatus.NOT_FOUND)));
        suscripcion.setAgricultor(agricultorRepository.findById(dto.getAgricultorId())
                .orElseThrow(() -> new BusinessException("Agricultor no encontrado", HttpStatus.NOT_FOUND)));
        suscripcion.setProducto(productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new BusinessException("Producto no encontrado", HttpStatus.NOT_FOUND)));
        suscripcion.setCantidadPeriodica(dto.getCantidadPeriodica());
        suscripcion.setFrecuencia(dto.getFrecuencia().name());
        suscripcion.setEstado("activa");
        suscripcion.setFechaInicio(dto.getFechaInicio());
        suscripcion.setFechaFin(dto.getFechaFin());
        return SuscripcionResponseDto.from(suscripcionRepository.save(suscripcion));
    }

    @Override
    @Transactional
    public SuscripcionResponseDto actualizar(UUID id, SuscripcionUpdateDto dto) {
        Suscripcion suscripcion = getSuscripcion(id);
        LocalDate fechaInicio = dto.getFechaInicio() != null ? dto.getFechaInicio() : suscripcion.getFechaInicio();
        LocalDate fechaFin = dto.getFechaFin() != null ? dto.getFechaFin() : suscripcion.getFechaFin();
        validarFechas(fechaInicio, fechaFin);

        if (dto.getCantidadPeriodica() != null) suscripcion.setCantidadPeriodica(dto.getCantidadPeriodica());
        if (dto.getFrecuencia() != null) suscripcion.setFrecuencia(dto.getFrecuencia().name());
        if (dto.getEstado() != null) suscripcion.setEstado(dto.getEstado().name());
        if (dto.getFechaInicio() != null) suscripcion.setFechaInicio(dto.getFechaInicio());
        if (dto.getFechaFin() != null) suscripcion.setFechaFin(dto.getFechaFin());

        return SuscripcionResponseDto.from(suscripcionRepository.save(suscripcion));
    }

    @Override
    @Transactional
    public SuscripcionResponseDto cancelar(UUID id) {
        Suscripcion suscripcion = getSuscripcion(id);
        suscripcion.setEstado("cancelada");
        return SuscripcionResponseDto.from(suscripcionRepository.save(suscripcion));
    }

    private void validarFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaFin != null && !fechaFin.isAfter(fechaInicio)) {
            throw new BusinessException("La fecha fin debe ser posterior a la fecha inicio", HttpStatus.BAD_REQUEST);
        }
    }

    private Suscripcion getSuscripcion(UUID id) {
        return suscripcionRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Suscripcion no encontrada", HttpStatus.NOT_FOUND));
    }
}

