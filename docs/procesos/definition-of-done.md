[Inicio](/) > [Procesos](/procesos/definition-of-done.md) > Definition of Done

# Definition of Done (DoD) de FoodGest

La **Definición de Terminado (Definition of Done)** representa el acuerdo formal y de calidad que comparte el equipo de ingeniería de FoodGest. Ninguna Historia de Usuario (HU) o tarea técnica en el tablero de Trello puede moverse a la columna **"Done" (Terminado)** si no satisface el 100% de los criterios listados en este documento.

---

## 1. Criterios de Calidad por Categoría

### 💻 Código
*   **Cumplimiento de Estilo:** El código respeta rigurosamente las pautas de nomenclatura y convenciones detalladas en la [Guía de Convenciones de Código](/guias-desarrollo/convenciones-codigo.md).
*   **Sin Código Muerto:** No existen impresiones en terminal (`System.out.println`), trazas de depuración de IDEs ni bloques de código comentados.
*   **Compilación Limpia:** El código compila localmente en un entorno limpio al ejecutar `./mvnw clean compile` sin errores ni alertas críticas de compilación.
*   **Manejo de Excepciones:** No se capturan excepciones genéricas (`catch(Exception e)`) sin tratamiento, y todo error del negocio lanza excepciones tipificadas del dominio o de `shared.exceptions`.

### 🧪 Pruebas
*   **Pruebas Unitarias Aprobadas:** Todos los tests de JUnit existentes y los nuevos se ejecutan exitosamente al correr `./mvnw test`.
*   **Cobertura del 80%:** La cobertura de código para las nuevas clases del dominio es igual o superior al **80%** (medida localmente con JaCoCo o en SonarQube).
*   **Prueba de Integración Espacial:** Para servicios que utilizan coordenadas (como búsquedas de ofertas), se ha verificado el correcto funcionamiento espacial con datos reales.

### 📝 Documentación
*   **Documentación de Endpoints:** Si se creó o modificó un endpoint REST, las anotaciones de OpenAPI/Swagger (`@Tag`, `@Operation`) están actualizadas y renderizan correctamente en `/swagger-ui.html`.
*   **Wiki Técnica Actualizada:** Cualquier decisión arquitectónica relevante cuenta con su respectivo documento de registro de arquitectura ([ADRs](/arquitectura/README.md#4-principios-de-diseno-adoptados)).
*   **Javadocs Esenciales:** Los métodos transaccionales críticos de las clases `ServiceImpl` tienen comentarios Javadoc describiendo entradas, salidas y comportamiento de negocio.

### 🔍 Revisión de Código (Code Review)
*   **Aprobación Obligatoria:** El Pull Request (PR) cuenta con la revisión y el voto aprobatorio de al menos **1 revisor técnico** calificado del equipo.
*   **Pipeline de CI Aprobado:** El pipeline de GitHub Actions se ejecuta con estado verde exitoso, habiendo superado el umbral de SonarQube.

### 🚀 Despliegue
*   **Entorno de Staging:** Los cambios fueron integrados en la rama `develop` y se han desplegado de forma limpia en el servidor de Staging.
*   **Verificación Manual:** Se ha verificado manualmente el correcto funcionamiento de la Historia de Usuario consumiendo la API desplegada en Staging a través de clientes HTTP (Postman/Curl) o desde la interfaz móvil de pruebas.

---

## 2. Checklist Imprimible para Sprints

Utiliza esta lista de verificación simplificada en tus revisiones diarias y al final de cada iteración de sprint:

```markdown
- [ ] ¿El código sigue las convenciones de nomenclatura PascalCase y camelCase?
- [ ] ¿Se eliminaron todos los System.out.println y código antiguo comentado?
- [ ] ¿El build de Maven compila localmente de forma limpia?
- [ ] ¿Las pruebas automatizadas de JUnit pasan al 100%?
- [ ] ¿La cobertura de código del módulo se mantiene arriba del 80%?
- [ ] ¿Los endpoints de Swagger están documentados y libres de placeholders?
- [ ] ¿Se abrió el PR hacia develop siguiendo las Conventional Commits?
- [ ] ¿Se obtuvo al menos 1 aprobación técnica de un compañero en GitHub?
- [ ] ¿El cambio fue desplegado y verificado físicamente en Staging?
```

---

## Ver también

- [Checklist de Pull Requests](/guias-desarrollo/checklist-pull-requests.md)
- [Definición de Ready (DoR)](/procesos/definition-of-ready.md)
- [Flujo de Despliegue y Rollback](/procesos/despliegue-rollback.md)
