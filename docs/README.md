# 🌾 Bienvenido a la Wiki Técnica de FoodGest

---

**FoodGest** es una plataforma digital agrícola B2B/B2C diseñada para el mercado peruano. Conecta a agricultores directamente con compradores y transportistas certificados, eliminando intermediarios costosos mediante negociaciones transparentes y un flujo transaccional seguro respaldado por geolocalización.

Este espacio contiene toda la documentación técnica de ingeniería, arquitectura, guías de desarrollo y runbooks operativos para los desarrolladores y mantenedores de la plataforma.

---

## 🗺️ Mapa de Navegación Rápida

Consulte las diferentes secciones de nuestra documentación según su perfil e interés actual:

### 🏛️ [Arquitectura y ADRs](/arquitectura/README.md)
*   Conozca la estructura de contenedores C4, el stack tecnológico de backend (Java 17 / Spring Boot) y base de datos (PostgreSQL + PostGIS).
*   Consulte los 6 registros de decisiones de arquitectura (**ADRs**) que justifican nuestras elecciones técnicas clave.

### 🛠️ [Guías de Desarrollo](/guias-desarrollo/convenciones-codigo.md)
*   Alinee su código con las [Convenciones de Código Java](/guias-desarrollo/convenciones-codigo.md).
*   Familiarícese con nuestra estrategia de ramas [Git Flow](/guias-desarrollo/git-flow.md) y las convenciones de [Conventional Commits](/guias-desarrollo/conventional-commits.md).
*   Prepare sus Pull Requests usando el [Checklist obligatorio de revisión](/guias-desarrollo/checklist-pull-requests.md).

### ⚙️ [Configuración de Entorno](/configuracion/setup-local.md)
*   Siga la guía paso a paso para levantar el proyecto en su máquina [local](/configuracion/setup-local.md).
*   Conozca las [variables de entorno](/configuracion/variables-entorno.md) requeridas y la gestión de [entornos](/configuracion/entornos.md) (Dev, Staging, Prod).

### 📡 [APIs e Integración](/apis/endpoints-implementados.md)
*   Consulte el catálogo de [endpoints REST](/apis/endpoints-implementados.md) con ejemplos reales de JSON para registro, login y refresh token.
*   Comprenda el flujo financiero de [pagos con escrow](/apis/integracion-pagos.md) (pasarela Culqi).
*   Vea cómo opera el cálculo geográfico de [búsqueda por radio y flete con PostGIS](/apis/integracion-maps.md).

### 📈 [Procesos y Calidad](/procesos/definition-of-done.md)
*   Revise los criterios obligatorios del [Definition of Done (DoD)](/procesos/definition-of-done.md) y el [Definition of Ready (DoR)](/procesos/definition-of-ready.md).
*   Consulte el plan de [Despliegue y Rollback](/procesos/despliegue-rollback.md) en producción.
*   Encuentre soluciones a fallos del sistema en las guías [Runbooks](/procesos/runbooks.md).

---

## 🚀 ¿Cómo previsualizar esta wiki en tu máquina local?

Una de las grandes ventajas de **Docsify** es que **no requiere compilar archivos estáticos ni instalar pesadas dependencias de Node.js en el proyecto**. Todo el motor se ejecuta directamente en el navegador web cargándose dinámicamente mediante CDN.

### Opción 1: Servidor HTTP Global (Docsify CLI)
Si deseas utilizar la herramienta oficial de Docsify:
1. Instala el CLI de Docsify de forma global en tu máquina:
   ```bash
   npm install -g docsify-cli
   ```
2. Inicia el servidor de desarrollo apuntando a la carpeta `/docs`:
   ```bash
   docsify serve docs
   ```
3. Abre tu navegador en: **`http://localhost:3000`**

### Opción 2: Extensión Live Server de VS Code (La más sencilla)
1. Instala la extensión **Live Server** en Visual Studio Code.
2. Haz clic derecho sobre el archivo `docs/index.html` y selecciona **Open with Live Server**.

### Opción 3: Servidor rápido con Python
Si tienes Python instalado en tu máquina, abre una terminal en la raíz del proyecto y corre:
```bash
python -m http.server 3000
```
Luego abre tu navegador en `http://localhost:3000/docs/`.
