package com.foodgest.catalogo.dtos;

import com.foodgest.catalogo.entities.FotoProducto;
import com.foodgest.catalogo.entities.Producto;
import java.util.UUID;

public class FotoProductoDto {
    private UUID id;
    private UUID productoId;
    private String url;
    private String descripcion;
    private Boolean esPrincipal;

    public static FotoProductoDto from(FotoProducto f) {
        FotoProductoDto dto = new FotoProductoDto();
        dto.id = f.getId();
        if (f.getProducto() != null) dto.productoId = f.getProducto().getId();
        dto.url = f.getUrl();
        dto.descripcion = f.getDescripcion();
        dto.esPrincipal = f.getEsPrincipal();
        return dto;
    }

    public FotoProducto toEntity(Producto producto) {
        FotoProducto f = new FotoProducto();
        f.setProducto(producto);
        f.setUrl(this.url);
        f.setDescripcion(this.descripcion);
        f.setEsPrincipal(this.esPrincipal != null ? this.esPrincipal : false);
        return f;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getProductoId() { return productoId; }
    public void setProductoId(UUID productoId) { this.productoId = productoId; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Boolean getEsPrincipal() { return esPrincipal; }
    public void setEsPrincipal(Boolean esPrincipal) { this.esPrincipal = esPrincipal; }
}
