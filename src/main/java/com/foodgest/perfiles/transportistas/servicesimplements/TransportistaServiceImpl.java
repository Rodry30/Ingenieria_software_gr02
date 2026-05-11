package com.foodgest.perfiles.transportistas.servicesimplements;

import com.foodgest.perfiles.transportistas.dtos.TransportistaCreateDto;
import com.foodgest.perfiles.transportistas.dtos.TransportistaResponseDto;
import com.foodgest.perfiles.transportistas.dtos.TransportistaUpdateDto;
import com.foodgest.perfiles.transportistas.entities.TransportistaEntity;
import com.foodgest.perfiles.transportistas.repositories.TransportistaRepository;
import com.foodgest.perfiles.transportistas.servicesinterfaces.ITransportistaService;
import com.foodgest.users.entities.UserEntities;
import com.foodgest.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@Service
public class TransportistaServiceImpl implements ITransportistaService {

    @Autowired
    private TransportistaRepository transportistaRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<TransportistaResponseDto> listar() {
        return transportistaRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public TransportistaResponseDto obtenerPorId(UUID id) {
        TransportistaEntity transportista = transportistaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Transportista no encontrado"));
        return toResponse(transportista);
    }

    @Override
    @Transactional
    public TransportistaResponseDto crear(TransportistaCreateDto dto) {
        UserEntities usuario = userRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuario no encontrado"));

        if (transportistaRepository.existsByUsuarioId(dto.getUsuarioId()))
            throw new ResponseStatusException(BAD_REQUEST, "Este usuario ya tiene perfil de transportista");

        if (transportistaRepository.existsByDni(dto.getDni()))
            throw new ResponseStatusException(BAD_REQUEST, "El DNI ya está registrado");

        if (transportistaRepository.existsByPlacaVehiculo(dto.getPlacaVehiculo()))
            throw new ResponseStatusException(BAD_REQUEST, "La placa ya está registrada");

        TransportistaEntity transportista = new TransportistaEntity();
        transportista.setUsuario(usuario);
        transportista.setNombreCompleto(dto.getNombreCompleto());
        transportista.setDni(dto.getDni());
        transportista.setLicenciaConducir(dto.getLicenciaConducir());
        transportista.setTipoLicencia(dto.getTipoLicencia());
        transportista.setPlacaVehiculo(dto.getPlacaVehiculo());
        transportista.setTipoVehiculo(dto.getTipoVehiculo());
        transportista.setMarcaVehiculo(dto.getMarcaVehiculo());
        transportista.setCapacidadToneladas(dto.getCapacidadToneladas());
        transportista.setDisponible(dto.getDisponible() != null ? dto.getDisponible() : true);
        transportista.setVerificado(false);
        transportista.setCalificacionPromedio(java.math.BigDecimal.ZERO);
        transportista.setLatitudActual(dto.getLatitudActual());
        transportista.setLongitudActual(dto.getLongitudActual());

        return toResponse(transportistaRepository.save(transportista));
    }

    @Override
    @Transactional
    public TransportistaResponseDto actualizar(UUID id, TransportistaUpdateDto dto) {
        TransportistaEntity transportista = transportistaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Transportista no encontrado"));

        transportista.setNombreCompleto(dto.getNombreCompleto());
        transportista.setDni(dto.getDni());
        transportista.setLicenciaConducir(dto.getLicenciaConducir());
        transportista.setTipoLicencia(dto.getTipoLicencia());
        transportista.setPlacaVehiculo(dto.getPlacaVehiculo());
        transportista.setTipoVehiculo(dto.getTipoVehiculo());
        transportista.setMarcaVehiculo(dto.getMarcaVehiculo());
        transportista.setCapacidadToneladas(dto.getCapacidadToneladas());
        transportista.setDisponible(dto.getDisponible());
        transportista.setLatitudActual(dto.getLatitudActual());
        transportista.setLongitudActual(dto.getLongitudActual());

        return toResponse(transportistaRepository.save(transportista));
    }

    @Override
    @Transactional
    public void eliminar(UUID id) {
        if (!transportistaRepository.existsById(id))
            throw new ResponseStatusException(NOT_FOUND, "Transportista no encontrado");
        transportistaRepository.deleteById(id);
    }

    private TransportistaResponseDto toResponse(TransportistaEntity t) {
        TransportistaResponseDto dto = new TransportistaResponseDto();
        dto.setId(t.getId());
        dto.setNombreCompleto(t.getNombreCompleto());
        dto.setDni(t.getDni());
        dto.setLicenciaConducir(t.getLicenciaConducir());
        dto.setTipoLicencia(t.getTipoLicencia());
        dto.setPlacaVehiculo(t.getPlacaVehiculo());
        dto.setTipoVehiculo(t.getTipoVehiculo());
        dto.setMarcaVehiculo(t.getMarcaVehiculo());
        dto.setCapacidadToneladas(t.getCapacidadToneladas());
        dto.setVerificado(t.getVerificado());
        dto.setCalificacionPromedio(t.getCalificacionPromedio());
        dto.setDisponible(t.getDisponible());
        dto.setLatitudActual(t.getLatitudActual());
        dto.setLongitudActual(t.getLongitudActual());
        dto.setCreatedAt(t.getCreatedAt());

        TransportistaResponseDto.UsuarioResumen usuarioResumen = new TransportistaResponseDto.UsuarioResumen();
        usuarioResumen.setId(t.getUsuario().getId());
        usuarioResumen.setNombre(t.getUsuario().getNombre());
        usuarioResumen.setEmail(t.getUsuario().getEmail());
        usuarioResumen.setTelefono(t.getUsuario().getTelefono());
        dto.setUsuario(usuarioResumen);

        return dto;
    }
}
