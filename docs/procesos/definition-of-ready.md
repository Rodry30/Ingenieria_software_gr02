[Inicio](/) > [Procesos](/procesos/definition-of-done.md) > Definition of Ready

# Definition of Ready (DoR) de FoodGest

La **Definición de Listo (Definition of Ready)** es el estándar de calidad aplicado a las Historias de Usuario (HU) en el Backlog de Trello antes de ser seleccionadas para ingresar en un Sprint activo. 

Su propósito es evitar que el equipo de desarrollo inicie tareas ambiguas, mal especificadas o que carezcan de los insumos mínimos necesarios para su codificación, reduciendo drásticamente la fricción y las estimaciones erróneas.

---

## 1. Criterios Mandatorios del DoR

Para que una Historia de Usuario sea declarada **"Ready" (Lista)** en la planeación del Sprint, debe cumplir estrictamente con los siguientes requisitos:

1.  **Título y Descripción Claros:** Redactados bajo el formato ágil estándar:
    *   *Como* [Rol de negocio: Agricultor, Comprador, Transportista, Admin]
    *   *Quiero* [Acción específica a realizar en la plataforma]
    *   *Para* [Beneficio o valor comercial directo que aporta la funcionalidad]
2.  **Criterios de Aceptación Técnicos:** Definidos en formato estructurado Gherkin (*Dado que... Cuando... Entonces...*) para guiar las pruebas de comportamiento y evitar interpretaciones libres del alcance.
3.  **Dependencias Resueltas:** Se ha verificado que la HU no está bloqueada por otras tareas pendientes del backlog. Si requiere APIs o modelos de otros dominios, estos deben estar previamente terminados.
4.  **Estimación en Puntos de Historia:** La tarea ha sido analizada y estimada en consenso por el equipo de desarrollo (empleando la secuencia de Fibonacci en Planning Poker).
5.  **Diseño / Mockups Disponibles:** Si la HU involucra interfaces de usuario en el frontend móvil/web (Angular), debe incluir el enlace al diseño aprobado en Figma o bosquejos claros de las pantallas.
6.  **Esquema de BD Definido:** Si la HU requiere crear nuevas tablas, se adjunta un borrador del modelo físico relacional y los tipos de datos espaciales requeridos.

---

## 2. Ejemplos de Historias de Usuario

### ❌ HISTORIA DE USUARIO NO LISTA (Rechazada en Planning)

*   **Título:** "Arreglar mapa"
*   **Descripción:** *"El mapa no funciona bien para buscar papas. Necesitamos que busque mejor y muestre los marcadores rápido."*
*   **Por qué NO cumple el DoR:**
    *   No define quién es el usuario que realiza la acción (*Como...*).
    *   No especifica el valor que aporta el cambio (*Para...*).
    *   "Funcionar bien" y "buscar mejor" son términos subjetivos sin criterios de aceptación numéricos o técnicos.
    *   No define qué radio de búsqueda se requiere ni qué información debe pintar cada marcador.
    *   No cuenta con estimación en puntos de historia.

---

###  HISTORIA DE USUARIO LISTA (Aprobada para entrar al Sprint)

*   **Título:** `HU-15 - Búsqueda de Ofertas Agrícolas por Radio en el Mapa`
*   **Descripción:**
    *   *Como* Comprador Mayorista
    *   *Quiero* buscar ofertas agrícolas visualizando marcadores en un mapa dentro de un radio en kilómetros a la redonda de mi ubicación
    *   *Para* localizar lotes de cosechas cercanos y optimizar los costos de flete terrestre.
*   **Criterios de Aceptación:**
    *   **Escenario 1:** Búsqueda dentro del radio.
        *   *Dado que* un comprador se encuentra en las coordenadas `-12.04` (latitud) y `-77.03` (longitud).
        *   *Cuando* ingresa un radio de búsqueda de `50` kilómetros en el filtro.
        *   *Entonces* el sistema debe retornar únicamente las ofertas de la tabla `ofertas` que se ubiquen a menos de 50 km utilizando la consulta PostGIS `ST_DWithin`.
    *   **Escenario 2:** Ordenamiento de resultados.
        *   *Entonces* los resultados en la lista del mapa deben presentarse ordenados de forma ascendente según la distancia calculada por la función `ST_Distance` de PostGIS.
*   **Diseño Asociado:** Enlace al mockup de Figma `https://figma.com/file/foodgest-map-view`.
*   **Estimación:** 5 Puntos de Historia.
*   **Estatus del DoR:** **APROBADO** (Listo para desarrollo).

---

## Ver también

- [Definition of Done (DoD) de Calidad](/procesos/definition-of-done.md)
- [Estrategia de Ramas Git Flow](/guias-desarrollo/git-flow.md)
- [Mapeo del Modelo de Dominios](/arquitectura/dominios.md)
