[Inicio](/) > [APIs](/apis/endpoints-implementados.md) > Integración de Mapas y Geolocalización

# Integración con Mapas y Geolocalización (PostGIS)

La geolocalización es una de las ventajas competitivas de **FoodGest**. Permite a los compradores encontrar lotes agrícolas cercanos para abaratar costos de transporte, y a los transportistas trazar rutas óptimas para el despacho de mercancía en el Perú.

---

## 1. Uso de Google Maps en el Frontend

La aplicación web y móvil (construida en Angular) consume la **Google Maps JavaScript API** para pintar el mapa interactivo.
*   **Marcadores Dinámicos:** Muestra la ubicación de las parcelas agrarias activas con ofertas disponibles.
*   **Geocodificación Inversa:** Al registrar una finca, el agricultor puede presionar un punto en el mapa y la API de Google traduce las coordenadas a una dirección postal legible de referencia (ej. "Valle del Mantaro, Concepción, Junín").
*   **Trazado de Rutas:** Emplea Google Directions API para pintar de forma gráfica la ruta terrestre sugerida desde el punto de cosecha hasta el almacén de destino del comprador.

---

## 2. Endpoint del Mapa y Query Geoespacial

Para poblar el mapa interactivo del comprador de forma ultra-eficiente sin sobrecargar el frontend, el backend expone el endpoint:
`GET /api/marketplace/ofertas?latitud=-12.04&longitud=-77.03&radioKm=50`

Este endpoint ejecuta una única consulta optimizada a la base de datos PostgreSQL utilizando funciones nativas de **PostGIS**.

### Consulta SQL Nativa Ejecutada en el Repositorio:
```sql
SELECT o.id, o.precio_unitario, o.stock_disponible, p.nombre, a.nombre_finca,
       ST_Y(o.ubicacion_oferta::geometry) as latitud,
       ST_X(o.ubicacion_oferta::geometry) as longitud,
       ST_Distance(o.ubicacion_oferta, ST_MakePoint(:longitud, :latitud)::geography) as distancia_metros
FROM ofertas o
JOIN productos p ON o.producto_id = p.id
JOIN agricultores a ON o.agricultor_id = a.usuario_id
WHERE o.estado_oferta = 'activa'
  AND ST_DWithin(o.ubicacion_oferta, ST_MakePoint(:longitud, :latitud)::geography, :radioKm * 1000)
ORDER BY distancia_metros ASC;
```

*   **`ST_MakePoint(longitud, latitud)`:** Construye un objeto geométrico a partir de las coordenadas del comprador.
*   **`ST_DWithin`:** Valida si el punto de la oferta se encuentra dentro del radio especificado (el tercer parámetro se multiplica por 1000 ya que `GEOGRAPHY` mide distancias en metros).
*   **`ST_Distance`:** Retorna la distancia lineal real entre ambos puntos sobre el elipsoide terrestre.

---

## 3. Optimización con Índices Espaciales

Para evitar que PostgreSQL realice un escaneo completo de la tabla `ofertas` (*Table Scan*) al hacer búsquedas espaciales, se define obligatoriamente un índice **GiST** sobre la columna geoespacial:

```sql
CREATE INDEX idx_ofertas_ubicacion_spatial ON ofertas USING gist(ubicacion_oferta);
```
Este índice cuadricula espacialmente el globo terrestre, permitiendo buscar y descartar millones de ofertas distantes en microsegundos.

---

## 4. Algoritmo de Cálculo de Flete Estimado

El costo del flete de transporte terrestre es calculado por la plataforma de forma automática antes de que el comprador cierre el pedido, sirviendo de base para que los transportistas postulen al despacho:

### Fórmula del Flete:
$$\text{Flete Total (PEN)} = \text{Tarifa Base} + (\text{Distancia Terrestre en Km} \times \text{Factor de Dificultad de Ruta} \times \text{Tarifa por Kilómetro}) + (\text{Peso en Toneladas} \times \text{Factor de Carga})$$

### Variables Involucradas:
1.  **Tarifa Base:** Costo fijo de partida del transportista (ej. S/. 50.00).
2.  **Distancia Terrestre:** Calculada mediante consultas de ruta real en carretera, no lineal (aproximadamente un 30% superior a la distancia aérea de PostGIS).
3.  **Factor de Dificultad de Ruta:** Coeficiente multiplicador según geografía peruana (Costa: `1.0`, Selva: `1.3`, Sierra/Paso de Altura: `1.5` debido al desgaste de neumáticos y consumo de combustible en altura).
4.  **Factor de Carga:** Adicional por tonelada transportada que incrementa el consumo de combustible del camión.

---

## Ver también

- [ADR-01: Selección de PostgreSQL + PostGIS](/arquitectura/adr-01-postgresql-postgis.md)
- [Esquema del Dominio Logística](/arquitectura/dominios.md#7-logistica)
- [Glosario de Términos Geográficos](/glosario.md#p)
