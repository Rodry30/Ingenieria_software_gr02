package com.foodgest.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para POST /api/auth/bootstrap-admin.
 * Solo funciona mientras no exista ningun usuario tipoUsuario='admin' en el
 * sistema: crea el primer administrador. Una vez que existe uno, este
 * endpoint queda permanentemente bloqueado y la creacion de nuevos admins
 * pasa por POST /api/users (que ya exige ser ADMIN).
 */
public class BootstrapAdminDto {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato valido")
    private String email;

    @NotBlank(message = "La contrasena es obligatoria")
    @Size(min = 8, max = 100, message = "La contrasena debe tener entre 8 y 100 caracteres")
    private String password;

    @Size(max = 15, message = "El telefono no puede superar los 15 caracteres")
    private String telefono;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
