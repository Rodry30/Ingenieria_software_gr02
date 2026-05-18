package com.foodgest.shared.response;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Envelope estandar para todas las respuestas de la API.
 * El campo 'data' se omite del JSON cuando es null, para respuestas de error limpias.
 *
 * @param <T> tipo del payload de datos
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private int status;
    private String message;
    private T data;

    // MEJORA: se usan factory methods estaticos para legibilidad en el controller/service
    public static <T> ApiResponse<T> success(int status, String message, T data) {
        ApiResponse<T> r = new ApiResponse<>();
        r.status = status;
        r.message = message;
        r.data = data;
        return r;
    }

    public static <T> ApiResponse<T> error(int status, String message) {
        ApiResponse<T> r = new ApiResponse<>();
        r.status = status;
        r.message = message;
        return r;
    }

    public ApiResponse() {}

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}
