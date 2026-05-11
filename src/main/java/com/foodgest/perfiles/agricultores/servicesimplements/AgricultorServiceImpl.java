package com.foodgest.perfiles.agricultores.servicesimplements;

import com.foodgest.perfiles.agricultores.dtos.AgricultorCreateDto;
import com.foodgest.perfiles.agricultores.dtos.AgricultorResponseDto;
import com.foodgest.perfiles.agricultores.dtos.AgricultorUpdateDto;
import com.foodgest.perfiles.agricultores.entities.AgricultorEntity;
import com.foodgest.perfiles.agricultores.repositories.AgricultorRepository;
import com.foodgest.perfiles.agricultores.servicesinterfaces.IAgricultorService;
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
public class AgricultorServiceImpl implements IAgricultorService {

    @Autowired
    private AgricultorRepository agricultorRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<AgricultorResponseDto> listar() {
        return agricultorRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public AgricultorResponseDto obtenerPorId(UUID id) {
        AgricultorEntity agricultor = agricultorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Agricultor no encontrado"));
        return toResponse(agricultor);
    }

    @Override
    @Transactional
    public AgricultorResponseDto crear(AgricultorCreateDto dto) {
        UserEntities usuario = userRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuario no encontrado"));

        if (agricultorRepository.existsByUsuarioId(dto.getUsuarioId())) {
            throw new ResponseStatusException(BAD_REQUEST, "Este usuario ya tiene perfil de agricultor");
        }

        AgricultorEntity agricultor = new AgricultorEntity();
        agricultor.setUsuario(usuario);
        agricultor.setNombreFinca(dto.getNombreFinca());
        agricultor.setHectareas(dto.getHectareas());
        agricultor.setTipoCultivoPrincipal(dto.getTipoCultivoPrincipal());
        agricultor.setDireccionParcela(dto.getDireccionParcela());
        agricultor.setLatitud(dto.getLatitud());
        agricultor.setLongitud(dto.getLongitud());
        agricultor.setRuc(dto.getRuc());
        agricultor.setCuentaBancaria(dto.getCuentaBancaria());
        agricultor.setBanco(dto.getBanco());

        return toResponse(agricultorRepository.save(agricultor));
    }

    @Override
    @Transactional
    public AgricultorResponseDto actualizar(UUID id, AgricultorUpdateDto dto) {
        AgricultorEntity agricultor = agricultorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Agricultor no encontrado"));

        agricultor.setNombreFinca(dto.getNombreFinca());
        agricultor.setHectareas(dto.getHectareas());
        agricultor.setTipoCultivoPrincipal(dto.getTipoCultivoPrincipal());
        agricultor.setDireccionParcela(dto.getDireccionParcela());
        agricultor.setLatitud(dto.getLatitud());
        agricultor.setLongitud(dto.getLongitud());
        agricultor.setRuc(dto.getRuc());
        agricultor.setCuentaBancaria(dto.getCuentaBancaria());
        agricultor.setBanco(dto.getBanco());

        return toResponse(agricultorRepository.save(agricultor));
    }

    @Override
    @Transactional
    public void eliminar(UUID id) {
        if (!agricultorRepository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND, "Agricultor no encontrado");
        }
        agricultorRepository.deleteById(id);
    }

    private AgricultorResponseDto toResponse(AgricultorEntity a) {
        AgricultorResponseDto dto = new AgricultorResponseDto();
        dto.setId(a.getId());
        dto.setNombreFinca(a.getNombreFinca());
        dto.setHectareas(a.getHectareas());
        dto.setTipoCultivoPrincipal(a.getTipoCultivoPrincipal());
        dto.setDireccionParcela(a.getDireccionParcela());
        dto.setLatitud(a.getLatitud());
        dto.setLongitud(a.getLongitud());
        dto.setRuc(a.getRuc());
        dto.setCuentaBancaria(a.getCuentaBancaria());
        dto.setBanco(a.getBanco());
        dto.setCreatedAt(a.getCreatedAt());

        // Armar objeto usuario anidado
        AgricultorResponseDto.UsuarioResumen usuarioResumen = new AgricultorResponseDto.UsuarioResumen();
        usuarioResumen.setId(a.getUsuario().getId());
        usuarioResumen.setNombre(a.getUsuario().getNombre());
        usuarioResumen.setEmail(a.getUsuario().getEmail());
        usuarioResumen.setTelefono(a.getUsuario().getTelefono());
        dto.setUsuario(usuarioResumen);

        return dto;
    }
}