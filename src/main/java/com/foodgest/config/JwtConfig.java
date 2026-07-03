package com.foodgest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {

    @Value("${jwt.expiration-ms:3600000}")
    private long accessExpirationMs;

    @Value("${jwt.refresh-expiration-ms:604800000}")
    private long refreshExpirationMs;

    public long getAccessExpirationMs() { return accessExpirationMs; }
    public long getRefreshExpirationMs() { return refreshExpirationMs; }
}

