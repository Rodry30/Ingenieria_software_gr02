[Inicio](/) > [Guías de Desarrollo](/guias-desarrollo/convenciones-codigo.md) > Conventional Commits

# Convención de Commits (Conventional Commits)

FoodGest exige el cumplimiento de la especificación **Conventional Commits** para todos los mensajes de commit realizados en el repositorio de GitHub. Esto permite estructurar un historial de Git limpio, legible, y facilita la generación automática de notas de lanzamiento (*changelogs*).

---

## 1. Estructura Estándar de Mensaje

El mensaje del commit debe estructurarse de la siguiente manera:

```
<tipo>(<módulo/dominio>): <descripción corta en minúsculas y modo imperativo>

[cuerpo del mensaje opcional con detalles más extensos]

[pie de página opcional con referencias a issues de GitHub o HUs de Trello]
```

---

## 2. Tipos de Commits Permitidos

| Tipo de Commit | Cuándo Utilizarlo |
| :--- | :--- |
| **`feat`** | Incorporación de una nueva funcionalidad para el usuario final (ej. un nuevo endpoint, una nueva vista). |
| **`fix`** | Solución de un error o bug técnico en el código de producción. |
| **`docs`** | Cambios exclusivamente en la documentación del proyecto (archivos `.md`, Javadocs). |
| **`style`** | Ajustes cosméticos que no alteran el comportamiento del código (espaciados, tabulaciones, formateo automático). |
| **`refactor`** | Cambios en el código que no corrigen un bug ni añaden una funcionalidad, pero mejoran la estructura o rendimiento del código. |
| **`test`** | Creación, modificación o ampliación de pruebas automatizadas (unitarias o de integración). |
| **`chore`** | Labores de mantenimiento rutinarias (actualización de librerías, dependencias de Maven, scripts de CI/CD). |

---

## 3. Ejemplos Reales en el Contexto de FoodGest

| Mensaje de Commit Correcto | Explicación |
| :--- | :--- |
| `feat(auth): implementar registro unificado de agricultores con MapsId` | Añade un nuevo endpoint y lógica de negocio para registro de usuarios. |
| `fix(logistica): corregir error de precision decimal en el calculo de flete` | Soluciona un bug específico en el cálculo de transporte. |
| `docs(arquitectura): redactar ADR-01 para la seleccion de PostgreSQL` | Modifica documentación en la wiki de arquitectura. |
| `refactor(marketplace): optimizar consulta de ofertas por radio de busqueda` | Reestructura la consulta SQL espacial para mejorar performance. |
| `test(users): agregar pruebas de integracion para transacciones de wallet` | Añade cobertura de JUnit para monederos digitales. |
| `chore(deps): actualizar version de jjwt a la 0.11.5 en pom.xml` | Tarea de mantenimiento de librerías y dependencias. |

---

## 4. Ejemplos de Mensajes de Commit Correctos e Incorrectos

### ❌ Mensajes INCORRECTOS:
*   `subiendo cambios` (No define tipo, módulo, ni es descriptivo).
*   `fix: arreglado error en ofertas` (Falta especificar el módulo entre paréntesis, descripción en participio en lugar de imperativo).
*   `FEAT(SUBASTAS): AGREGADO PUJAS` (Usa mayúsculas incorrectas).

###  Mensajes CORRECTOS:
*   `feat(subastas): crear servicio para recibir pujas de compradores`
*   `fix(pedidos): solucionar desbordamiento de saldo en monedero tras escrow`
*   `refactor(shared): centralizar manejo de excepciones de base de datos`

---

## 5. Cómo Revertir un Commit con Formato Incorrecto

Si por error realizas un commit localmente que infringe las convenciones y aún **no** le has hecho `git push` al servidor, puedes corregirlo inmediatamente usando la herramienta de modificación de Git:

```bash
# Permite reeditar el mensaje del último commit realizado
git commit --amend
```
Esto abrirá tu editor de texto en terminal (ej. Nano o Vim), donde podrás corregir el mensaje siguiendo el estándar e indicar los tipos adecuados. 

Si el commit ya fue subido al servidor remoto (`push`), **nunca** uses `--amend` ni hagas reescritura de historial (`push --force`) sobre ramas compartidas (`develop` o `main`), ya que desestabilizará el espacio de trabajo de tus compañeros de equipo. En su lugar, esmérate en escribir de forma impecable los commits siguientes.

---

## Ver también

- [Convenciones de Nomenclatura Java](/guias-desarrollo/convenciones-codigo.md)
- [Checklist para Pull Requests](/guias-desarrollo/checklist-pull-requests.md)
- [Definición de Done (DoD)](/procesos/definition-of-done.md)
