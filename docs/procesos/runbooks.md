[Inicio](/) > [Procesos](/procesos/definition-of-done.md) > Runbooks

# Guías de Resolución de Incidentes (Runbooks)

Este documento detalla los procedimientos técnicos paso a paso para diagnosticar, mitigar y solucionar de forma ágil los incidentes de infraestructura y software más recurrentes en el ecosistema **FoodGest**.

---

## 🛠️ Runbook 1: La Aplicación no arranca (Application Failed to Start)

### 🚨 Síntomas:
Al ejecutar `./mvnw spring-boot:run` o levantar el contenedor Docker, el backend aborta abruptamente imprimiendo trazas rojas en la terminal y cerrando el proceso.

### 🔍 Diagnóstico y Soluciones:

#### A. Excepción: `ConflictingBeanDefinitionException`
*   **Causa:** Se han detectado dos nombres de clases idénticos anotados con `@Service`, `@Component` o `@Repository` bajo el mismo escaneo de paquetes, o existe una interfaz con múltiples clases de implementación activas sin calificadores.
*   **Solución:**
    1.  Inspecciona los logs para identificar los dos nombres conflictivos indicados en la traza.
    2.  Si estás utilizando múltiples implementaciones de un servicio (ej. dos implementaciones de `IAuthService` por pruebas), añade la anotación **`@Primary`** sobre la implementación oficial, o utiliza **`@Qualifier("nombreEspecifico")`** para inyecciones ambiguas.

#### B. Excepción: `NoSuchBeanDefinitionException: No qualifying bean of type 'com.fasterxml.jackson.databind.ObjectMapper'`
*   **Causa:** Una clase inyecta `ObjectMapper` de Jackson en su constructor, pero la configuración interna de Spring Boot falló al cargar los mapeadores por incompatibilidades de módulos de fechas de Java 8.
*   **Solución:**
    1.  Asegúrate de tener declarada la dependencia de Jackson JSR310 en tu `pom.xml`:
        ```xml
        <dependency>
            <groupId>com.fasterxml.jackson.datatype</groupId>
            <artifactId>jackson-datatype-jsr310</artifactId>
        </dependency>
        ```
    2.  Revisa que tu clase `JacksonConfig.java` en el dominio `config` registre correctamente el módulo del formato temporal:
        ```java
        @Configuration
        public class JacksonConfig {
            @Bean
            public ObjectMapper objectMapper() {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                return mapper;
            }
        }
        ```

---

## 🔌 Runbook 2: Error de Conexión a la Base de Datos PostgreSQL

### 🚨 Síntomas:
Los endpoints responden con errores `500` masivos, o el log de consola emite de forma repetida el mensaje:
`Cannot get JDBC Connection; nested exception is org.postgresql.util.PSQLException: Connection refused`

### 🔍 Diagnóstico y Soluciones:
1.  **Verificar estado del servicio PostgreSQL local:**
    *   *En Windows (PowerShell):* Ejecuta `Get-Service postgresql*` para verificar si está corriendo. Si está detenido, ejecútalo con `Start-Service postgresql*`.
    *   *En Linux:* Corre `sudo systemctl status postgresql`.
2.  **Verificar credenciales y puerto:**
    *   Revisa que el puerto de PostgreSQL (por defecto `5432`) no esté bloqueado o reasignado.
    *   Prueba conectarte manualmente a la base de datos `foodgest` desde la terminal utilizando `psql` para descartar bloqueos de usuario:
        ```bash
        psql -h localhost -U postgres -d foodgest
        ```
    *   Si la conexión manual es exitosa, revisa que tu archivo `.env` o las variables de entorno inyectadas no contengan erratas tipográficas en `DB_USER` o `DB_PASSWORD`.

---

## 🔑 Runbook 3: JWT Token es declarado inválido en todas las peticiones

### 🚨 Síntomas:
El cliente móvil / frontend no puede acceder a ningún recurso protegido y recibe respuestas sistemáticas del tipo **`401 Unauthorized`**, a pesar de enviar la cabecera `Authorization` correcta.

### 🔍 Diagnóstico y Soluciones:
1.  **Verificar la firma del token:**
    *   Copia uno de los tokens JWT de respuesta del login generados e ingresa a `jwt.io`. Pastea el token y valida si el payload coincide y si el firmado es HS256.
2.  **Diferencia de claves secretas (`JWT_SECRET`):**
    *   *Causa común:* El servidor backend de producción fue reiniciado sin inyectar la variable de entorno fija `JWT_SECRET`, obligando a la aplicación a autogenerar una clave aleatoria en memoria en el arranque. Esto causa que todos los tokens JWT emitidos previamente a los usuarios dejen de ser válidos instantáneamente.
    *   *Solución:* Define la variable `JWT_SECRET` como una variable estática persistente y robusta en el panel de configuración de tu servidor Cloud o en tu archivo `.env` local. Nunca dejes que el backend inicie sin un secreto definido y acotado.

---

## 🛡️ Runbook 4: El Quality Gate de SonarQube falla en el pipeline

### 🚨 Síntomas:
El Pull Request en GitHub bloquea de forma automática la posibilidad de fusionar (*Merge*) a `develop` porque el paso del análisis estático de código de SonarQube falla en GitHub Actions.

### 🔍 Diagnóstico y Soluciones:
1.  **Baja Cobertura de Código:**
    *   *Problema:* El nuevo código añadido en el PR tiene una cobertura de pruebas inferior al **80%**.
    *   *Solución:* Identifica qué clases nuevas del dominio carecen de tests y redacta pruebas unitarias JUnit en tu paquete paralelos bajo `src/test/java`. Vuelve a correr `./mvnw clean test` y empuja los cambios a tu rama.
2.  **Presencia de Vulnerabilidades (Code Smells / Security Hotspots):**
    *   *Problema:* SonarQube detectó contraseñas quemadas, uso de métodos criptográficos obsoletos, o bucles infinitos peligrosos.
    *   *Solución:* Ingresa al dashboard del reporte provisto en la alerta del PR de GitHub, ubica exactamente la línea de código señalada y corrígela (ej. retirando la clave dura e implementando inyección vía `@Value` de Spring).

---

## Ver también

- [Guía de Configuración Local](/configuracion/setup-local.md)
- [Proceso de Despliegue y Rollback](/procesos/despliegue-rollback.md)
- [Definition of Done (DoD)](/procesos/definition-of-done.md)
