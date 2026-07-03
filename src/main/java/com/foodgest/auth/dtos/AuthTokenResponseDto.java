package com.foodgest.auth.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.foodgest.users.entities.UserEntities;

/**
 * Respuesta unificada para login y register con JWT.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthTokenResponseDto {

    private String accessToken;
    private String refreshToken;
    private String token;
    private String tokenType;
    private String tipoUsuario;
    private UserResponseDto user;

    public static AuthTokenResponseDto of(String token, UserEntities usuario) {
        return of(token, null, usuario);
    }

    public static AuthTokenResponseDto of(String accessToken, String refreshToken, UserEntities usuario) {
        AuthTokenResponseDto dto = new AuthTokenResponseDto();
        dto.accessToken = accessToken;
        dto.refreshToken = refreshToken;
        dto.token = accessToken;
        dto.tokenType = "Bearer";
        dto.tipoUsuario = usuario.getTipoUsuario();
        dto.user = UserResponseDto.from(usuario);
        return dto;
    }

    public static AuthTokenResponseDto pending(UserEntities usuario) {
        AuthTokenResponseDto dto = new AuthTokenResponseDto();
        dto.tipoUsuario = usuario.getTipoUsuario();
        dto.user = UserResponseDto.from(usuario);
        return dto;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        this.token = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        this.accessToken = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public UserResponseDto getUser() {
        return user;
    }

    public void setUser(UserResponseDto user) {
        this.user = user;
    }
}
