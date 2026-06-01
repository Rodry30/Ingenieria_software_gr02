[Inicio](/) > [Configuración](/configuracion/setup-local.md) > Configuración Local

# Guía de Configuración del Entorno Local

Esta guía detalla los pasos requeridos para compilar, configurar y ejecutar el backend del proyecto **FoodGest** en tu máquina de desarrollo local.

---

## 1. Requisitos Previos

Antes de comenzar, asegúrate de tener instaladas las siguientes herramientas con sus versiones recomendadas:

*   **Java Development Kit (JDK):** Versión 17 (LTS). Se recomienda Adoptium Temurin o Amazon Corretto.
*   **Apache Maven:** Versión 3.8 o superior (opcional, ya que el proyecto incluye el envoltorio `./mvnw`).
*   **PostgreSQL:** Versión 15.x.
*   **PostGIS:** Versión 3.x (Extensión geoespacial activa).
*   **Git:** Versión 2.30+ para control de versiones.

---

## 2. Paso 1: Clonar el Repositorio

Abre una terminal y clona el repositorio del proyecto en tu directorio local:

```bash
git clone https://github.com/Rodry30/Ingenieria_software_gr02.git
cd Ingenieria_software_gr02
```

---

## 3. Paso 2: Crear la Base de Datos con Soporte PostGIS

1. Conéctate a tu consola de PostgreSQL (utilizando `psql` o una interfaz gráfica como pgAdmin o DBeaver).
2. Ejecuta los siguientes comandos SQL exactos para crear la base de datos y activar la extensión espacial y de generación de UUIDs:

```sql
-- Crear base de datos
CREATE DATABASE foodgest;

-- Conectarse a la base de datos (en psql)
\c foodgest;

-- Habilitar la extensión de geolocalización PostGIS
CREATE EXTENSION IF NOT EXISTS postgis;

-- Habilitar la extensión para generación de identificadores UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
```

---

## 4. Paso 3: Configurar el Archivo `application.properties`

El archivo de configuración principal se encuentra en `src/main/resources/application.properties`. 

Para el entorno de desarrollo local, puedes definir variables de entorno en tu sistema operativo o simplemente sobrescribir los valores por defecto. Asegúrate de configurar correctamente los accesos a tu base de datos local:

```properties
spring.application.name=grupo2

# Configuración de PostgreSQL local
spring.datasource.url=jdbc:postgresql://localhost:5432/foodgest
spring.datasource.username=tu_usuario_postgres
spring.datasource.password=tu_contrasena_postgres
spring.datasource.driver-class-name=org.postgresql.Driver

# Configuración del Dialecto de Base de Datos y JPA
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Ruta de documentación OpenAPI Swagger
springdoc.swagger-ui.path=/swagger-ui.html

# Ejecución automática de scripts SQL
spring.sql.init.mode=always
```

---

## 5. Paso 4: Creación de Tablas e Inicialización de Datos

Dado que `spring.jpa.hibernate.ddl-auto` está configurado en `none`, la estructura de tablas de la base de datos debe ser creada de forma explícita.
*   El backend incluye la directiva `spring.sql.init.mode=always`, lo cual cargará y ejecutará automáticamente los archivos de inicialización y semillas presentes en la carpeta de recursos.
*   Asegúrate de que los archivos `schema.sql` (creación de las 23 tablas) y `data.sql` (inserción de usuarios iniciales) estén presentes en `src/main/resources`. De lo contrario, ejecuta manualmente el script SQL consolidado del proyecto en tu gestor de base de datos.

---

## 6. Paso 5: Correr la Aplicación

Desde la raíz del proyecto, ejecuta el envoltorio de Maven para descargar las dependencias, compilar y levantar la aplicación Spring Boot:

**En Linux / macOS:**
```bash
./mvnw spring-boot:run
```

**En Windows (Command Prompt o PowerShell):**
```powershell
.\mvnw.cmd spring-boot:run
```

Si el servidor inicia correctamente, verás una línea de log similar a:
`Started FoodgestApplication in X.XXX seconds (JVM running for X.XX)` escuchando en el puerto por defecto **8080**.

---

## 7. Paso 6: Verificar el Funcionamiento

Para confirmar que la aplicación está respondiendo de manera correcta, abre tu navegador web o ejecuta el siguiente comando en terminal:

```bash
# Consultar la documentación interactiva Swagger
http://localhost:8080/swagger-ui/index.html

# Probar el listado de usuarios de prueba iniciales creados por data.sql
curl -X GET http://localhost:8080/api/users
```

---

## 8. Solución de Problemas Comunes (Troubleshooting)

### Error: `PostGIS extension not found`
*   **Causa:** No se ha habilitado físicamente PostGIS en la base de datos o PostgreSQL no tiene instalado el paquete espacial.
*   **Solución:** Ejecuta `CREATE EXTENSION postgis;` en la consola SQL de la base de datos `foodgest`. Si esto arroja error, instala PostGIS en tu sistema (en Ubuntu: `sudo apt-get install postgresql-15-postgis-3`).

### Error: `Address already in use`
*   **Causa:** El puerto 8080 ya está siendo utilizado por otro servicio (ej. Tomcat o Docker).
*   **Solución:** Detén el servicio que ocupa el puerto o edita `application.properties` añadiendo la variable `server.port=8081` para levantar FoodGest en un puerto alternativo.

---

## Ver también

- [Configuración de Variables de Entorno](/configuracion/variables-entorno.md)
- [Gestión de Entornos del Proyecto](/configuracion/entornos.md)
- [Runbook: Errores Comunes de Arranque](/procesos/runbooks.md)
