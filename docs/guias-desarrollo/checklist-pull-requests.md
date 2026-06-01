[Inicio](/) > [Guías de Desarrollo](/guias-desarrollo/convenciones-codigo.md) > Checklist Pull Requests

# Checklist para Pull Requests y Code Review

El proceso de revisión de código (*Code Review*) es crucial en FoodGest para asegurar la calidad de la base de código, compartir conocimiento técnico entre los integrantes del equipo y garantizar que ningún bug se filtre a ramas críticas.

---

## 1. Para el Autor del PR (Antes de abrir la revisión)

Antes de cambiar el estado de tu Pull Request a "Ready for Review" en GitHub, asegúrate de marcar con un check cada uno de los siguientes puntos en la plantilla del PR:

*   [ ] **Compilación Local Exitosa:** El proyecto compila limpiamente al ejecutar `./mvnw clean compile` sin errores ni advertencias de deprecación críticas.
*   [ ] **Pruebas Unitarias Aprobadas:** Todas las pruebas locales pasan con éxito (`./mvnw test`).
*   [ ] **Cobertura Mínima Asegurada:** Se ha verificado que la cobertura de código para la funcionalidad añadida no disminuye el umbral requerido del **80%**.
*   [ ] **Migración e Integridad de BD:** Si se realizaron cambios en el modelo físico de base de datos, se adjunta el script SQL de migración correspondiente y se ha verificado localmente.
*   [ ] **Cumplimiento de Nomenclatura:** Las clases, DTOs y variables creados respetan rigurosamente las [Convenciones de Código](/guias-desarrollo/convenciones-codigo.md).
*   [ ] **Mensajes de Commit Correctos:** Los commits incluidos en el PR siguen estrictamente el formato de [Conventional Commits](/guias-desarrollo/conventional-commits.md).
*   [ ] **Nula Presencia de Secretos:** Se ha revisado manualmente que el código **NO** contiene contraseñas en texto plano, tokens API, claves de firma o URLs privadas. Todo se inyecta por variables de entorno.

---

## 2. Para el Revisor (Antes de aprobar el Merge)

Al revisar el Pull Request de un colega del equipo, enfoca tu análisis en los siguientes aspectos técnicos fundamentales:

*   [ ] **Modularidad y DDD Lite:** ¿El autor respetó los límites del dominio de negocio? ¿Se inyectaron interfaces de servicio en lugar de repositorios de otros dominios directamente?
*   [ ] **Validación Estricta:** ¿Todos los DTOs de entrada cuentan con anotaciones de validación `@NotBlank`, `@Positive`, etc. pertinentes?
*   [ ] **Gestión de Excepciones:** ¿Se utilizaron las excepciones compartidas de `com.foodgest.shared.exceptions` (ej. `BusinessException`) o se lanzaron excepciones genéricas incorrectas?
*   [ ] **Consultas de Base de Datos:** ¿Las consultas personalizadas del repositorio utilizan los índices correctos? ¿Se evitan bucles que ejecuten consultas redundantes en la base de datos (problema de N+1 consultas)?
*   [ ] **Optimización Espacial:** Para consultas geográficas de mapas u ofertas, ¿se emplearon las funciones geográficas nativas de PostGIS (`ST_DWithin`) optimizadas con índices GiST?

---

## 3. Criterios de Rechazo Automático (Bloqueadores de Merge)

Un revisor tiene la obligación y la facultad de marcar un PR con **"Request Changes" (Rechazado)** de forma inmediata si se detecta cualquiera de los siguientes factores:

1.  **Código Muerto o Comentado:** Presencia de bloques de código antiguo comentados, impresiones en consola (`System.out.println`) o logs de depuración huérfanos.
2.  **Violación del Aislamiento de Dominios:** Acceso directo a bases de datos o repositorios ajenos sin pasar por la capa de interfaces del dominio de negocio correspondiente.
3.  **Contraseñas o Credenciales Duras:** Cualquier token, clave o credencial quemada (*hardcoded*) en el código fuente.
4.  **Pruebas Rotas en Pipeline:** Fallo de compilación o rotura de tests unitarios existentes en el pipeline de GitHub Actions.
5.  **Placeholders:** Presencia de textos temporales como `// TODO: completar despues` o `/* pendiente */` en el código funcional de producción.

---

## 4. Ejemplos de Comentarios Constructivos en Code Review

El tono de la revisión siempre debe ser técnico, educado, constructivo y enfocado en la mejora continua del producto.

### ❌ Ejemplos de lo que NO se debe escribir:
*   *"Esto está mal hecho, cámbialo de inmediato."* (Agresivo y no aporta contexto).
*   *"No me gusta cómo se ve esta clase."* (Subjetivo, sin fundamento técnico).

###  Ejemplos de comentarios constructivos excelentes:
*   *"Excelente avance aquí. Sin embargo, noto que estamos inyectando `UserRepository` directamente en el `SubastaServiceImpl` de otro dominio. Para respetar las reglas de comunicación de nuestra arquitectura, sugiero inyectar la interfaz `IUserService` en su lugar. ¿Qué opinas?"*
*   *"Veo que el campo `precioUnitario` en tu DTO `OfertaCreateDto` no tiene la anotación `@Positive`. Es importante añadirla para evitar que se registren ofertas agrícolas con precios negativos o en cero a nivel de API. Sugiero agregar `@Positive(message = "El precio debe ser mayor a cero")`."*

---

## Ver también

- [Convenciones de Nomenclatura Java](/guias-desarrollo/convenciones-codigo.md)
- [Estrategia de Ramas Git Flow](/guias-desarrollo/git-flow.md)
- [Definición de Done (DoD)](/procesos/definition-of-done.md)
