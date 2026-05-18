package com.foodgest.auth.controllers;

import com.foodgest.auth.dtos.RegisterRequestDto;
import com.foodgest.auth.dtos.UserResponseDto;
import com.foodgest.auth.servicesinterfaces.IAuthService;
import com.foodgest.shared.response.ApiResponse;
import com.foodgest.users.entities.UserEntities;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    /**
     * POST /api/auth/register
     * Endpoint publico. Crea usuario + perfil + wallet en una transaccion.
     *
     * @param dto body del request validado por @Valid
     * @return 201 Created con los datos publicos del usuario
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> register(
            @Valid @RequestBody RegisterRequestDto dto) {

        UserEntities usuario = authService.register(dto);
        UserResponseDto responseDto = UserResponseDto.from(usuario);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        201,
                        "Usuario registrado exitosamente. Pendiente de aprobacion.",
                        responseDto));
    }
}
