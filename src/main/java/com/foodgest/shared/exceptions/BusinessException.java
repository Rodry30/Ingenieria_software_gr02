package com.foodgest.shared.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Excepcion de negocio con HTTP status embebido para que el GlobalExceptionHandler
 * pueda devolver el codigo correcto sin switch/case externo.
 */
public class BusinessException extends RuntimeException {

    private final HttpStatus httpStatus;

    public BusinessException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    // MEJORA: constructor de conveniencia para el caso 400 (el mas comun)
    public BusinessException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
