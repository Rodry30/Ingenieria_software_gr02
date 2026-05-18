package com.foodgest.shared.exceptions;

import com.foodgest.shared.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /** Errores de negocio con status dinamico (400, 409, etc.) */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException ex) {
        int code = ex.getHttpStatus().value();
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(ApiResponse.error(code, ex.getMessage()));
    }

    /** Errores de validacion de @Valid en el body del request */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException ex) {
        // MEJORA: se concatenan todos los mensajes de campo en una sola linea para
        // facilitar el consumo desde el frontend sin iterar un array.
        String detalle = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(400, detalle));
    }

    /** Fallback para cualquier excepcion no controlada -> 500 sin exponer stack trace */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneric(Exception ex) {
        // No loguear el stack trace hacia el cliente
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(500, "Error interno del servidor. Por favor contacte al administrador."));
    }
}
