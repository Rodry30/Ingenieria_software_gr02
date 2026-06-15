[Inicio](/) > [Arquitectura](/arquitectura/README.md) > Stack Tecnológico

# Stack Tecnológico de FoodGest

La selección tecnológica de FoodGest responde a criterios de robustez empresarial, soporte a datos georreferenciados en tiempo real, facilidad de integración móvil y mantenibilidad en equipos ágiles.

---

## 1. Tecnologías de Backend

| Tecnología | Versión | Propósito en el Proyecto | Justificación Técnica |
| :--- | :--- | :--- | :--- |
| **Java** | 17 (LTS) | Lenguaje de programación principal | Ofrece estabilidad, tipado estático fuerte, mejoras de performance con recolectores modernos y características avanzadas como *Records* y *Pattern Matching* para DTOs. |
| **Spring Boot** | 3.3.5 | Framework de desarrollo de la API | Simplifica la configuración de dependencias (*Starters*), inyección de dependencias implícita, y provee un servidor integrado Tomcat optimizado para API REST. |
| **Spring Security** | 6.x | Seguridad y control de accesos | Administra el cifrado de contraseñas de usuarios con `BCrypt` y permite interceptar peticiones no autorizadas a nivel de filtros REST. |
| **jjwt (JSON Web Token)** | 0.11.5 | Generación y firmado de tokens JWT | Librería compacta para construir tokens seguros y parsear claims sin añadir sobrecargas a la API stateless. |
| **Spring Data JPA / Hibernate** | 3.3.x / 6.x | ORM y persistencia relacional | Permite mapear tablas de base de datos a objetos Java, automatizando consultas rutinarias y reduciendo la escritura manual de consultas SQL complejas. |
| **PostgreSQL** | 15.x | Base de datos relacional principal | Altamente confiable, cumple estrictamente con el principio ACID y ofrece un rendimiento extraordinario en transacciones simultáneas con UUIDs como llaves primarias. |
| **PostGIS** | 3.x (Extensión) | Procesamiento y consultas geoespaciales | Extiende PostgreSQL para soportar tipos espaciales `GEOGRAPHY` y realizar búsquedas de distancia y vecindad a través de índices `GIST` eficientes. |
| **Maven** | 3.8+ | Gestor de dependencias y automatización de compilación | Estandariza la estructura del proyecto y define el ciclo de vida de compilación, empaquetado y ejecución de pruebas automatizadas. |

---

## 2. Tecnologías de Frontend y Móvil

| Tecnología | Versión | Propósito en el Proyecto | Justificación Técnica |
| :--- | :--- | :--- | :--- |
| **Angular** | 17+ | Interfaz web de administración y clientes | Framework SPA basado en TypeScript que promueve modularidad por componentes, renderizado eficiente y excelente manejo de estados locales en formularios complejos. |

---

## 3. Infraestructura y Gestión de Proyectos

| Tecnología | Versión | Propósito en el Proyecto | Justificación Técnica |
| :--- | :--- | :--- | :--- |
| **GitHub** | N/A | Repositorio de código y control de versiones | Facilita la colaboración segura mediante ramas (*Git Flow*), revisiones de código obligatorias mediante Pull Requests e integración directa con CI/CD. |
| **GitHub Actions** | N/A | Pipeline de Integración y Despliegue Continuo (CI/CD) | Automatiza la ejecución de compilación con Maven, análisis de código con SonarQube y despliegue del empaquetado JAR tras cada merge exitoso a `develop` o `main`. |
| **Docsify** | 4.x | Generación dinámica de la Wiki técnica | Convierte archivos Markdown en un sitio web dinámico de documentación en tiempo real sobre GitHub Pages, eliminando la necesidad de compilar archivos estáticos manualmente. |
| **Trello** | N/A | Tablero Scrum de tareas | Mantiene la visibilidad del Backlog de Producto, historias de usuario (HU), tareas técnicas y el avance del Sprint en tiempo real. |

---

## Ver también

- [ADR-01: Selección de PostgreSQL + PostGIS](/arquitectura/adr-01-postgresql-postgis.md)
- [ADR-03: Autenticación JWT Stateless](/arquitectura/adr-03-jwt-stateless.md)
- [Configuración de Variables de Entorno](/configuracion/variables-entorno.md)
