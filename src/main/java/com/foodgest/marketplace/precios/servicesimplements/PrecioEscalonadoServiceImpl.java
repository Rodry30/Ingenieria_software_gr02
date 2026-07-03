package com.foodgest.marketplace.precios.servicesimplements;

import com.foodgest.marketplace.ofertas.repositories.OfertaRepository;
import com.foodgest.marketplace.precios.dtos.PrecioEscalonadoDto;
import com.foodgest.marketplace.precios.entities.PrecioEscalonado;
import com.foodgest.marketplace.precios.repositories.PrecioEscalonadoRepository;
import com.foodgest.marketplace.precios.servicesinterfaces.IPrecioEscalonadoService;
import com.foodgest.shared.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PrecioEscalonadoServiceImpl implements IPrecioEscalonadoService {

    private final PrecioEscalonadoRepository precioEscalonadoRepository;
    private final OfertaRepository ofertaRepository;

    public PrecioEscalonadoServiceImpl(PrecioEscalonadoRepository precioEscalonadoRepository,
                                       OfertaRepository ofertaRepository) {
        this.precioEscalonadoRepository = precioEscalonadoRepository;
        this.ofertaRepository = ofertaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrecioEscalonadoDto> listByOferta(UUID ofertaId) {
        return precioEscalonadoRepository.findByOfertaIdOrderByCantidadDesdeAsc(ofertaId).stream()
                .map(PrecioEscalonadoDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PrecioEscalonadoDto crear(PrecioEscalonadoDto dto) {
        validarRango(dto.getCantidadDesde(), dto.getCantidadHasta());
        PrecioEscalonado precio = new PrecioEscalonado();
        precio.setOferta(ofertaRepository.findById(dto.getOfertaId())
                .orElseThrow(() -> new BusinessException("Oferta no encontrada", HttpStatus.NOT_FOUND)));
        aplicar(dto, precio);
        return PrecioEscalonadoDto.from(precioEscalonadoRepository.save(precio));
    }

    @Override
    @Transactional
    public PrecioEscalonadoDto actualizar(UUID id, PrecioEscalonadoDto dto) {
        validarRango(dto.getCantidadDesde(), dto.getCantidadHasta());
        PrecioEscalonado precio = getPrecio(id);
        if (dto.getOfertaId() != null && !dto.getOfertaId().equals(precio.getOferta().getId())) {
            precio.setOferta(ofertaRepository.findById(dto.getOfertaId())
                    .orElseThrow(() -> new BusinessException("Oferta no encontrada", HttpStatus.NOT_FOUND)));
        }
        aplicar(dto, precio);
        return PrecioEscalonadoDto.from(precioEscalonadoRepository.save(precio));
    }

    @Override
    @Transactional
    public void eliminar(UUID id) {
        if (!precioEscalonadoRepository.existsById(id)) {
            throw new BusinessException("Precio escalonado no encontrado", HttpStatus.NOT_FOUND);
        }
        precioEscalonadoRepository.deleteById(id);
    }

    private void aplicar(PrecioEscalonadoDto dto, PrecioEscalonado precio) {
        precio.setCantidadDesde(dto.getCantidadDesde());
        precio.setCantidadHasta(dto.getCantidadHasta());
        precio.setPrecioUnitario(dto.getPrecioUnitario());
        precio.setMoneda(dto.getMoneda() != null ? dto.getMoneda() : "PEN");
    }

    private void validarRango(BigDecimal desde, BigDecimal hasta) {
        if (hasta != null && hasta.compareTo(desde) <= 0) {
            throw new BusinessException("La cantidad hasta debe ser mayor que la cantidad desde", HttpStatus.BAD_REQUEST);
        }
    }

    private PrecioEscalonado getPrecio(UUID id) {
        return precioEscalonadoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Precio escalonado no encontrado", HttpStatus.NOT_FOUND));
    }
}

