[Inicio](/) > [Guías de Desarrollo](/guias-desarrollo/convenciones-codigo.md) > Convenciones de Código

# Convenciones de Código Java

Para garantizar que el código de FoodGest sea uniforme, legible y fácil de mantener por cualquier desarrollador del equipo, se establecen las siguientes directrices y estándares obligatorios.

---

## 1. Nomenclatura Estándar

### Clases Java
*   **PascalCase:** Los nombres de clases e interfaces deben usar CamelCase con la primera letra en mayúscula.
*   **Sufijos de Dominio:**
    *   **Entidades JPA:** Nombre simple en singular (ej. `Oferta.java`, `Pedido.java`, `Categoria.java`). *Excepción:* Para la entidad de usuario base se usa `UserEntities.java` para evitar colisiones con palabras clave del motor SQL.
    *   **Repositorios:** `NombreRepository.java` (ej. `OfertaRepository.java`).
    *   **Interfaces de Servicio:** `INombreService.java` (ej. `IOfertaService.java`).
    *   **Implementaciones de Servicio:** `NombreServiceImpl.java` (ej. `OfertaServiceImpl.java`).
    *   **Controladores REST:** `NombreController.java` (ej. `OfertaController.java`).
    *   **DTOs:** `NombreAccionDto.java` (ej. `OfertaCreateDto.java`, `OfertaResponseDto.java`).

### Métodos y Variables
*   **camelCase:** Letra inicial en minúscula, verbos claros para métodos y nombres descriptivos para variables (ej. `obtenerOfertasPorRadio()`, `stockDisponible`).
*   **Español:** Todo el dominio de negocio, variables y tablas se escriben en español (ej. `precioUnitario`, `nombreFinca`), a excepción de palabras reservadas del framework o términos universales de la industria (ej. `id`, `uuid`, `created_at`, `login`, `wallet`, `token`).

### Constantes
*   **UPPER_SNAKE_CASE:** Letras mayúsculas separadas por guiones bajos (ej. `IGV_PORCENTAJE = 0.18`, `MONEDA_DEFAULT = "PEN"`).

---

## 2. Estructura Interna de un ServiceImpl

El orden de los elementos dentro de las clases de servicio implementadas (`NombreServiceImpl.java`) debe ser consistente:

1.  **Anotación `@Service`** de Spring a nivel de clase.
2.  **Inyecciones de Dependencia (`@Autowired`):** Primero repositorios del dominio, luego interfaces de servicios externos, y finalmente utilidades transversales (ej. `ObjectMapper`).
3.  **Métodos Públicos Sobreescritos (`@Override`):** En el mismo orden en que están definidos en la interfaz (`I*.java`).
4.  **Métodos Privados Auxiliares:** Mapeadores manuales DTO-Entidad o algoritmos de validación interna.

---

## 3. Reglas para DTOs

Los DTOs (Data Transfer Objects) son objetos planos diseñados para recibir peticiones HTTP o responder datos.
*   **Validación Activa:** Todos los campos de entrada obligatorios en DTOs de creación/actualización deben estar anotados con constraints de `jakarta.validation.constraints` (ej. `@NotBlank`, `@NotNull`, `@Size`, `@Positive`).
*   **Seguridad:** Un DTO de respuesta jamás debe contener datos sensibles como contraseñas en texto plano, contraseñas encriptadas, números completos de tarjetas de crédito o códigos PIN.
*   **Boilerplate:** Se permite el uso de getters y setters explícitos o records de Java para simplificar el código.

---

## 4. Reglas para Entidades JPA

*   **Anotación `@Entity` y `@Table`:** Es obligatorio especificar el nombre físico de la tabla en la base de datos (ej. `@Table(name = "ofertas")`).
*   **Estrategia de ID:** Todas las tablas de FoodGest deben emplear identificadores únicos universales (UUID) generados por el backend o la base de datos para facilitar integraciones offline e impedir enumeración de recursos:
    ```java
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    ```
*   **Auditoría Automática:** Toda entidad debe contar con los campos `createdAt` y `updatedAt` gestionados automáticamente por JPA:
    ```java
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    ```

---

## 5. Ejemplos Concretos de Código

### ❌ CÓDIGO INCORRECTO (Mal escrito)
```java
// Sin encapsulación por dominio, sin validación en DTO, inyección de campo obsoleta, JPA expuesto directamente.
@RestController
@RequestMapping("/api/ofertas")
public class OfertaController {

    @Autowired
    public OfertaRepository repo; // Mala práctica: Inyección por campo público directa del repositorio en el controlador.

    @PostMapping
    public Oferta crear(@RequestBody Oferta o) { // Mala práctica: Recibe entidad de base de datos directamente del JSON.
        if (o.precio == null) {
            throw new RuntimeException("Precio invalido"); // Mala práctica: Excepción genérica sin código HTTP claro.
        }
        return repo.save(o); 
    }
}
```

###  CÓDIGO CORRECTO (Estándar FoodGest)
```java
package com.foodgest.marketplace.ofertas.controllers;

import com.foodgest.marketplace.ofertas.dtos.OfertaCreateDto;
import com.foodgest.marketplace.ofertas.dtos.OfertaResponseDto;
import com.foodgest.marketplace.ofertas.servicesinterfaces.IOfertaService;
import com.foodgest.shared.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/marketplace/ofertas")
public class OfertaController {

    private final IOfertaService ofertaService;

    // Constructor Injection (Recomendado por Spring)
    public OfertaController(IOfertaService ofertaService) {
        this.ofertaService = ofertaService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OfertaResponseDto>> crearOferta(
            @Valid @RequestBody OfertaCreateDto dto) {
        
        OfertaResponseDto response = ofertaService.crear(dto);
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        201, 
                        "Oferta agrícola publicada exitosamente en el mapa", 
                        response));
    }
}
```

---

## Ver también

- [ADR-02: Modularidad por Dominio](/arquitectura/adr-02-ddd-lite.md)
- [Guía del Flujo de Ramas Git](/guias-desarrollo/git-flow.md)
- [Definición de Done (DoD) de Código](/procesos/definition-of-done.md)
