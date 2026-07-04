package com.foodgest.users.servicesimplements;

import com.foodgest.users.dtos.UserCreateRequest;
import com.foodgest.users.dtos.UserResponse;
import com.foodgest.users.dtos.UserUpdateRequest;
import com.foodgest.users.entities.UserEntities;
import com.foodgest.users.repositories.UserRepository;
import com.foodgest.users.servicesinterfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> list() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public UserResponse insert(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(BAD_REQUEST, "El email ya está registrado");
        }

        UserEntities user = new UserEntities();
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

    @Override public UserResponse listId(UUID id) {
        UserEntities user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuario no encontrado"));
        return toResponse(user);
    }

    @Transactional
    public UserResponse update(UUID id, UserUpdateRequest request) {
        UserEntities user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuario no encontrado"));

        if (userRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new ResponseStatusException(BAD_REQUEST, "El email ya está registrado");
        }

        user.setNombre(request.getNombre());
        user.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }
        // tipoUsuario y estado solo los puede modificar un ADMIN: de lo contrario
        // cualquier usuario autenticado podria auto-ascenderse editando su propio perfil.
        if (isAdmin()) {
            user.setTipoUsuario(request.getTipoUsuario());
            user.setEstado(request.getEstado());
        }
        user.setTelefono(request.getTelefono());
        user.setDireccion(request.getDireccion());
        user.setCiudad(request.getCiudad());
        user.setDepartamento(request.getDepartamento());
        user.setCodigoPostal(request.getCodigoPostal());
        user.setFotoPerfilUrl(request.getFotoPerfilUrl());

        return toResponse(userRepository.save(user));
    }

    @Transactional
    @Override public void delete(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND, "Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }


    private boolean isAdmin() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }

    private UserResponse toResponse(UserEntities user) {
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
