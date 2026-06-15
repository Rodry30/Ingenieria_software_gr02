[Inicio](/) > [Configuración](/configuracion/setup-local.md) > Variables de Entorno

# Gestión de Variables de Entorno

FoodGest sigue estrictamente las directrices del factor *Configuración* de la metodología **Twelve-Factor App**, lo que implica que toda configuración que varíe entre entornos (como credenciales de base de datos o claves de cifrado) debe ser inyectada al backend en tiempo de ejecución a través de **Variables de Entorno**.

---

## 1. Tabla de Variables de Configuración

A continuación, se detallan todas las variables definidas en la configuración del proyecto, sus propósitos y los valores estándar según el entorno:

| Variable | Propósito | Valor en Despliegue Local (Dev) | Valor Recomendado en Producción |
| :--- | :--- | :--- | :--- |
| `server.port` | Puerto de escucha HTTP de la API REST. | `8080` | `8080` (o el asignado por el host Cloud) |
| `DB_URL` | URL de conexión JDBC hacia PostgreSQL. | `jdbc:postgresql://localhost:5432/foodgest` | `jdbc:postgresql://<HOST_PROD>:<PORT>/foodgest` |
| `DB_USER` | Nombre de usuario administrador de la BD. | `postgres` | `db_foodgest_admin_user` (usuario con accesos acotados) |
| `DB_PASSWORD` | Contraseña del usuario de base de datos. | `123456` | `<CONTRASENA_COMPLEJA_ALFANUMERICA>` |
| `JWT_SECRET` | Clave secreta hexadecimal HS256 para firmar tokens. | `superSecretKeyForDevEnvMustBeLongAndSafe123!` | `<CADENA_COMPLEJA_HS256_MINIMO_256_BITS>` |
| `JWT_EXPIRATION` | Duración del token de acceso (en milisegundos). | `900000` (15 minutos) | `900000` (15 minutos) |
| `JWT_REFRESH_EXPIRATION` | Duración del token de renovación (en milisegundos). | `604800000` (7 días) | `604800000` (7 días) |
| `SPRING_SQL_INIT_MODE` | Determina si ejecuta scripts SQL automáticos al levantar. | `always` | `never` (las migraciones se hacen vía CI/CD en prod) |

---

## 2. Configuración en Entorno Local (`.env`)

Para evitar tener que escribir manualmente las credenciales en la terminal en cada arranque o modificar accidentalmente el archivo `application.properties` con contraseñas locales privadas, se recomienda el uso de un archivo local de entorno.

### Instrucciones de Creación:
1. En la raíz del repositorio, crea un archivo llamado `.env` (este archivo está listado en tu `.gitignore` para prevenir subidas involuntarias).
2. Agrega las variables según tus accesos locales:
```bash
# Servidor
server.port=8080

# Base de Datos
DB_URL=jdbc:postgresql://localhost:5432/foodgest
DB_USER=postgres
DB_PASSWORD=mi_contrasena_secreta

# Seguridad
JWT_SECRET=estaClaveEsSuperSecretaParaFirmarTokensJWT2026!
JWT_EXPIRATION=900000
JWT_REFRESH_EXPIRATION=604800000
```
3. Si utilizas un IDE como IntelliJ IDEA o Eclipse, puedes instalar extensiones de soporte para archivos `.env` o agregar estas variables de entorno directamente en el perfil de ejecución (*Run Configuration*) de la clase principal `FoodgestApplication.java`.

---

## 3.  ADVERTENCIA DE SEGURIDAD CRÍTICA

> [!CAUTION]
> **BAJO NINGUNA CIRCUNSTANCIA** se debe commitear o subir al repositorio de GitHub el archivo `.env` o modificar directamente el `application.properties` con contraseñas reales del entorno de Staging o Producción. 
> 
> Revelar claves secretas (`JWT_SECRET`) o credenciales de base de datos de producción comprometerá por completo la seguridad financiera de los monederos (*wallets*), las contraseñas de los usuarios y las ubicaciones privadas de las parcelas agrícolas.

---

## Ver también

- [Guía de Configuración Local](/configuracion/setup-local.md)
- [Esquema de Entornos del Sistema](/configuracion/entornos.md)
- [Checklist de Seguridad en Pull Requests](/guias-desarrollo/checklist-pull-requests.md)
