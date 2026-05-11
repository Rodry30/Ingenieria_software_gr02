package com.foodgest.catalogo.dtos;

import com.foodgest.catalogo.entities.Producto;
import java.math.BigDecimal;
import java.util.UUID;

public class ProductoResponseDto {
    private UUID id;
    private UUID categoriaId;
    private String categoriaNombre;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private String unidadMedidaDefault;
    private String imagenUrl;
    private Boolean activo;

    public static ProductoResponseDto from(Producto p) {
        ProductoResponseDto dto = new ProductoResponseDto();
        dto.id = p.getId();
        if (p.getCategoria() != null) {
            dto.categoriaId = p.getCategoria().getId();
            dto.categoriaNombre = p.getCategoria().getNombre();
        }
        dto.nombre = p.getNombre();
        dto.descripcion = p.getDescripcion();
        dto.precio = p.getPrecio();
        dto.stock = p.getStock();
        dto.unidadMedidaDefault = p.getUnidadMedidaDefault();
        dto.imagenUrl = p.getImagenUrl();
        dto.activo = p.getActivo();
        return dto;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getCategoriaId() { return categoriaId; }
    public void setCategoriaId(UUID categoriaId) { this.categoriaId = categoriaId; }
    public String getCategoriaNombre() { return categoriaNombre; }
    public void setCategoriaNombre(String categoriaNombre) { this.categoriaNombre = categoriaNombre; }
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
