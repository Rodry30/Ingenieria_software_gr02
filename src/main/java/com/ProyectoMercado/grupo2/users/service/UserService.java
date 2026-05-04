package com.ProyectoMercado.grupo2.users.service;

import com.ProyectoMercado.grupo2.users.dto.UserCreateRequest;
import com.ProyectoMercado.grupo2.users.dto.UserResponse;
import com.ProyectoMercado.grupo2.users.dto.UserUpdateRequest;
import com.ProyectoMercado.grupo2.users.entity.UserEntity;
import com.ProyectoMercado.grupo2.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponse> listarUsuarios() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public UserResponse obtenerPorId(UUID id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuario no encontrado"));
        return toResponse(user);
    }

    @Transactional
    public UserResponse crear(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(BAD_REQUEST, "El email ya está registrado");
        }

        UserEntity user = new UserEntity();
        user.setNombre(request.getNombre());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setTipoUsuario(request.getTipoUsuario());
        user.setEstado(request.getEstado());
        user.setTelefono(request.getTelefono());
        user.setDireccion(request.getDireccion());
        user.setCiudad(request.getCiudad());
        user.setDepartamento(request.getDepartamento());
        user.setCodigoPostal(request.getCodigoPostal());
        user.setFotoPerfilUrl(request.getFotoPerfilUrl());
        user.setVerificado(false);

        return toResponse(userRepository.save(user));
    }

    @Transactional
    public UserResponse actualizar(UUID id, UserUpdateRequest request) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuario no encontrado"));

        if (userRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new ResponseStatusException(BAD_REQUEST, "El email ya está registrado");
        }

        user.setNombre(request.getNombre());
        user.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }
        user.setTipoUsuario(request.getTipoUsuario());
        user.setEstado(request.getEstado());
        user.setTelefono(request.getTelefono());
        user.setDireccion(request.getDireccion());
        user.setCiudad(request.getCiudad());
        user.setDepartamento(request.getDepartamento());
        user.setCodigoPostal(request.getCodigoPostal());
        user.setFotoPerfilUrl(request.getFotoPerfilUrl());

        return toResponse(userRepository.save(user));
    }

    @Transactional
    public void eliminar(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND, "Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }

    private UserResponse toResponse(UserEntity user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setNombre(user.getNombre());
        response.setEmail(user.getEmail());
        response.setTipoUsuario(user.getTipoUsuario());
        response.setEstado(user.getEstado());
        response.setTelefono(user.getTelefono());
        response.setDireccion(user.getDireccion());
        response.setCiudad(user.getCiudad());
        response.setDepartamento(user.getDepartamento());
        response.setCodigoPostal(user.getCodigoPostal());
        response.setFotoPerfilUrl(user.getFotoPerfilUrl());
        response.setCalificacionPromedio(user.getCalificacionPromedio());
        response.setTotalCalificaciones(user.getTotalCalificaciones());
        response.setVerificado(user.getVerificado());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}
