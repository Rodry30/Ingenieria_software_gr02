package com.foodgest.catalogo.entities;


import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    //@Column(name = "categoria_id")
    //private UUID categoriaID;

    @Column(name = "nombre",length = 200 )
    private String nombre;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Column(name = "unidad_medida_default")
    private String unidadMedidaDefault;

    @Column(name = "imagen_url")
    private String imagenUrl;
}

