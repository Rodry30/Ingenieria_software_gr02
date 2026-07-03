package com.foodgest.subastas.pujas.servicesimplements;

import com.foodgest.perfiles.compradores.entities.Comprador;
import com.foodgest.perfiles.compradores.repositories.CompradorRepository;
import com.foodgest.shared.exceptions.BusinessException;
import com.foodgest.subastas.pujas.dtos.PujaCreateDto;
import com.foodgest.subastas.pujas.dtos.PujaResponseDto;
import com.foodgest.subastas.pujas.entities.PujaSubasta;
import com.foodgest.subastas.pujas.repositories.PujaSubastaRepository;
import com.foodgest.subastas.pujas.servicesinterfaces.IPujaService;
import com.foodgest.subastas.subastas.entities.Subasta;
import com.foodgest.subastas.subastas.repositories.SubastaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PujaServiceImpl implements IPujaService {

    private final PujaSubastaRepository pujaSubastaRepository;
    private final SubastaRepository subastaRepository;
    private final CompradorRepository compradorRepository;

    public PujaServiceImpl(PujaSubastaRepository pujaSubastaRepository,
                           SubastaRepository subastaRepository,
                           CompradorRepository compradorRepository) {
        this.pujaSubastaRepository = pujaSubastaRepository;
        this.subastaRepository = subastaRepository;
        this.compradorRepository = compradorRepository;
    }

    @Override
    @Transactional
    public PujaResponseDto pujar(UUID subastaId, PujaCreateDto dto) {
        Subasta subasta = subastaRepository.findById(subastaId)
                .orElseThrow(() -> new BusinessException("Subasta no encontrada", HttpStatus.NOT_FOUND));
        if (!"activa".equals(subasta.getEstado())) {
            throw new BusinessException("La subasta no esta activa", HttpStatus.BAD_REQUEST);
        }
        OffsetDateTime now = OffsetDateTime.now();
        if (now.isBefore(subasta.getFechaInicio()) || now.isAfter(subasta.getFechaFin())) {
            throw new BusinessException("La subasta esta fuera de su ventana de pujas", HttpStatus.BAD_REQUEST);
        }
        var minimo = subasta.getPrecioActual().add(subasta.getIncrementoMinimo());
        if (dto.getMontoPuja().compareTo(minimo) < 0) {
            throw new BusinessException("La puja debe ser al menos " + minimo, HttpStatus.BAD_REQUEST);
        }

        Comprador comprador = compradorRepository.findById(dto.getCompradorId())
                .orElseThrow(() -> new BusinessException("Comprador no encontrado", HttpStatus.NOT_FOUND));

        PujaSubasta puja = new PujaSubasta();
        puja.setSubasta(subasta);
        puja.setComprador(comprador);
        puja.setMontoPuja(dto.getMontoPuja());

        subasta.setPrecioActual(dto.getMontoPuja());
        subasta.setGanador(comprador);
        subastaRepository.save(subasta);
        return PujaResponseDto.from(pujaSubastaRepository.save(puja));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PujaResponseDto> listarPorSubasta(UUID subastaId) {
        return pujaSubastaRepository.findBySubastaIdOrderByMontoPujaDesc(subastaId).stream()
                .map(PujaResponseDto::from)
                .collect(Collectors.toList());
    }
}

