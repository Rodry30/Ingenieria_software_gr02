package com.foodgest.users.controllers;

import com.foodgest.users.dtos.UserCreateRequest;
import com.foodgest.users.dtos.UserResponse;
import com.foodgest.users.dtos.UserUpdateRequest;
import com.foodgest.users.servicesimplements.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Operaciones de lectura para usuarios")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Listar usuarios")
    public List<UserResponse> listarUsuarios() {
        return userService.list();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID")
    public UserResponse obtenerUsuario(@PathVariable UUID id) {
        return userService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear usuario")
    public UserResponse crearUsuario(@Valid @RequestBody UserCreateRequest request) {
        return userService.crear(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario")
    public UserResponse actualizarUsuario(@PathVariable UUID id, @Valid @RequestBody UserUpdateRequest request) {
        return userService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar usuario")
    public void eliminarUsuario(@PathVariable UUID id) {
        userService.eliminar(id);
    }
}
