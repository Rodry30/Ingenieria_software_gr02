package com.ProyectoMercado.grupo2.categories.controller;

import com.ProyectoMercado.grupo2.categories.dto.CategoryRequest;
import com.ProyectoMercado.grupo2.categories.dto.CategoryResponse;
import com.ProyectoMercado.grupo2.categories.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Categorias", description = "CRUD de categorias")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @Operation(summary = "Listar categorias")
    public List<CategoryResponse> listar() {
        return categoryService.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener categoria por ID")
    public CategoryResponse obtenerPorId(@PathVariable UUID id) {
        return categoryService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear categoria")
    public CategoryResponse crear(@Valid @RequestBody CategoryRequest request) {
        return categoryService.crear(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar categoria")
    public CategoryResponse editar(@PathVariable UUID id, @Valid @RequestBody CategoryRequest request) {
        return categoryService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar categoria")
    public void eliminar(@PathVariable UUID id) {
        categoryService.eliminar(id);
    }
}
