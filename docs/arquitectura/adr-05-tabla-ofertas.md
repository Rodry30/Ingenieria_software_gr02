[Inicio](/) > [Arquitectura](/arquitectura/README.md) > [ADRs](/arquitectura/README.md#4-principios-de-diseno-adoptados) > ADR-05

# ADR-05: Entidad independiente OFERTAS como núcleo dinámico del marketplace

* **ID:** ADR-05
* **Título:** Tabla OFERTAS como Entidad Central
* **Estado:** Aprobado
* **Fecha:** 2026-06-01
* **Autor:** Equipo de Diseño de Modelo de Datos

---

## Contexto

En el diseño de plataformas de comercio electrónico tradicionales (como una tienda retail estándar), los productos expuestos en catálogo (`PRODUCTOS`) contienen directamente propiedades dinámicas y volátiles como el precio unitario y el stock actual.

Sin embargo, FoodGest es un **Marketplace bilateral B2B/B2C** de sector agrícola. En este modelo:
1. Cientos de agricultores diferentes pueden cultivar y vender el mismo producto de referencia (ej. "Papa Única" o "Limón Sutil").
2. Cada agricultor ofrece su lote cosechado bajo condiciones radicalmente distintas: diferente precio por kilogramo, diferentes cantidades disponibles (toneladas), diferentes ubicaciones geográficas de sus parcelas, fechas de cosecha dispares y distintas certificaciones fitosanitarias particulares.
3. El catálogo de productos debe actuar únicamente como un diccionario taxonómico de referencia centralizado para evitar que cada agricultor registre el mismo producto con faltas de ortografía (ej. "papa unica", "Papa Unica", "papas unicas") lo que arruinaría las búsquedas y la categorización.

---

## Decisión

Se establece que la tabla **`PRODUCTOS`** funcionará estrictamente como un catálogo maestro de lectura estática (administrado únicamente por administradores de FoodGest), mientras que las publicaciones dinámicas de venta de los productores agrícolas se registrarán en una entidad independiente central llamada **`OFERTAS`**.

La tabla `ofertas` cuenta con la siguiente arquitectura de relaciones:
* **`producto_id`** (FK a `productos`): Define el producto de referencia del catálogo.
* **`agricultor_id`** (FK a `agricultores`): Enlaza al productor dueño de la cosecha.
* **Datos Volátiles:**
  * `precio_unitario`: El precio base ofertado por kilo/saco.
  * `stock_disponible`: Cantidad física real actual disponible del lote.
  * `ubicacion_oferta` (`GEOGRAPHY`): Coordenadas geográficas exactas desde donde se despachará el producto.
  * `estado_oferta`: Estados del ciclo de vida (`activa`, `pausada`, `agotada`).
  * `precios_escalonados`: Descuentos por volumen configurados para compras mayoristas.

---

## Consecuencias

### Positivas (+)
* **Catálogo Homogéneo:** Las búsquedas de productos son consistentes y limpias. Los compradores pueden buscar "Papa Única" y ver un listado comparativo de 50 ofertas de diferentes agricultores ordenadas por distancia o precio.
* **Soporte Mayorista:** Facilita la creación de precios escalonados (ej. "1.50 PEN/kg si compras más de 1 tonelada, 1.80 PEN/kg si compras menos") vinculados directamente a la oferta de un lote específico sin alterar el catálogo maestro.
* **Desempeño en Búsquedas:** Permite optimizar búsquedas espaciales directamente sobre la tabla `ofertas` mediante índices GiST geográficos en su columna `ubicacion_oferta`.

### Negativas (-)
* **Doble Relación:** Para pintar una oferta en pantalla, el frontend obligatoriamente debe realizar un Join entre `ofertas` y `productos` (resuelto eficientemente en la base de datos o mediante DTOs combinados).
* **Gestión de Catálogo Requerida:** Requiere una interfaz de administración para que los curadores de la plataforma agreguen nuevos productos agrícolas de referencia al catálogo a solicitud de los agricultores.

---

## Alternativas Consideradas

* **Modelo Retail Estándar (Precios en Productos):** Descartado de inmediato, ya que forzaría a cada agricultor a registrar su propia versión de "Papa" en la tabla de productos, contaminando el catálogo y haciendo imposible la consolidación de estadísticas de precios de mercado.

---

## Ver también

- [Estructura del Dominio Catalogo](/arquitectura/dominios.md#4-catalogo)
- [Estructura del Dominio Marketplace](/arquitectura/dominios.md#5-marketplace)
- [Algoritmo de Cálculo de Fletes y Geolocalización](/apis/integracion-maps.md)
