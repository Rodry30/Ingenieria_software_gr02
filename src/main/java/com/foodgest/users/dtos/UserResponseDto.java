package com.foodgest.users.dtos;

import java.math.BigDecimal; import java.time.LocalDateTime; import java.util.UUID;

public class UserResponseDto {
    private UUID id;
    private String nombre, email, tipoUsuario, estado, telefono, direccion, ciudad, departamento, codigoPostal, fotoPerfilUrl;
    private BigDecimal calificacionPromedio;
    private Integer totalCalificaciones;
    private Boolean verificado;
    private LocalDateTime createdAt, updatedAt, ultimoLogin;

    public UUID getId(){return id;} public void setId(UUID v){id=v;}
    public String getNombre(){return nombre;} public void setNombre(String v){nombre=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getTipoUsuario(){return tipoUsuario;} public void setTipoUsuario(String v){tipoUsuario=v;}
    public String getEstado(){return estado;} public void setEstado(String v){estado=v;}
    public String getTelefono(){return telefono;} public void setTelefono(String v){telefono=v;}
    public String getDireccion(){return direccion;} public void setDireccion(String v){direccion=v;}
    public String getCiudad(){return ciudad;} public void setCiudad(String v){ciudad=v;}
    public String getDepartamento(){return departamento;} public void setDepartamento(String v){departamento=v;}
    public String getCodigoPostal(){return codigoPostal;} public void setCodigoPostal(String v){codigoPostal=v;}
    public String getFotoPerfilUrl(){return fotoPerfilUrl;} public void setFotoPerfilUrl(String v){fotoPerfilUrl=v;}
    public BigDecimal getCalificacionPromedio(){return calificacionPromedio;} public void setCalificacionPromedio(BigDecimal v){calificacionPromedio=v;}
    public Integer getTotalCalificaciones(){return totalCalificaciones;} public void setTotalCalificaciones(Integer v){totalCalificaciones=v;}
    public Boolean getVerificado(){return verificado;} public void setVerificado(Boolean v){verificado=v;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
    public LocalDateTime getUpdatedAt(){return updatedAt;} public void setUpdatedAt(LocalDateTime v){updatedAt=v;}
    public LocalDateTime getUltimoLogin(){return ultimoLogin;} public void setUltimoLogin(LocalDateTime v){ultimoLogin=v;}
}
