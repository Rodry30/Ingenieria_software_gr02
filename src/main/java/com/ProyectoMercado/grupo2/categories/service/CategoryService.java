package com.ProyectoMercado.grupo2.categories.service;

import com.ProyectoMercado.grupo2.categories.dto.CategoryRequest;
import com.ProyectoMercado.grupo2.categories.dto.CategoryResponse;
import com.ProyectoMercado.grupo2.categories.entity.CategoryEntity;
import com.ProyectoMercado.grupo2.categories.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> listar() {
        return categoryRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public CategoryResponse obtenerPorId(UUID id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Categoria no encontrada"));
        return toResponse(category);
    }

    @Transactional
    public CategoryResponse crear(CategoryRequest request) {
        if (categoryRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new ResponseStatusException(BAD_REQUEST, "La categoria ya existe");
        }

        CategoryEntity category = new CategoryEntity();
        category.setNombre(request.getNombre());
        category.setDescripcion(request.getDescripcion());
        category.setImagenUrl(request.getImagenUrl());
        category.setActivo(request.getActivo());
        category.setOrden(request.getOrden());

        return toResponse(categoryRepository.save(category));
    }

    @Transactional
    public CategoryResponse actualizar(UUID id, CategoryRequest request) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Categoria no encontrada"));

        if (categoryRepository.existsByNombreIgnoreCaseAndIdNot(request.getNombre(), id)) {
            throw new ResponseStatusException(BAD_REQUEST, "La categoria ya existe");
        }

        category.setNombre(request.getNombre());
        category.setDescripcion(request.getDescripcion());
        category.setImagenUrl(request.getImagenUrl());
        category.setActivo(request.getActivo());
        category.setOrden(request.getOrden());

        return toResponse(categoryRepository.save(category));
    }

    @Transactional
    public void eliminar(UUID id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND, "Categoria no encontrada");
        }
        categoryRepository.deleteById(id);
    }

    private CategoryResponse toResponse(CategoryEntity category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setNombre(category.getNombre());
        response.setDescripcion(category.getDescripcion());
        response.setImagenUrl(category.getImagenUrl());
        response.setActivo(category.getActivo());
        response.setOrden(category.getOrden());
        return response;
    }
}
