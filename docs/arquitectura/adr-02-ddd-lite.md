[Inicio](/) > [Arquitectura](/arquitectura/README.md) > [ADRs](/arquitectura/README.md#4-principios-de-diseno-adoptados) > ADR-02

# ADR-02: Organización de código por dominio de negocio (DDD Lite)

* **ID:** ADR-02
* **Título:** Arquitectura Modular por Dominio (DDD Lite)
* **Estado:** Aprobado
* **Fecha:** 2026-06-01
* **Autor:** Equipo de Desarrollo Backend

---

## Contexto

El sistema FoodGest cuenta con un modelo de base de datos robusto de 23 tablas, que abarca subastas, logística en tiempo real, mensajería, pasarelas de pago y perfiles especializados de usuarios. 

El enfoque tradicional de estructurar proyectos en Java consiste en agrupar las clases por su rol técnico/tecnológico (`controllers/`, `services/`, `repositories/`, `entities/` globales). Sin embargo, a medida que el proyecto crece, este enfoque presenta serios inconvenientes:
1. **Dificultad de navegación:** Encontrar todos los archivos relacionados con una sola funcionalidad (como "Subastas") obliga al programador a saltar entre 5 o 6 carpetas raíz muy distantes.
2. **Conflictos de Merge continuos:** Al trabajar en paralelo en el mismo repositorio, múltiples desarrolladores modifican las mismas carpetas raíz y clases transversales en simultáneo, generando costosos bloqueos de git.
3. **Falta de cohesión:** La lógica de negocio termina diluyéndose y acoplándose innecesariamente.

---

## Decisión

Se adopta una arquitectura modular por dominio de negocio, inspirada en principios de diseño táctico de **Domain-Driven Design (DDD Lite)**. 

El package raíz `com.foodgest` se subdivide en paquetes autocontenidos por contexto delimitado. Cada paquete representa un dominio y contiene sus propias subcarpetas de:
* `controllers`: Endpoints HTTP expuestos.
* `entities`: Modelos de persistencia JPA.
* `repositories`: Interfaces que extienden `JpaRepository`.
* `dtos`: Objetos para transferencia de datos en requests y responses.
* `servicesinterfaces` e `servicesimplements`: Definiciones e implementaciones de lógica transaccional.

Los 12 dominios iniciales del sistema son:
1. `auth` (autenticación y firma JWT)
2. `users` (billetera y usuarios genéricos)
3. `perfiles` (roles específicos: agricultores, compradores, transportistas)
4. `catalogo` (categorías y marcas de productos agrícolas de referencia)
5. `marketplace` (ofertas de lotes, precios dinámicos y negociaciones de trato directo)
6. `pedidos` (órdenes, detalles de lotes y transacciones)
7. `logistica` (ubicaciones de GPS y tracking en camino)
8. `comunicaciones` (mensajería interna y notificaciones push)
9. `reputacion` (puntuación de estrellas y reseñas)
10. `subastas` (ventas por subasta del agricultor y pujas de compradores)
11. `config` (configuraciones globales de Spring y librerías)
12. `shared` (excepciones comunes, respuestas API estandarizadas y utilidades de soporte)

---

## Consecuencias

### Positivas (+)
* **Alta Cohesión y Bajo Acoplamiento:** Toda la lógica referida a un módulo de negocio vive en un mismo lugar físico del repositorio.
* **Reducción de Conflictos de Git:** Múltiples programadores pueden trabajar de forma autónoma en paralelo; por ejemplo, un programador puede reconstruir el flujo de `subastas` sin tocar ningún archivo del flujo de `logistica`.
* **Escalabilidad Orgánica:** Si en el futuro se decide migrar a una arquitectura de microservicios, cualquier dominio se puede extraer a su propio servicio independiente de forma inmediata y limpia.

### Negativas (-)
* **Duplicación Menor de Código:** Puede haber pequeñas redundancias en nombres de DTOs, las cuales se toleran en favor del aislamiento del dominio.
* **Importaciones Cruzadas:** Requiere disciplina para evitar que un dominio dependa directamente de clases internas de otro sin pasar por las interfaces de servicios recomendadas.

---

## Alternativas Consideradas

* **Arquitectura tradicional por capas:** Descartada por la alta probabilidad de colisiones de código y por la dificultad de separar responsabilidades a gran escala.
* **Arquitectura en microservicios puros desde el Día 1:** Descartada inmediatamente por la sobrecarga operacional (múltiples pipelines de despliegue, infraestructura costosa de red y latencia innecesaria) que es inviable en la etapa inicial del proyecto.

---

## Ver también

- [Catálogo de Dominios del Proyecto](/arquitectura/dominios.md)
- [Convenciones de Nomenclatura de Clases](/guias-desarrollo/convenciones-codigo.md)
