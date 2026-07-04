package com.foodgest.perfiles.transportistas.entities;

import com.foodgest.users.entities.UserEntities;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnTransformer;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transportistas")
public class TransportistaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UserEntities usuario;

    @Column(name = "nombre_completo", length = 100)
    private String nombreCompleto;

    @Column(name = "dni", length = 8)
    private String dni;

    @Column(name = "licencia_conducir", length = 20)
    private String licenciaConducir;

    @Column(name = "tipo_licencia", length = 10)
    @ColumnTransformer(write = "?::tipo_licencia_enum")
    private String tipoLicencia;

    @Column(name = "placa_vehiculo", length = 10)
    private String placaVehiculo;

    @Column(name = "tipo_vehiculo", length = 50)
    @ColumnTransformer(write = "?::tipo_vehiculo_enum")
    private String tipoVehiculo;

    @Column(name = "marca_vehiculo", length = 50)
    private String marcaVehiculo;

    @Column(name = "capacidad_toneladas", precision = 8, scale = 2)
    private BigDecimal capacidadToneladas;

    @Column(name = "verificado")
    private Boolean verificado;

    @Column(name = "calificacion_promedio", precision = 3, scale = 2)
    private BigDecimal calificacionPromedio;

    @Column(name = "disponible")
    private Boolean disponible;

    @Column(name = "latitud_actual", precision = 10, scale = 7)
    private BigDecimal latitudActual;

    @Column(name = "longitud_actual", precision = 10, scale = 7)
    private BigDecimal longitudActual;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UserEntities getUsuario() { return usuario; }
    public void setUsuario(UserEntities usuario) { this.usuario = usuario; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getLicenciaConducir() { return licenciaConducir; }
    public void setLicenciaConducir(String licenciaConducir) { this.licenciaConducir = licenciaConducir; }

    public String getTipoLicencia() { return tipoLicencia; }
    public void setTipoLicencia(String tipoLicencia) { this.tipoLicencia = tipoLicencia; }

    public String getPlacaVehiculo() { return placaVehiculo; }
    public void setPlacaVehiculo(String placaVehiculo) { this.placaVehiculo = placaVehiculo; }

    public String getTipoVehiculo() { return tipoVehiculo; }
    public void setTipoVehiculo(String tipoVehiculo) { this.tipoVehiculo = tipoVehiculo; }

    public String getMarcaVehiculo() { return marcaVehiculo; }
    public void setMarcaVehiculo(String marcaVehiculo) { this.marcaVehiculo = marcaVehiculo; }

    public BigDecimal getCapacidadToneladas() { return capacidadToneladas; }
    public void setCapacidadToneladas(BigDecimal capacidadToneladas) { this.capacidadToneladas = capacidadToneladas; }

    public Boolean getVerificado() { return verificado; }
    public void setVerificado(Boolean verificado) { this.verificado = verificado; }

    public BigDecimal getCalificacionPromedio() { return calificacionPromedio; }
    public void setCalificacionPromedio(BigDecimal calificacionPromedio) { this.calificacionPromedio = calificacionPromedio; }

    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }

    public BigDecimal getLatitudActual() { return latitudActual; }
    public void setLatitudActual(BigDecimal latitudActual) { this.latitudActual = latitudActual; }

    public BigDecimal getLongitudActual() { return longitudActual; }
    public void setLongitudActual(BigDecimal longitudActual) { this.longitudActual = longitudActual; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}