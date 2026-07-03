package com.foodgest.auth.dtos;

import jakarta.validation.constraints.AssertTrue;

public class RefreshTokenDto {

    private String refreshToken;

    private String token;

    @AssertTrue(message = "El refreshToken es obligatorio")
    public boolean isTokenPresent() {
        String value = getRefreshToken();
        return value != null && !value.isBlank();
    }

    public String getRefreshToken() {
        return refreshToken != null ? refreshToken : token;
    }

    public String getToken() {
        return getRefreshToken();
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

