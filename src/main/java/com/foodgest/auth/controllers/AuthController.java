package com.foodgest.auth.controllers;

import com.foodgest.auth.dtos.AuthTokenResponseDto;
import com.foodgest.auth.dtos.BootstrapAdminDto;
import com.foodgest.auth.dtos.LoginRequestDto;
import com.foodgest.auth.dtos.RefreshTokenDto;
import com.foodgest.auth.dtos.RegisterRequestDto;
import com.foodgest.auth.servicesinterfaces.IAuthService;
import com.foodgest.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticacion", description = "Registro y login con JWT")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Registrar usuario", description = "Crea un usuario agricultor o comprador en estado pendiente")
    public ResponseEntity<ApiResponse<AuthTokenResponseDto>> register(
            @Valid @RequestBody RegisterRequestDto dto) {

        AuthTokenResponseDto response = authService.register(dto);

        boolean activado = response.getAccessToken() != null;
        String mensaje = activado
                ? "Usuario registrado y activado exitosamente."
                : "Usuario registrado exitosamente. Pendiente de aprobacion.";

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, mensaje, response));
    }

    @PostMapping("/bootstrap-admin")
    @Operation(summary = "Crear el primer admin", description = "Solo funciona si todavia no existe ningun usuario admin en el sistema")
    public ResponseEntity<ApiResponse<AuthTokenResponseDto>> bootstrapAdmin(
            @Valid @RequestBody BootstrapAdminDto dto) {

        AuthTokenResponseDto response = authService.bootstrapAdmin(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "Administrador creado exitosamente.", response));
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesion", description = "Autentica con email y contrasena, retorna un JWT")
    public ResponseEntity<ApiResponse<AuthTokenResponseDto>> login(
            @Valid @RequestBody LoginRequestDto dto) {

        AuthTokenResponseDto response = authService.login(dto);

        return ResponseEntity.ok(ApiResponse.success(
                200,
                "Inicio de sesion exitoso.",
                response));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Renovar token", description = "Emite un nuevo JWT a partir de un token valido")
    public ResponseEntity<ApiResponse<AuthTokenResponseDto>> refresh(
            @Valid @RequestBody RefreshTokenDto dto) {

        AuthTokenResponseDto response = authService.refresh(dto);

        return ResponseEntity.ok(ApiResponse.success(
                200,
                "Token renovado exitosamente.",
                response));
    }

    @PostMapping("/logout")
    @Operation(summary = "Cerrar sesion", description = "Revoca el refresh token activo")
    public ResponseEntity<ApiResponse<Void>> logout(@Valid @RequestBody RefreshTokenDto dto) {
        authService.logout(dto);
        return ResponseEntity.ok(ApiResponse.success(200, "Sesion cerrada correctamente.", null));
    }
}
