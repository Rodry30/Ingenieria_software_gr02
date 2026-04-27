package com.ProyectoMercado.grupo2.users.controller;

import com.ProyectoMercado.grupo2.users.dto.UserResponse;
import com.ProyectoMercado.grupo2.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Operaciones de lectura para usuarios")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Listar usuarios")
    public List<UserResponse> listarUsuarios() {
        return userService.listarUsuarios();
    }
}
