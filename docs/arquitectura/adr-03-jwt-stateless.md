[Inicio](/) > [Arquitectura](/arquitectura/README.md) > [ADRs](/arquitectura/README.md#4-principios-de-diseno-adoptados) > ADR-03

# ADR-03: Autenticación basada en JSON Web Tokens (JWT) Stateless

* **ID:** ADR-03
* **Título:** JWT Stateless sin Sesión de Servidor
* **Estado:** Aprobado
* **Fecha:** 2026-06-01
* **Autor:** Equipo de Seguridad y Backend

---

## Contexto

FoodGest es concebido desde su origen para operar primordialmente a través de aplicaciones móviles (Angular/Capacitor) utilizadas por agricultores en campo y transportistas en ruta, donde la conectividad a internet puede ser inestable y de baja velocidad.

Mantener sesiones tradicionales del lado del servidor (`HttpSession` / Cookies de sesión):
1. Añade sobrecarga de memoria RAM en el servidor, almacenando estados de sesión por miles de usuarios concurrentes.
2. Dificulta el escalamiento horizontal (requiere balanceadores con sesiones pegajosas o bases de datos de sesión tipo Redis).
3. No es natural ni óptimo para el desarrollo de clientes nativos móviles, que a menudo carecen de soporte nativo simple para cookies persistentes.

---

## Decisión

Se decide implementar un mecanismo de autenticación **Stateless** (sin estado) utilizando **JSON Web Tokens (JWT)**.

El flujo de autenticación opera de la siguiente manera:
1. El usuario envía sus credenciales al endpoint `POST /api/auth/login`.
2. El servidor valida las credenciales contra la base de datos PostgreSQL, genera una firma digital criptográfica HS256 y retorna un DTO con un **Access Token** de corta duración (ej. 15 minutos) y un **Refresh Token** de larga duración (ej. 7 días).
3. El cliente almacena estos tokens de forma segura (ej. Secure Storage en móvil, LocalStorage/Memory en Web).
4. Para cada petición subsecuente a endpoints protegidos, el cliente adjunta el Access Token en el encabezado `Authorization: Bearer <TOKEN>`.
5. El servidor intercepta la petición, valida la firma del token criptográficamente y extrae la identidad y roles del usuario en memoria de forma instantánea sin necesidad de consultar bases de datos en cada petición.

El firmado se realiza con algoritmos HMAC-SHA256, empleando una clave secreta fuerte inyectada al servidor desde variables de entorno seguras (`JWT_SECRET`).

---

## Consecuencias

### Positivas (+)
* **Escalabilidad Infinita:** Al ser stateless, el backend no retiene ningún estado en memoria RAM. Es posible apagar y encender servidores en la nube de forma transparente sin interrumpir las sesiones de los usuarios.
* **Compatibilidad Móvil Nativa:** El uso de cabeceras `Authorization` es el estándar indiscutible para aplicaciones móviles multiplataforma.
* **Reducción de Peticiones a Base de Datos:** Los datos básicos del usuario (ID, email y rol) viajan encriptados dentro del *payload* (claims) del token, reduciendo las consultas repetitivas de verificación de identidad.

### Negativas (-)
* **Dificultad de Revocación:** Una vez emitido un Access Token, este es válido hasta que expire, a menos que se implemente una lista negra en caché (Redis), lo que reintroduciría estado al sistema.
* **Mayor Tamaño de Petición:** Los tokens JWT añaden unos cientos de bytes de sobrecarga a los encabezados de cada petición HTTP en comparación con simples identificadores de sesión opacos.

---

## Alternativas Consideradas

* **Sesiones basadas en Cookies / HttpSession:** Descartada por la nula escalabilidad horizontal nativa y por la fricción que introduce en integraciones móviles híbridas.
* **OAuth 2.0 / OpenID Connect completo (con Keycloak u Okta):** Aunque es extremadamente robusto, añade una sobrecarga técnica y costos de infraestructura inicial que no se justifican para la escala inicial del proyecto FoodGest. La implementación personalizada simplificada en Spring Boot cumple perfectamente con los requerimientos actuales del negocio.

---

## Ver también

- [Configuración de Jwt Token Provider](/apis/endpoints-implementados.md#1-autenticación)
- [Listado de Endpoints Implementados](/apis/endpoints-implementados.md)
