package com.foodgest.catalogo.dtos;

import com.foodgest.catalogo.entities.Categoria;
import com.foodgest.catalogo.entities.Producto;
import java.math.BigDecimal;
import java.util.UUID;

public class ProductoCreateDto {
    private UUID categoriaId;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private String unidadMedidaDefault;
    private String imagenUrl;
    private Boolean activo;

    public Producto toEntity(Categoria categoria) {
        Producto p = new Producto();
        p.setCategoria(categoria);
        p.setNombre(this.nombre);
        p.setDescripcion(this.descripcion);
        p.setPrecio(this.precio);
        p.setStock(this.stock != null ? this.stock : 0);
        p.setUnidadMedidaDefault(this.unidadMedidaDefault);
        p.setImagenUrl(this.imagenUrl);
        p.setActivo(this.activo != null ? this.activo : true);
        return p;
    }

    public void applyTo(Producto p, Categoria categoria) {
        p.setCategoria(categoria);
        p.setNombre(this.nombre);
        p.setDescripcion(this.descripcion);
        p.setPrecio(this.precio);
        if (this.stock != null) p.setStock(this.stock);
        p.setUnidadMedidaDefault(this.unidadMedidaDefault);
        p.setImagenUrl(this.imagenUrl);
        if (this.activo != null) p.setActivo(this.activo);
    }

    public UUID getCategoriaId() { return categoriaId; }
    public void setCategoriaId(UUID categoriaId) { this.categoriaId = categoriaId; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public String getUnidadMedidaDefault() { return unidadMedidaDefault; }
    public void setUnidadMedidaDefault(String u) { this.unidadMedidaDefault = u; }
    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
