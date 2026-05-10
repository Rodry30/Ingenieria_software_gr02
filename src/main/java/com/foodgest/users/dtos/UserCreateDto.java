package com.foodgest.users.dtos;

public class UserCreateDto {
    private String nombre, email, passwordHash, tipoUsuario, telefono, direccion, ciudad, departamento, codigoPostal, fotoPerfilUrl;
    public String getNombre(){return nombre;} public void setNombre(String v){nombre=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
    public String getTipoUsuario(){return tipoUsuario;} public void setTipoUsuario(String v){tipoUsuario=v;}
    public String getTelefono(){return telefono;} public void setTelefono(String v){telefono=v;}
    public String getDireccion(){return direccion;} public void setDireccion(String v){direccion=v;}
    public String getCiudad(){return ciudad;} public void setCiudad(String v){ciudad=v;}
    public String getDepartamento(){return departamento;} public void setDepartamento(String v){departamento=v;}
    public String getCodigoPostal(){return codigoPostal;} public void setCodigoPostal(String v){codigoPostal=v;}
    public String getFotoPerfilUrl(){return fotoPerfilUrl;} public void setFotoPerfilUrl(String v){fotoPerfilUrl=v;}
}
