package com.foodgest.auth.dtos;

import com.foodgest.users.entities.UserEntities;

/**
 * Respuesta unificada para login y register con JWT.
 */
public class AuthTokenResponseDto {

    private String token;
    private String tokenType = "Bearer";
    private UserResponseDto user;

    public static AuthTokenResponseDto of(String token, UserEntities usuario) {
        AuthTokenResponseDto dto = new AuthTokenResponseDto();
        dto.token = token;
        dto.user = UserResponseDto.from(usuario);
        return dto;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public UserResponseDto getUser() {
        return user;
    }

    public void setUser(UserResponseDto user) {
        this.user = user;
    }
}
