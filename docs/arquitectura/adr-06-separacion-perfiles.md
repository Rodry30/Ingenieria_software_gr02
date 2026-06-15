[Inicio](/) > [Arquitectura](/arquitectura/README.md) > [ADRs](/arquitectura/README.md#4-principios-de-diseno-adoptados) > ADR-06

# ADR-06: Separación de perfiles especializados mediante tablas vinculadas en relación 1:1

* **ID:** ADR-06
* **Título:** Separación de Perfiles por Tabla Especializada
* **Estado:** Aprobado
* **Fecha:** 2026-06-01
* **Autor:** Equipo de Base de Datos y Arquitectura

---

## Contexto

La plataforma FoodGest gestiona tres tipos principales de usuarios altamente diferenciados en cuanto a sus atributos de negocio:
1. **Agricultores:** Requieren registrar nombre de la finca/parcela, hectáreas cultivables, tipo de cultivo principal, geolocalización exacta de la finca, RUC para facturación, datos bancarios (cuenta corriente y banco) para cobros.
2. **Compradores:** Requieren registrar el tipo de comprador (mayorista, minorista, restaurante), razón social, RUC o DNI, y coordenadas geográficas por defecto para entregas logísticas.
3. **Transportistas:** Requieren registrar detalles del vehículo (placa, marca, capacidad de carga en toneladas), licencia de conducir, SOAT y tarifas estimadas por kilómetro.

Si almacenáramos toda esta información en una única tabla unificada de usuarios (`users`):
* Terminaríamos con una "God Table" de más de 60 columnas.
* Más de la mitad de las columnas para cualquier usuario específico serían estrictamente `NULL` (ej. los campos de vehículo de un transportista serían nulos para un agricultor, y los campos de hectáreas de un agricultor serian nulos para un comprador).
* La integridad de los datos sería sumamente difícil de validar a nivel de base de datos (`NOT NULL` constraints serían imposibles de aplicar en columnas de perfiles).

---

## Decisión

Se decide implementar una estrategia de **tabla base con tablas especializadas vinculadas mediante relaciones de llave primaria compartida 1:1**.

La estructura física de la base de datos se define de la siguiente manera:
1. **Tabla Base `users`:** Almacena los campos compartidos de seguridad, contacto e identidad general de la cuenta:
   * `id` (`UUID`, PK)
   * `nombre`, `email`, `password_hash`, `tipo_usuario` (enum), `estado` (enum), `telefono`, `foto_perfil_url`, `verificado` (boolean).
2. **Tablas de Perfiles Especializados:**
   * **`agricultores`:** `usuario_id` (`UUID`, PK y FK hacia `users.id`), `nombre_finca`, `hectareas`, `tipo_cultivo_principal`, `ubicacion_parcela`, `ruc`, `cuenta_bancaria`, `banco`.
   * **`compradores`:** `usuario_id` (`UUID`, PK y FK hacia `users.id`), `tipo_comprador` (enum), `razon_social`, `ruc`, `direccion_entrega_default`, `latitud_entrega`, `longitud_entrega`.
   * **`transportistas`:** `usuario_id` (`UUID`, PK y FK hacia `users.id`), `placa_vehiculo`, `capacidad_carga`, `tipo_licencia`, `soat_vigente`, `tarifa_base`.

En JPA, estas relaciones se modelan de manera limpia utilizando la anotación `@MapsId` sobre el campo `@OneToOne` de la entidad de perfil, garantizando que el ID del perfil sea exactamente el mismo ID físico del usuario.

---

## Consecuencias

### Positivas (+)
* **Esquema Relacional Limpio:** No hay columnas fantasma con valores nulos innecesarios en la base de datos.
* **Integridad Estricta:** Se pueden aplicar restricciones `NOT NULL` a nivel físico de base de datos en los campos obligatorios del perfil (ej. `nombre_finca` es estrictamente requerido en la tabla `agricultores`).
* **Fácil Extensibilidad:** Si mañana el negocio decide agregar un nuevo rol (ej. "Inversionista Agrícola" o "Inspector de Calidad"), solo se requiere crear una nueva tabla e integrarla con una relación 1:1, sin modificar la tabla central `users` ni alterar las funcionalidades existentes.

### Negativas (-)
* **Joins en Consultas:** Obtener el perfil completo de un usuario para pintarlo en una pantalla requiere un Join físico entre `users` y la tabla especializada correspondiente (mitigado eficientemente mediante índices automáticos de llaves primarias).
* **Gestión de Cascadas:** Eliminar un usuario requiere cuidado para garantizar que los perfiles dependientes se eliminen en cascada de forma atómica y sin violar restricciones referenciales.

---

## Alternativas Consideradas

* **Tabla Única Consolidada (God Table):** Descartada por el pésimo diseño relacional, nula extensibilidad y la imposibilidad de aplicar restricciones de base de datos para asegurar integridad de datos.
* **Herencia Single Table (JPA `@Inheritance(strategy = InheritanceType.SINGLE_TABLE)`):** Descartada porque físicamente produce la misma God Table con abundancia de campos nulos en PostgreSQL.
* **Herencia Joined (JPA `@Inheritance(strategy = InheritanceType.JOINED`):** Es la alternativa más cercana, pero se prefirió el acoplamiento explícito manual mediante tablas separadas y `@MapsId` para tener un control granular absoluto sobre las consultas SQL nativas y una conversión DTO limpia por composición.

---

## Ver también

- [ADR-04: Registro Unificado de Perfiles](/arquitectura/adr-04-registro-unificado.md)
- [Modelado de Entidades en JPA](/guias-desarrollo/convenciones-codigo.md#4-reglas-para-entidades-jpa)
- [Catálogo de Dominios del Proyecto](/arquitectura/dominios.md)
