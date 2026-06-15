[Inicio](/) > [Arquitectura](/arquitectura/README.md) > [ADRs](/arquitectura/README.md#4-principios-de-diseno-adoptados) > ADR-04

# ADR-04: Registro unificado de usuarios y perfiles especializados en un solo endpoint transaccional

* **ID:** ADR-04
* **Título:** Registro Unificado Transaccional (`POST /api/auth/register`)
* **Estado:** Aprobado
* **Fecha:** 2026-06-01
* **Autor:** Equipo de Desarrollo Backend

---

## Contexto

En el dominio de FoodGest, un usuario no puede existir en un "limbo". Esto significa que cualquier usuario registrado con el tipo `agricultor` obligatoriamente debe tener asociada una fila correspondiente en la tabla `agricultores` (con sus datos de finca, hectáreas, RUC e información geográfica de su parcela). Del mismo modo, un usuario con el tipo `comprador` debe tener una fila en la tabla `compradores` (con su tipo de comprador y dirección de entrega por defecto).

Si el registro de usuarios y la creación de perfiles se realizara en dos pasos secuenciales en el frontend:
1. Paso 1: `POST /api/users` para crear el registro básico en la tabla `users`.
2. Paso 2: `POST /api/profiles/farmers` para crear el perfil de agricultor.

Se corre el altísimo riesgo de fallos de red intermedios o errores de usuario en el segundo paso, lo que generaría **datos huérfanos e inconsistencias severas** en el sistema: un usuario existente de tipo `agricultor` pero sin perfil asociado, provocando errores catastróficos (`NullPointerException`) en las búsquedas en el mapa o en las pantallas de perfil.

---

## Decisión

Se decide implementar un **único endpoint unificado de registro**: `POST /api/auth/register`, el cual recibe un DTO compuesto (`RegisterRequestDto`) que contiene tanto la información de credenciales básicas de la cuenta, como un bloque flexible denominado `perfil` de tipo `Map<String, Object>`.

El servicio backend `AuthServiceImpl.register()` procesa este flujo bajo una única anotación **`@Transactional`**:
1. Crea y guarda el registro en la tabla `users`, obteniendo el `UUID` generado.
2. Utiliza `ObjectMapper` para deserializar el `Map` dinámico del perfil en el DTO correspondiente (`AgricultorCreateDto` o `CompradorCreateDto`) según el campo `tipo_usuario`.
3. Crea y persiste la entidad de perfil especializada (`Agricultor` o `Comprador`) enlazándola por llave foránea 1:1 con el usuario.
4. Genera y persiste una billetera por defecto (`Wallet`) inicializada con saldos en `0.00 PEN`.

Si cualquiera de estos pasos falla, la transacción realiza un **Rollback automático**, asegurando que la base de datos nunca quede en un estado inconsistente.

---

## Consecuencias

### Positivas (+)
* **Garantía Absoluta de Integridad:** Se elimina por completo el riesgo de crear usuarios sin perfil o usuarios sin billetera. La base de datos mantiene integridad referencial impecable.
* **Experiencia de Usuario Fluida (UX):** El cliente realiza una única llamada de red y el registro se completa de manera atómica instantáneamente.
* **Simplificación del Frontend:** El cliente no necesita manejar complejos estados de transición para usuarios a medio registrar.

### Negativas (-)
* **DTO Complejo:** El DTO de entrada en el backend requiere una estructura anidada flexible (`Map`), lo que dificulta la validación automática directa de campos específicos de perfiles a primer nivel, obligando a usar validación programática con inyección de `ObjectMapper`.
* **Carga Transaccional:** La transacción en PostgreSQL permanece abierta por más tiempo en comparación con microtransacciones individuales, aunque el impacto en performance es imperceptible para el volumen actual.

---

## Alternativas Consideradas

* **Registro en dos fases:** Descartada por la falta de atomicidad de red y la alta propensión a la generación de datos huérfanos difíciles de limpiar periódicamente.
* **Tabla de Usuarios Única (God Table):** Descartada (ver [ADR-06](/arquitectura/adr-06-separacion-perfiles.md)) por problemas de diseño de base de datos relacional limpia.

---

## Ver también

- [Flujo de Registro Unificado en Código](/apis/endpoints-implementados.md#1-autenticación)
- [ADR-06: Separación de Perfiles por Tabla Especializada](/arquitectura/adr-06-separacion-perfiles.md)
- [Definición de DTOs en Guías de Desarrollo](/guias-desarrollo/convenciones-codigo.md#3-reglas-para-dtos)
