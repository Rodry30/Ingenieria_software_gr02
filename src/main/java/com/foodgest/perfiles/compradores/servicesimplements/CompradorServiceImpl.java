package com.foodgest.perfiles.compradores.servicesimplements;

import com.foodgest.perfiles.compradores.dtos.CompradorCreateDto;
import com.foodgest.perfiles.compradores.dtos.CompradorResponseDto;
import com.foodgest.perfiles.compradores.dtos.CompradorUpdateDto;
import com.foodgest.perfiles.compradores.entities.Comprador;
import com.foodgest.perfiles.compradores.repositories.CompradorRepository;
import com.foodgest.perfiles.compradores.servicesinterfaces.ICompradorService;
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
public class CompradorServiceImpl implements ICompradorService {

    @Autowired
    private CompradorRepository compradorRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<CompradorResponseDto> listar() {
        return compradorRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CompradorResponseDto obtenerPorId(UUID id) {
        Comprador comprador = compradorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Comprador no encontrado"));
        return toResponse(comprador);
    }

    @Override
    @Transactional
    public CompradorResponseDto crear(CompradorCreateDto dto) {
        if (dto.getUsuarioId() == null) {
            throw new ResponseStatusException(BAD_REQUEST, "usuarioId es obligatorio");
        }

        UserEntities usuario = userRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuario no encontrado"));

        if (compradorRepository.existsByUsuarioId(dto.getUsuarioId())) {
            throw new ResponseStatusException(BAD_REQUEST, "Este usuario ya tiene perfil de comprador");
        }

        Comprador comprador = dto.toEntity(usuario);
        if (dto.getLimiteCredito() != null) comprador.setLimiteCredito(dto.getLimiteCredito());

        return toResponse(compradorRepository.save(comprador));
    }

    @Override
    @Transactional
    public CompradorResponseDto actualizar(UUID id, CompradorUpdateDto dto) {
        Comprador comprador = compradorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Comprador no encontrado"));
        if (dto.getTipoComprador() != null) comprador.setTipoComprador(dto.getTipoComprador());
        if (dto.getRazonSocial() != null) comprador.setRazonSocial(dto.getRazonSocial());
        if (dto.getRuc() != null) comprador.setRuc(dto.getRuc());
        if (dto.getDireccionEntregaDefault() != null) comprador.setDireccionEntregaDefault(dto.getDireccionEntregaDefault());
        if (dto.getLatitudEntrega() != null) comprador.setLatitudEntrega(dto.getLatitudEntrega());
        if (dto.getLongitudEntrega() != null) comprador.setLongitudEntrega(dto.getLongitudEntrega());
        if (dto.getLimiteCredito() != null) comprador.setLimiteCredito(dto.getLimiteCredito());

        return toResponse(compradorRepository.save(comprador));
    }

    @Override
    @Transactional
    public void eliminar(UUID id) {
        if (!compradorRepository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND, "Comprador no encontrado");
        }
        compradorRepository.deleteById(id);
    }

    private CompradorResponseDto toResponse(Comprador c) {
        CompradorResponseDto dto = new CompradorResponseDto();
        dto.setId(c.getId());
        dto.setTipoComprador(c.getTipoComprador() == null ? null : c.getTipoComprador().name());
        dto.setRazonSocial(c.getRazonSocial());
        dto.setRuc(c.getRuc());
        dto.setDireccionEntregaDefault(c.getDireccionEntregaDefault());
        dto.setLatitudEntrega(c.getLatitudEntrega());
        dto.setLongitudEntrega(c.getLongitudEntrega());
        dto.setLimiteCredito(c.getLimiteCredito());
        dto.setCreatedAt(c.getCreatedAt() == null ? null : c.getCreatedAt().toLocalDateTime());

        CompradorResponseDto.UsuarioResumen usuarioResumen = new CompradorResponseDto.UsuarioResumen();
        usuarioResumen.setId(c.getUsuario().getId());
        usuarioResumen.setNombre(c.getUsuario().getNombre());
        usuarioResumen.setEmail(c.getUsuario().getEmail());
        usuarioResumen.setTelefono(c.getUsuario().getTelefono());
        dto.setUsuario(usuarioResumen);

        return dto;
    }
}
