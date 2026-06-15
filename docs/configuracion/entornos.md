[Inicio](/) > [Configuración](/configuracion/setup-local.md) > Entornos

# Gestión de Entornos

Para asegurar el ciclo de vida correcto del software, FoodGest opera bajo un esquema estructurado de **3 entornos independientes de ejecución**. Esto garantiza que las pruebas de nuevas características no afecten la información transaccional real ni degraden el servicio de los usuarios finales en producción.

---

## 1. Tabla Comparativa de Entornos

| Característica | Entorno de Desarrollo (Dev) | Entorno de Pruebas (Staging) | Entorno de Producción (Prod) |
| :--- | :--- | :--- | :--- |
| **Audiencia** | Desarrolladores del equipo. | Analistas de calidad (QA) y clientes de prueba. | Agricultores, compradores y transportistas reales. |
| **Base de Datos** | PostgreSQL + PostGIS local (o Docker local). | Instancia administrada aislada en la nube con semillas de prueba. | Servidor de base de datos de alta disponibilidad replicado en la nube. |
| **Datos Guardados** | Información simulada y cambiante sin valor comercial. | Datos ficticios completos y consistentes para simulación de flujos. | Datos transaccionales 100% reales, información bancaria y financiera real. |
| **Inicialización BD** | `spring.sql.init.mode=always` (Recrea datos base automáticamente). | `never` (Inicialización manual o migraciones coordinadas). | `never` (Cambios estrictos vía control de migraciones). |
| **Nivel de Logs** | `DEBUG` / `TRACE` (Muestra consultas SQL formateadas en consola). | `INFO` (Registra flujos generales, latencias e ingresos). | `WARN` / `ERROR` (Solo registra advertencias graves para optimizar rendimiento). |
| **Despliegue** | Manual por el desarrollador. | Automático al mezclar cambios exitosamente a la rama `develop`. | Manual controlado mediante Releases mezcladas a la rama `main`. |
| **Acceso a Logs** | Consola local del IDE. | Dashboard del servidor Cloud o consola de la nube de pruebas. | Agregador de logs seguro con accesos restringidos por contraseña fuerte. |

---

## 2. Diferencias Clave de Configuración

*   **Entorno de Desarrollo (Dev):** Prioriza la velocidad de diagnóstico. El motor Hibernate tiene la directiva `show-sql` activa, imprimiendo en consola de terminal cada sentencia SQL ejecutada, ideal para cazar problemas de performance o mapeos erróneos en caliente.
*   **Entorno de Pruebas (Staging):** Emula de forma exacta el comportamiento en la nube. El procesamiento de pagos de la billetera digital se realiza utilizando tokens e interfaces de prueba de la pasarela (*sandbox*), permitiendo simular transacciones financieras exitosas y fallidas sin mover capital real.
*   **Entorno de Producción (Prod):** Configurado con máxima seguridad. Las comunicaciones HTTP están protegidas de forma obligatoria mediante certificados SSL/TLS (HTTPS). Todas las conexiones a servicios externos (SUNAT, Google Maps, pasarela de pagos) apuntan a entornos de facturación reales de producción.

---

## 3. Cómo Identificar el Entorno en Ejecución

El backend de FoodGest utiliza los **Perfiles de Spring (`Spring Profiles`)** para cargar el conjunto de propiedades correspondiente al entorno. 

Durante el arranque de la aplicación, el framework imprimirá en la primera página de logs el perfil activo:
`The following 1 profile is active: "dev"`

### Formas de Cambiar el Perfil en Ejecución:

**1. Vía Variable de Entorno en el Servidor:**
```bash
export SPRING_PROFILES_ACTIVE=staging
```

**2. Vía Parámetro de Línea de Comandos al Compilar:**
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

---

## Ver también

- [Guía de Configuración Local](/configuracion/setup-local.md)
- [Listado de Variables de Entorno](/configuracion/variables-entorno.md)
- [Proceso de Despliegue y Rollback](/procesos/despliegue-rollback.md)
