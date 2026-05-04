package com.ProyectoMercado.grupo2.users.controllers;

import com.ProyectoMercado.grupo2.users.dtos.UserDTO;
import com.ProyectoMercado.grupo2.users.dtos.UserInsertDTO;
import com.ProyectoMercado.grupo2.users.entities.User;
import com.ProyectoMercado.grupo2.users.servicesinterfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService uS;

    @GetMapping
    public List<UserDTO> list() {
        return uS.list().stream().map(u -> {
            UserDTO dto = new UserDTO();
            dto.setId(u.getId());
            dto.setNombre(u.getNombre());
            dto.setEmail(u.getEmail());
            dto.setTipoUsuario(u.getTipoUsuario());
            dto.setEstado(u.getEstado());
            dto.setTelefono(u.getTelefono());
            dto.setDireccion(u.getDireccion());
            dto.setCiudad(u.getCiudad());
            dto.setDepartamento(u.getDepartamento());
            dto.setCodigoPostal(u.getCodigoPostal());
            dto.setFotoPerfilUrl(u.getFotoPerfilUrl());
            dto.setCalificacionPromedio(u.getCalificacionPromedio());
            dto.setTotalCalificaciones(u.getTotalCalificaciones());
            dto.setVerificado(u.getVerificado());
            dto.setCreatedAt(u.getCreatedAt());
            dto.setUpdatedAt(u.getUpdatedAt());
            dto.setUltimoLogin(u.getUltimoLogin());
            return dto;
        }).collect(Collectors.toList());
    }

    @PostMapping
    public void insert(@RequestBody UserInsertDTO dto) {
        User u = new User();
        u.setNombre(dto.getNombre());
        u.setEmail(dto.getEmail());
        u.setPasswordHash(dto.getPasswordHash());
        u.setTipoUsuario(dto.getTipoUsuario());
        u.setTelefono(dto.getTelefono());
        u.setDireccion(dto.getDireccion());
        u.setCiudad(dto.getCiudad());
        u.setDepartamento(dto.getDepartamento());
        u.setCodigoPostal(dto.getCodigoPostal());
        u.setFotoPerfilUrl(dto.getFotoPerfilUrl());
        uS.insert(u);
    }

    @GetMapping("/{id}")
    public UserDTO listId(@PathVariable("id") UUID id) {
        User u = uS.listId(id).orElse(new User());
        UserDTO dto = new UserDTO();
        dto.setId(u.getId());
        dto.setNombre(u.getNombre());
        dto.setEmail(u.getEmail());
        dto.setTipoUsuario(u.getTipoUsuario());
        dto.setEstado(u.getEstado());
        dto.setTelefono(u.getTelefono());
        dto.setDireccion(u.getDireccion());
        dto.setCiudad(u.getCiudad());
        dto.setDepartamento(u.getDepartamento());
        dto.setCodigoPostal(u.getCodigoPostal());
        dto.setFotoPerfilUrl(u.getFotoPerfilUrl());
        dto.setCalificacionPromedio(u.getCalificacionPromedio());
        dto.setTotalCalificaciones(u.getTotalCalificaciones());
        dto.setVerificado(u.getVerificado());
        dto.setCreatedAt(u.getCreatedAt());
        dto.setUpdatedAt(u.getUpdatedAt());
        dto.setUltimoLogin(u.getUltimoLogin());
        return dto;
    }

    @PutMapping
    public void update(@RequestBody UserDTO dto) {
        // En una aplicación real, probablemente no querrás sobreescribir el password o el estado aquí directamente sin verificaciones
        User u = uS.listId(dto.getId()).orElse(new User());
        u.setNombre(dto.getNombre());
        u.setEmail(dto.getEmail());
        u.setTipoUsuario(dto.getTipoUsuario());
        u.setEstado(dto.getEstado());
        u.setTelefono(dto.getTelefono());
        u.setDireccion(dto.getDireccion());
        u.setCiudad(dto.getCiudad());
        u.setDepartamento(dto.getDepartamento());
        u.setCodigoPostal(dto.getCodigoPostal());
        u.setFotoPerfilUrl(dto.getFotoPerfilUrl());
        u.setCalificacionPromedio(dto.getCalificacionPromedio());
        u.setTotalCalificaciones(dto.getTotalCalificaciones());
        u.setVerificado(dto.getVerificado());
        // createdAt no se actualiza, updatedAt se maneja con @PreUpdate
        uS.update(u);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID id) {
        uS.delete(id);
    }
}
