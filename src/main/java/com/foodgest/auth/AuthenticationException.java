package com.foodgest.auth;

public class AuthenticationException extends org.springframework.security.core.AuthenticationException {

    public AuthenticationException(String msg) {
        super(msg);
    }

    public AuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
