package com.ProyectoMercado.grupo2.common.exception;

import com.ProyectoMercado.grupo2.common.dto.ApiErrorResponse;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        Map<String, String> validationErrors = new LinkedHashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ApiErrorResponse response = baseResponse(
                HttpStatus.BAD_REQUEST,
                "Error de validacion en la solicitud",
                request.getRequestURI()
        );
        response.setValidationErrors(validationErrors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request
    ) {
        ApiErrorResponse response = baseResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleBadJson(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {
        ApiErrorResponse response = baseResponse(
                HttpStatus.BAD_REQUEST,
                "JSON invalido o tipo de dato incorrecto en la solicitud",
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrity(
            DataIntegrityViolationException ex,
            HttpServletRequest request
    ) {
        String message = "Violacion de integridad de datos";
        String cause = ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : null;

        if (cause != null) {
            if (cause.contains("users_email_key")) {
                message = "El email ya esta registrado";
            } else if (cause.contains("estado_usuario_enum")) {
                message = "Valor invalido para estado. Revisa que coincida con tu enum de base de datos";
            } else if (cause.contains("tipo_usuario_enum")) {
                message = "Valor invalido para tipoUsuario. Revisa que coincida con tu enum de base de datos";
            } else if (cause.contains("null value")) {
                message = "Faltan campos obligatorios en la solicitud";
            }
        }

        ApiErrorResponse response = baseResponse(
                HttpStatus.BAD_REQUEST,
                message,
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({DataAccessException.class, JpaSystemException.class, PersistenceException.class})
    public ResponseEntity<ApiErrorResponse> handlePersistence(
            Exception ex,
            HttpServletRequest request
    ) {
        String causeMessage = ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();
        ApiErrorResponse response = baseResponse(
                HttpStatus.BAD_REQUEST,
                "Error de persistencia en base de datos: " + sanitize(causeMessage),
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrorResponse> handleResponseStatus(
            ResponseStatusException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        ApiErrorResponse response = baseResponse(
                status,
                ex.getReason() != null ? ex.getReason() : "Error en la solicitud",
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request
    ) {
        String causeMessage = ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();
        ApiErrorResponse response = baseResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error interno del servidor: " + sanitize(causeMessage),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private ApiErrorResponse baseResponse(HttpStatus status, String message, String path) {
        ApiErrorResponse response = new ApiErrorResponse();
        response.setStatus(status.value());
        response.setError(status.getReasonPhrase());
        response.setMessage(message);
        response.setPath(path);
        response.setTimestamp(OffsetDateTime.now());
        return response;
    }

    private String sanitize(String message) {
        if (message == null || message.isBlank()) {
            return "sin detalle";
        }
        return message.replaceAll("\\s+", " ").trim();
    }
}
