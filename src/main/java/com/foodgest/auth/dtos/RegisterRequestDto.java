package com.foodgest.auth.dtos;

import com.foodgest.users.enums.TipoUsuarioEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.Map;

/**
 * DTO de entrada para POST /api/auth/register.
 *
 * El campo 'perfil' se tipifica como Map<String, Object> para deserializacion
 * flexible: el AuthService lo convertira al DTO concreto (AgricultorCreateDto
 * o CompradorCreateDto) usando ObjectMapper.convertValue() segun tipo_usuario.
 *
 * MEJORA: se usa Map en lugar de Object generico para evitar cast inseguro
 * y permitir inspeccion directa de claves si fuera necesario en el futuro.
 */
public class RegisterRequestDto {

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

    @NotNull(message = "El tipo de usuario es obligatorio")
    private TipoUsuarioEnum tipoUsuario;

    @NotNull(message = "El campo perfil es obligatorio")
    @Schema(
            description = "Datos del perfil segun tipoUsuario. Para agricultor use nombreFinca, latitud, longitud, etc.",
            example = """
                    {
                      "nombreFinca": "Finca Los Olivos",
                      "hectareas": 5.5,
                      "latitud": -12.0464,
                      "longitud": -77.0428,
                      "direccionParcela": "Av. Central 123",
                      "ruc": "20123456789"
                    }
                    """
    )
    private Map<String, Object> perfil;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public TipoUsuarioEnum getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(TipoUsuarioEnum tipoUsuario) { this.tipoUsuario = tipoUsuario; }

    public Map<String, Object> getPerfil() { return perfil; }
    public void setPerfil(Map<String, Object> perfil) { this.perfil = perfil; }
}
