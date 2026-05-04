package com.ProyectoMercado.grupo2.categorias.controllers;

import com.ProyectoMercado.grupo2.categorias.dtos.CategoriaDTO;
import com.ProyectoMercado.grupo2.categorias.dtos.CategoriaInsertDTO;
import com.ProyectoMercado.grupo2.categorias.entities.Categoria;
import com.ProyectoMercado.grupo2.categorias.servicesinterfaces.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private ICategoriaService cS;

    @GetMapping
    public List<CategoriaDTO> list() {
        return cS.list().stream().map(c -> {
            CategoriaDTO dto = new CategoriaDTO();
            dto.setId(c.getId());
            dto.setNombre(c.getNombre());
            dto.setDescripcion(c.getDescripcion());
            dto.setImagenUrl(c.getImagenUrl());
            dto.setActivo(c.getActivo());
            dto.setOrden(c.getOrden());
            return dto;
        }).collect(Collectors.toList());
    }

    @PostMapping
    public void insert(@RequestBody CategoriaInsertDTO dto) {
        Categoria c = new Categoria();
        c.setNombre(dto.getNombre());
        c.setDescripcion(dto.getDescripcion());
        c.setImagenUrl(dto.getImagenUrl());
        c.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        c.setOrden(dto.getOrden() != null ? dto.getOrden() : 0);
        cS.insert(c);
    }

    @GetMapping("/{id}")
    public CategoriaDTO listId(@PathVariable("id") UUID id) {
        Categoria c = cS.listId(id).orElse(new Categoria());
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(c.getId());
        dto.setNombre(c.getNombre());
        dto.setDescripcion(c.getDescripcion());
        dto.setImagenUrl(c.getImagenUrl());
        dto.setActivo(c.getActivo());
        dto.setOrden(c.getOrden());
        return dto;
    }

    @PutMapping
    public void update(@RequestBody CategoriaDTO dto) {
        Categoria c = new Categoria();
        c.setId(dto.getId());
        c.setNombre(dto.getNombre());
        c.setDescripcion(dto.getDescripcion());
        c.setImagenUrl(dto.getImagenUrl());
        c.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        c.setOrden(dto.getOrden() != null ? dto.getOrden() : 0);
        cS.update(c);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID id) {
        cS.delete(id);
    }
}
