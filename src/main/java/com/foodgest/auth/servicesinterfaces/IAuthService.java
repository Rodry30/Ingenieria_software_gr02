package com.foodgest.auth.servicesinterfaces;

import com.foodgest.auth.dtos.AuthTokenResponseDto;
import com.foodgest.auth.dtos.LoginRequestDto;
import com.foodgest.auth.dtos.RefreshTokenDto;
import com.foodgest.auth.dtos.RegisterRequestDto;

public interface IAuthService {

    /**
     * Registra un nuevo usuario junto con su perfil (agricultor o comprador) y su wallet.
     * Retorna un JWT para que el usuario pueda autenticarse de inmediato.
     */
    AuthTokenResponseDto register(RegisterRequestDto dto);

    /**
     * Autentica un usuario existente y retorna un JWT.
     */
    AuthTokenResponseDto login(LoginRequestDto dto);

    /**
     * Emite un nuevo JWT a partir de un token actual valido.
     */
    AuthTokenResponseDto refresh(RefreshTokenDto dto);

    /**
     * Revoca un refresh token para cerrar sesion.
     */
    void logout(RefreshTokenDto dto);
}
