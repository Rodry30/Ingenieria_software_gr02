[Inicio](/) > [Arquitectura](/arquitectura/README.md) > [ADRs](/arquitectura/README.md#4-principios-de-diseno-adoptados) > ADR-01

# ADR-01: Uso de PostgreSQL con extensión PostGIS para datos geoespaciales

* **ID:** ADR-01
* **Título:** PostgreSQL + PostGIS para base de datos y geolocalización
* **Estado:** Aprobado
* **Fecha:** 2026-06-01
* **Autor:** Equipo de Arquitectura FoodGest

---

## Contexto

El core de negocio de FoodGest gira en torno a la localización interactiva de parcelas de agricultores, el cálculo dinámico de fletes de transporte terrestre en el Perú y la visualización espacial de ofertas agrícolas en mapas interactivos para los compradores. 

Para habilitar estas funcionalidades es imperativo contar con:
1. Almacenamiento eficiente de puntos geográficos (latitud y longitud).
2. Capacidad de realizar consultas de vecindad (ej. "Encontrar papas ofrecidas a menos de 50 kilómetros a la redonda de mi ubicación en Arequipa").
3. Integridad transaccional estricta (ACID) para transacciones financieras (billeteras de depósito en escrow).

---

## Decisión

Se decide utilizar **PostgreSQL 15** como base de datos relacional del proyecto, activando la extensión **PostGIS** en la instancia.

Las entidades clave que utilizarán datos espaciales se definirán con columnas del tipo `GEOGRAPHY(POINT, 4326)`, incluyendo:
* `users.ubicacion_geo` (Ubicación física declarada de cada usuario).
* `agricultores.ubicacion_parcela` (Ubicación exacta de la chacra/parcela).
* `ofertas.ubicacion_oferta` (Ubicación del lote de cosecha ofrecido).
* `tracking_pedido.ubicacion` (Coordenadas de despacho del transportista en tránsito).

Se descarta el uso del tipo `GEOMETRY` en favor de `GEOGRAPHY` porque este último calcula distancias métricas de forma nativa sobre el elipsoide terrestre (WGS 84 / EPSG:4326), evitando conversiones matemáticas complejas en el código backend.

---

## Consecuencias

### Positivas (+)
* **Consultas Espaciales Nativas:** Permite usar funciones de PostGIS como `ST_DWithin` para búsquedas en radio y `ST_Distance` para cálculo instantáneo de rutas aéreas entre chacras y centros de consumo.
* **Excelente Indexación:** Habilita el uso de índices espaciales **GiST** (*Generalized Search Tree*), que reducen drásticamente el costo de búsqueda geográfica en comparación con consultas SQL tradicionales que usan fórmulas de Haversine.
* **Integridad Relacional Plena:** Al ser PostgreSQL, el sistema se beneficia de llaves foráneas, restricciones de unicidad de emails, y aislamiento ACID completo para transacciones financieras.

### Negativas (-)
* **Complejidad en Desarrollo Local:** Obliga a los desarrolladores a instalar localmente PostgreSQL con la biblioteca de PostGIS (o usar Docker), incrementando la curva de configuración inicial del entorno.
* **Curva de Aprendizaje:** Requiere que el equipo técnico domine el uso de anotaciones y dialectos espaciales en Hibernate (`hibernate-spatial`) y configure mapeadores JPA específicos.

---

## Alternativas Consideradas

* **MySQL / MariaDB:** Aunque poseen ciertas capacidades espaciales, sus implementaciones son inconsistentes, tienen soporte limitado de cálculo real sobre elipsoides y carecen de la robustez de las funciones analíticas avanzadas que provee PostGIS.
* **MongoDB:** Excelente para consultas geoespaciales no relacionales, pero fue descartado porque el modelo transaccional de FoodGest (cuentas, transferencias entre billeteras y estados de pedidos) requiere una consistencia ACID nativa extremadamente estricta y relaciones complejas que MongoDB no maneja de forma eficiente.

---

## Ver también

- [ADR-06: Separación de Perfiles por Tabla Especializada](/arquitectura/adr-06-separacion-perfiles.md)
- [Mapeo de Variables de Entorno de BD](/configuracion/variables-entorno.md)
- [Guía de Integración con Maps](/apis/integracion-maps.md)
