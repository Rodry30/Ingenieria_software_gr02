package com.foodgest.perfiles.transportistas.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransportistaResponseDto {

    private UUID id;
    private String nombreCompleto;
    private String dni;
    private String licenciaConducir;
    private String tipoLicencia;
    private String placaVehiculo;
    private String tipoVehiculo;
    private String marcaVehiculo;
    private BigDecimal capacidadToneladas;
    private Boolean verificado;
    private BigDecimal calificacionPromedio;
    private Boolean disponible;
    private BigDecimal latitudActual;
    private BigDecimal longitudActual;
    private LocalDateTime createdAt;

    private UsuarioResumen usuario;

    public static class UsuarioResumen {
        private UUID id;
        private String nombre;
        private String email;
        private String telefono;

        public UUID getId() { return id; }
        public void setId(UUID id) { this.id = id; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getTelefono() { return telefono; }
        public void setTelefono(String telefono) { this.telefono = telefono; }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getLicenciaConducir() {
        return licenciaConducir;
    }

    public void setLicenciaConducir(String licenciaConducir) {
        this.licenciaConducir = licenciaConducir;
    }

    public String getTipoLicencia() {
        return tipoLicencia;
    }

    public void setTipoLicencia(String tipoLicencia) {
        this.tipoLicencia = tipoLicencia;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getMarcaVehiculo() {
        return marcaVehiculo;
    }

    public void setMarcaVehiculo(String marcaVehiculo) {
        this.marcaVehiculo = marcaVehiculo;
    }

    public BigDecimal getCapacidadToneladas() {
        return capacidadToneladas;
    }

    public void setCapacidadToneladas(BigDecimal capacidadToneladas) {
        this.capacidadToneladas = capacidadToneladas;
    }

    public Boolean getVerificado() {
        return verificado;
    }

    public void setVerificado(Boolean verificado) {
        this.verificado = verificado;
    }

    public BigDecimal getCalificacionPromedio() {
        return calificacionPromedio;
    }

    public void setCalificacionPromedio(BigDecimal calificacionPromedio) {
        this.calificacionPromedio = calificacionPromedio;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public BigDecimal getLatitudActual() {
        return latitudActual;
    }

    public void setLatitudActual(BigDecimal latitudActual) {
        this.latitudActual = latitudActual;
    }

    public BigDecimal getLongitudActual() {
        return longitudActual;
    }

    public void setLongitudActual(BigDecimal longitudActual) {
        this.longitudActual = longitudActual;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UsuarioResumen getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResumen usuario) {
        this.usuario = usuario;
    }


}
