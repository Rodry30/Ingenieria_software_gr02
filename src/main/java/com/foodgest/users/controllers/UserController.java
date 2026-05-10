package com.foodgest.users.controllers;

import com.foodgest.users.dtos.UserResponseDto;
import com.foodgest.users.dtos.UserCreateDto;
import com.foodgest.users.entities.User;
import com.foodgest.users.servicesinterfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired private IUserService uS;

    @GetMapping
    public List<UserResponseDto> list() {
        return uS.list().stream().map(u -> {
            UserResponseDto dto = new UserResponseDto();
            dto.setId(u.getId()); dto.setNombre(u.getNombre()); dto.setEmail(u.getEmail());
            dto.setTipoUsuario(u.getTipoUsuario()); dto.setEstado(u.getEstado());
            dto.setTelefono(u.getTelefono()); dto.setDireccion(u.getDireccion());
            dto.setCiudad(u.getCiudad()); dto.setDepartamento(u.getDepartamento());
            dto.setCodigoPostal(u.getCodigoPostal()); dto.setFotoPerfilUrl(u.getFotoPerfilUrl());
            dto.setCalificacionPromedio(u.getCalificacionPromedio());
            dto.setTotalCalificaciones(u.getTotalCalificaciones());
            dto.setVerificado(u.getVerificado()); dto.setCreatedAt(u.getCreatedAt());
            dto.setUpdatedAt(u.getUpdatedAt()); dto.setUltimoLogin(u.getUltimoLogin());
            return dto;
        }).collect(Collectors.toList());
    }
    @PostMapping
    public void insert(@RequestBody UserCreateDto dto) {
        User u = new User();
        u.setNombre(dto.getNombre()); u.setEmail(dto.getEmail()); u.setPasswordHash(dto.getPasswordHash());
        u.setTipoUsuario(dto.getTipoUsuario()); u.setTelefono(dto.getTelefono());
        u.setDireccion(dto.getDireccion()); u.setCiudad(dto.getCiudad());
        u.setDepartamento(dto.getDepartamento()); u.setCodigoPostal(dto.getCodigoPostal());
        u.setFotoPerfilUrl(dto.getFotoPerfilUrl()); uS.insert(u);
    }
    @GetMapping("/{id}")
    public UserResponseDto listId(@PathVariable("id") UUID id) {
        User u = uS.listId(id).orElse(new User());
        UserResponseDto dto = new UserResponseDto();
        dto.setId(u.getId()); dto.setNombre(u.getNombre()); dto.setEmail(u.getEmail());
        dto.setTipoUsuario(u.getTipoUsuario()); dto.setEstado(u.getEstado());
        dto.setTelefono(u.getTelefono()); dto.setDireccion(u.getDireccion());
        dto.setCiudad(u.getCiudad()); dto.setDepartamento(u.getDepartamento());
        dto.setCodigoPostal(u.getCodigoPostal()); dto.setFotoPerfilUrl(u.getFotoPerfilUrl());
        dto.setCalificacionPromedio(u.getCalificacionPromedio());
        dto.setTotalCalificaciones(u.getTotalCalificaciones());
        dto.setVerificado(u.getVerificado()); dto.setCreatedAt(u.getCreatedAt());
        dto.setUpdatedAt(u.getUpdatedAt()); dto.setUltimoLogin(u.getUltimoLogin());
        return dto;
    }
    @PutMapping
    public void update(@RequestBody UserResponseDto dto) {
        User u = uS.listId(dto.getId()).orElse(new User());
        u.setNombre(dto.getNombre()); u.setEmail(dto.getEmail());
        u.setTipoUsuario(dto.getTipoUsuario()); u.setEstado(dto.getEstado());
        u.setTelefono(dto.getTelefono()); u.setDireccion(dto.getDireccion());
        u.setCiudad(dto.getCiudad()); u.setDepartamento(dto.getDepartamento());
        u.setCodigoPostal(dto.getCodigoPostal()); u.setFotoPerfilUrl(u.getFotoPerfilUrl());
        u.setCalificacionPromedio(dto.getCalificacionPromedio());
        u.setTotalCalificaciones(dto.getTotalCalificaciones());
        u.setVerificado(dto.getVerificado()); uS.update(u);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID id) { uS.delete(id); }
}
