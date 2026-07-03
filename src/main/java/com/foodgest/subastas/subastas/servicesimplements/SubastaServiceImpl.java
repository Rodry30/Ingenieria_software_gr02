package com.foodgest.subastas.subastas.servicesimplements;

import com.foodgest.catalogo.repositories.ProductoRepository;
import com.foodgest.perfiles.agricultores.repositories.AgricultorRepository;
import com.foodgest.shared.exceptions.BusinessException;
import com.foodgest.subastas.subastas.dtos.SubastaCreateDto;
import com.foodgest.subastas.subastas.dtos.SubastaEstadoDto;
import com.foodgest.subastas.subastas.dtos.SubastaResponseDto;
import com.foodgest.subastas.subastas.entities.Subasta;
import com.foodgest.subastas.subastas.repositories.SubastaRepository;
import com.foodgest.subastas.subastas.servicesinterfaces.ISubastaService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SubastaServiceImpl implements ISubastaService {

    private final SubastaRepository subastaRepository;
    private final ProductoRepository productoRepository;
    private final AgricultorRepository agricultorRepository;

    public SubastaServiceImpl(SubastaRepository subastaRepository,
                              ProductoRepository productoRepository,
                              AgricultorRepository agricultorRepository) {
        this.subastaRepository = subastaRepository;
        this.productoRepository = productoRepository;
        this.agricultorRepository = agricultorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubastaResponseDto> list(String estado) {
        var subastas = estado == null ? subastaRepository.findAll() : subastaRepository.findByEstado(estado);
        return subastas.stream().map(SubastaResponseDto::from).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SubastaResponseDto listId(UUID id) {
        return SubastaResponseDto.from(getSubasta(id));
    }

    @Override
    @Transactional
    public SubastaResponseDto crear(SubastaCreateDto dto) {
        if (!dto.getFechaFin().isAfter(dto.getFechaInicio())) {
            throw new BusinessException("La fecha de fin debe ser posterior a la fecha de inicio", HttpStatus.BAD_REQUEST);
        }
        Subasta subasta = new Subasta();
        subasta.setProducto(productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new BusinessException("Producto no encontrado", HttpStatus.NOT_FOUND)));
        subasta.setAgricultor(agricultorRepository.findById(dto.getAgricultorId())
                .orElseThrow(() -> new BusinessException("Agricultor no encontrado", HttpStatus.NOT_FOUND)));
        subasta.setCantidadLote(dto.getCantidadLote());
        subasta.setPrecioBase(dto.getPrecioBase());
        subasta.setPrecioActual(dto.getPrecioBase());
        subasta.setIncrementoMinimo(dto.getIncrementoMinimo());
        subasta.setFechaInicio(dto.getFechaInicio());
        subasta.setFechaFin(dto.getFechaFin());
        subasta.setEstado(dto.getFechaInicio().isAfter(OffsetDateTime.now()) ? "programada" : "activa");
        return SubastaResponseDto.from(subastaRepository.save(subasta));
    }

    @Override
    @Transactional
    public SubastaResponseDto cambiarEstado(UUID id, SubastaEstadoDto dto) {
        Subasta subasta = getSubasta(id);
        subasta.setEstado(dto.getEstado());
        return SubastaResponseDto.from(subastaRepository.save(subasta));
    }

    private Subasta getSubasta(UUID id) {
        return subastaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Subasta no encontrada", HttpStatus.NOT_FOUND));
    }
}

