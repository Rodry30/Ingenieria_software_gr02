[Inicio](/) > [APIs](/apis/endpoints-implementados.md) > Endpoints Implementados

# Catálogo de Endpoints Implementados

Este documento provee la documentación técnica de los endpoints de la API REST que ya se encuentran implementados y funcionales en el backend de FoodGest.

---

## 1. Resumen de Endpoints Disponibles

| Método | Ruta | Descripción | Autenticación | Rol Permitido |
| :--- | :--- | :--- | :--- | :--- |
| **`POST`** | `/api/auth/register` | Registro atómico de usuarios (crea cuenta + perfil + billetera). | **NO** (Público) | Cualquiera |
| **`POST`** | `/api/auth/login` | Autenticación de usuario. Retorna tokens de acceso y renovación. | **NO** (Público) | Cualquiera |
| **`POST`** | `/api/auth/refresh`| Renueva un token de acceso (Access Token) usando un Refresh Token. | **NO** (Público) | Cualquiera |

---

## 2. Detalle Técnico y Ejemplos de Petición

### 1. Registro de Usuario (`POST /api/auth/register`)
Crea de forma atómica y consistente el registro básico del usuario en la tabla `users`, su perfil correspondiente según el rol (`agricultor` o `comprador`), y asocia un monedero (`Wallet`) inicializado con saldo en cero.

#### Ejemplo de Petición (Payload para Registro de Agricultor)
```json
{
  "nombre": "Juan Pérez",
  "email": "juan.perez@agromarket.com",
  "password": "passwordSeguro123",
  "telefono": "987654321",
  "tipoUsuario": "agricultor",
  "perfil": {
    "nombreFinca": "Finca Los Cultivos",
    "hectareas": 5.5,
    "descripcion": "Chacra especializada en el cultivo de papa unica y huayro en Junin.",
    "tipoCultivoPrincipal": "Papa",
    "latitud": -12.046374,
    "longitud": -75.211122,
    "direccionParcela": "Carretera Central Km 125, Concepcion",
    "ruc": "20123456789",
    "cuentaBancaria": "191-98765432-0-99",
    "banco": "BCP"
  }
}
```

#### Ejemplo de Respuesta Correcta (`201 Created`)
```json
{
  "success": true,
  "statusCode": 201,
  "message": "Usuario registrado exitosamente. Pendiente de aprobacion.",
  "data": {
    "id": "e9b28b7a-9cf7-4f6c-9407-7f89b9d36a9a",
    "nombre": "Juan Pérez",
    "email": "juan.perez@agromarket.com",
    "tipoUsuario": "agricultor",
    "estado": "pendiente",
    "verificado": false,
    "createdAt": "2026-06-01T16:15:30"
  }
}
```

---

### 2. Login de Usuario (`POST /api/auth/login`)
Autentica al usuario en el sistema validando sus credenciales y devolviendo las llaves criptográficas de acceso stateless.

#### Ejemplo de Petición
```json
{
  "email": "juan.perez@agromarket.com",
  "password": "passwordSeguro123"
}
```

#### Ejemplo de Respuesta Correcta (`200 OK`)
```json
{
  "success": true,
  "statusCode": 200,
  "message": "Sesion iniciada correctamente.",
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqdWFuLnBlcmV6QGFncm9tYXJrZXQuY29tIiwiaWQiOiJlOWIyOGI3YS05Y2Y3LTRmNmMtOTQwNy03Zjg5YjlkMzZhOWEiLCJyb2xlIjoiYWdyaWN1bHRvciJ9...",
    "refreshToken": "d82abf71-2cfb-4e89-98ff-8a9d123e456b",
    "tipoUsuario": "agricultor"
  }
}
```

---

### 3. Renovación de Token (`POST /api/auth/refresh`)
Permite al cliente obtener un nuevo `accessToken` válido sin obligar al usuario a reintroducir sus credenciales de login, garantizando una excelente experiencia de uso móvil.

#### Ejemplo de Petición
```json
{
  "refreshToken": "d82abf71-2cfb-4e89-98ff-8a9d123e456b"
}
```

#### Ejemplo de Respuesta Correcta (`200 OK`)
```json
{
  "success": true,
  "statusCode": 200,
  "message": "Tokens renovados exitosamente.",
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqdWFuLnBlcmV6QGFncm9tYXJrZXQuY29tIiwiaWQiOiJlOWIyOGI3YS05Y2Y3LTRmNmMtOTQwNy03Zjg5YjlkMzZhOWEiLCJyb2xlIjoiYWdyaWN1bHRvciJ9...",
    "refreshToken": "a739de82-5dfc-49b0-98cc-9b8c345d98fa"
  }
}
```

---

## 3. Códigos de Error Comunes de la API

La API de FoodGest estandariza las respuestas de error en formato JSON para que el frontend pueda procesarlas e informar correctamente al usuario:

*   **`400 Bad Request` (Datos Inválidos):**
    *   *Causa:* Falta un campo obligatorio o el formato es incorrecto (ej. contraseñas de menos de 8 caracteres).
    *   *Respuesta:* `{"success": false, "statusCode": 400, "message": "El email no tiene un formato valido", "data": null}`.
*   **`409 Conflict` (Email Duplicado):**
    *   *Causa:* Se intenta registrar una cuenta utilizando un correo electrónico que ya existe en el sistema.
    *   *Respuesta:* `{"success": false, "statusCode": 409, "message": "El email ya esta registrado", "data": null}`.
*   **`401 Unauthorized` (Credenciales Incorrectas):**
    *   *Causa:* Contraseña o correo erróneos en el login, o token expirado/alterado en peticiones protegidas.
    *   *Respuesta:* `{"success": false, "statusCode": 401, "message": "Credenciales de acceso invalidas", "data": null}`.

---

## Ver también

- [Guía de Integración con Pasarela de Pagos](/apis/integracion-pagos.md)
- [ADR-04: Endpoint Transaccional de Registro](/arquitectura/adr-04-registro-unificado.md)
- [Manejo Global de Excepciones y Respuestas](/arquitectura/dominios.md#12-shared)
