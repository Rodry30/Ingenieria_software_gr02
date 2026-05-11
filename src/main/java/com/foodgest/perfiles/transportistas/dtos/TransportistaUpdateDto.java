package com.foodgest.perfiles.transportistas.dtos;

import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class TransportistaUpdateDto {

    @Size(max = 100)
    private String nombreCompleto;

    @Size(max = 8)
    private String dni;

    @Size(max = 20)
    private String licenciaConducir;

    @Size(max = 10)
    private String tipoLicencia;

    @Size(max = 10)
    private String placaVehiculo;

    @Size(max = 50)
    private String tipoVehiculo;

    @Size(max = 50)
    private String marcaVehiculo;

    private BigDecimal capacidadToneladas;

    private Boolean disponible;

    private BigDecimal latitudActual;

    private BigDecimal longitudActual;


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
}
