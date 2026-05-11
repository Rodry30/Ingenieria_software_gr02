package com.foodgest.catalogo.dtos;

import com.foodgest.catalogo.entities.Categoria;

public class CategoriaCreateDto {
    private String nombre;
    private String descripcion;
    private String imagenUrl;
    private Boolean activo;
    private Integer orden;

    public Categoria toEntity() {
        Categoria c = new Categoria();
        c.setNombre(this.nombre);
        c.setDescripcion(this.descripcion);
        c.setImagenUrl(this.imagenUrl);
        c.setActivo(this.activo != null ? this.activo : true);
        c.setOrden(this.orden != null ? this.orden : 0);
        return c;
    }

    public void applyTo(Categoria c) {
        c.setNombre(this.nombre);
        c.setDescripcion(this.descripcion);
        c.setImagenUrl(this.imagenUrl);
        if (this.activo != null) c.setActivo(this.activo);
        if (this.orden != null) c.setOrden(this.orden);
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }
}
