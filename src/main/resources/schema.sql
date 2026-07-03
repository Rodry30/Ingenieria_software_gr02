-- ============================================================
--  FoodGest — Script de creación de base de datos PostgreSQL
--  Autor: FoodGest Team (corregido)
--  Fecha: 2026-05-03 (versión adaptada)
-- ============================================================

-- Extensiones necesarias
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS postgis;

-- ============================================================
-- TIPOS ENUMERADOS
-- ============================================================

CREATE TYPE IF NOT EXISTS tipo_usuario_enum      AS ENUM ('agricultor', 'comprador', 'transportista', 'admin');
CREATE TYPE IF NOT EXISTS estado_usuario_enum    AS ENUM ('activo', 'inactivo', 'suspendido', 'pendiente');
CREATE TYPE IF NOT EXISTS estado_oferta_enum     AS ENUM ('activa', 'pausada', 'cerrada', 'expirada', 'agotada');
CREATE TYPE IF NOT EXISTS calidad_enum           AS ENUM ('primera', 'segunda', 'descarte', 'exportacion');
CREATE TYPE IF NOT EXISTS estado_pedido_enum     AS ENUM ('pendiente_pago', 'pagado_por_enviar', 'en_transito', 'entregado_finalizado', 'incidencia', 'cancelado');
CREATE TYPE IF NOT EXISTS estado_negociacion_enum AS ENUM ('pendiente', 'aceptada', 'rechazada', 'expirada', 'contraoferta');
CREATE TYPE IF NOT EXISTS tipo_movimiento_enum   AS ENUM ('venta', 'comision', 'retiro', 'deposito', 'flete', 'reembolso', 'escrow_retencion', 'escrow_liberacion');
CREATE TYPE IF NOT EXISTS tipo_comprador_enum    AS ENUM ('minorista', 'mayorista', 'restaurante', 'supermercado', 'exportadora');
CREATE TYPE IF NOT EXISTS tendencia_enum         AS ENUM ('alza', 'baja', 'estable');
CREATE TYPE IF NOT EXISTS estado_subasta_enum    AS ENUM ('programada', 'activa', 'cerrada', 'cancelada');
CREATE TYPE IF NOT EXISTS frecuencia_enum        AS ENUM ('semanal', 'quincenal', 'mensual');
CREATE TYPE IF NOT EXISTS estado_suscripcion_enum AS ENUM ('activa', 'pausada', 'cancelada', 'expirada');
CREATE TYPE IF NOT EXISTS tipo_licencia_enum     AS ENUM ('A2', 'A3', 'A4', 'B2', 'C');
CREATE TYPE IF NOT EXISTS tipo_vehiculo_enum     AS ENUM ('camion', 'volquete', 'furgon', 'camioneta', 'trailer');

-- ============================================================
-- 1. USERS
-- ============================================================

CREATE TABLE users (
    id                      UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nombre                  VARCHAR(100)    NOT NULL,
    email                   VARCHAR(150)    NOT NULL UNIQUE,
    password_hash           VARCHAR(255)    NOT NULL,
    tipo_usuario            tipo_usuario_enum NOT NULL,
    estado                  estado_usuario_enum NOT NULL DEFAULT 'pendiente',
    telefono                VARCHAR(15),
    ubicacion_geo           GEOGRAPHY(POINT, 4326),
    direccion               VARCHAR(200),
    ciudad                  VARCHAR(100),
    departamento            VARCHAR(100),
    codigo_postal           VARCHAR(10),
    foto_perfil_url         VARCHAR(255),
    calificacion_promedio   DECIMAL(3,2)    DEFAULT 0.00 CHECK (calificacion_promedio >= 0 AND calificacion_promedio <= 5),
    total_calificaciones    INT             DEFAULT 0,
    verificado              BOOLEAN         DEFAULT FALSE,
    created_at              TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    ultimo_login            TIMESTAMPTZ
);

CREATE INDEX idx_users_email        ON users(email);
CREATE INDEX idx_users_tipo         ON users(tipo_usuario);
CREATE INDEX idx_users_estado       ON users(estado);

-- ============================================================
-- 2. AGRICULTORES
-- ============================================================

CREATE TABLE agricultores (
    id                          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    usuario_id                  UUID            NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    nombre_finca                VARCHAR(100),
    hectareas                   DECIMAL(10,4),
    descripcion                 TEXT,
    tipo_cultivo_principal      VARCHAR(100),
    ubicacion_parcela           GEOGRAPHY(POINT, 4326),
    direccion_parcela           VARCHAR(255),
    latitud                     DECIMAL(9,6),
    longitud                    DECIMAL(9,6),
    ruc                         VARCHAR(15),
    cuenta_bancaria             VARCHAR(50),
    banco                       VARCHAR(50),
    created_at                  TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_agricultores_usuario ON agricultores(usuario_id);
CREATE INDEX idx_agricultores_geo     ON agricultores USING GIST(ubicacion_parcela);

-- ============================================================
-- 3. COMPRADORES
-- ============================================================

CREATE TABLE compradores (
    id                          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    usuario_id                  UUID            NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    tipo_comprador              tipo_comprador_enum NOT NULL DEFAULT 'minorista',
    razon_social                VARCHAR(100),
    ruc                         VARCHAR(15),
    direccion_entrega_default   VARCHAR(255),
    latitud_entrega             DECIMAL(9,6),
    longitud_entrega            DECIMAL(9,6),
    limite_credito              DECIMAL(12,2)   DEFAULT 0.00,
    created_at                  TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_compradores_usuario ON compradores(usuario_id);

-- ============================================================
-- 4. TRANSPORTISTAS
-- ============================================================

CREATE TABLE transportistas (
    id                      UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    usuario_id              UUID            NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    nombre_completo         VARCHAR(100)    NOT NULL,
    dni                     VARCHAR(15)     NOT NULL,
    licencia_conducir       VARCHAR(20)     NOT NULL,
    tipo_licencia           tipo_licencia_enum,
    placa_vehiculo          VARCHAR(10)     NOT NULL,
    tipo_vehiculo           tipo_vehiculo_enum,
    marca_vehiculo          VARCHAR(50),
    modelo_vehiculo         VARCHAR(50),
    capacidad_toneladas     DECIMAL(8,2),
    foto_vehiculo_url       VARCHAR(255),
    verificado              BOOLEAN         DEFAULT FALSE,
    calificacion_promedio   DECIMAL(3,2)    DEFAULT 0.00 CHECK (calificacion_promedio >= 0 AND calificacion_promedio <= 5),
    total_calificaciones    INT             DEFAULT 0,
    disponible              BOOLEAN         DEFAULT TRUE,
    latitud_actual          DECIMAL(9,6),
    longitud_actual         DECIMAL(9,6),
    created_at              TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_transportistas_usuario    ON transportistas(usuario_id);
CREATE INDEX idx_transportistas_disponible ON transportistas(disponible);
CREATE INDEX idx_transportistas_placa      ON transportistas(placa_vehiculo);

-- ============================================================
-- 5. WALLETS
-- ============================================================

CREATE TABLE wallets (
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    usuario_id          UUID            NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    saldo_disponible    DECIMAL(12,2)   NOT NULL DEFAULT 0.00 CHECK (saldo_disponible >= 0),
    saldo_retenido      DECIMAL(12,2)   NOT NULL DEFAULT 0.00 CHECK (saldo_retenido >= 0),
    saldo_total         DECIMAL(12,2)   GENERATED ALWAYS AS (saldo_disponible + saldo_retenido) STORED,
    moneda              VARCHAR(5)      NOT NULL DEFAULT 'PEN',
    updated_at          TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_wallets_usuario ON wallets(usuario_id);

-- ============================================================
-- 6. REFRESH_TOKENS
-- ============================================================

CREATE TABLE refresh_tokens (
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    usuario_id  UUID        NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    token       VARCHAR(120) NOT NULL UNIQUE,
    expires_at  TIMESTAMPTZ NOT NULL,
    revoked     BOOLEAN     NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_refresh_tokens_usuario ON refresh_tokens(usuario_id);
CREATE INDEX idx_refresh_tokens_token   ON refresh_tokens(token);
CREATE INDEX idx_refresh_tokens_valid   ON refresh_tokens(revoked, expires_at);

-- ============================================================
-- 7. CATEGORIAS
-- ============================================================

CREATE TABLE categorias (
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nombre      VARCHAR(100)    NOT NULL UNIQUE,
    descripcion VARCHAR(200),
    imagen_url  VARCHAR(255),
    activo      BOOLEAN         DEFAULT TRUE,
    orden       INT             DEFAULT 0
);

-- ============================================================
-- 7. PRODUCTOS
-- ============================================================

CREATE TABLE productos (
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    categoria_id        UUID            REFERENCES categorias(id) ON DELETE SET NULL,
    nombre              VARCHAR(200)    NOT NULL,
    descripcion         TEXT,
    unidad_medida_default VARCHAR(50),
    imagen_url          VARCHAR(255)
);

CREATE INDEX idx_productos_categoria ON productos(categoria_id);
CREATE INDEX idx_productos_nombre    ON productos(nombre);

-- ============================================================
-- 8. FOTOS_PRODUCTO
-- ============================================================

CREATE TABLE fotos_producto (
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    producto_id UUID            NOT NULL REFERENCES productos(id) ON DELETE CASCADE,
    url         VARCHAR(255)    NOT NULL,
    es_principal BOOLEAN        DEFAULT FALSE,
    orden       INT             DEFAULT 0,
    created_at  TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_fotos_producto ON fotos_producto(producto_id);

-- ============================================================
-- 9. OFERTAS
-- ============================================================

CREATE TABLE ofertas (
    id                          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    agricultor_id               UUID            NOT NULL REFERENCES agricultores(id) ON DELETE CASCADE,
    producto_id                 UUID            NOT NULL REFERENCES productos(id),
    variedad                    VARCHAR(100),
    calidad                     calidad_enum    DEFAULT 'primera',
    cantidad_disponible         DECIMAL(10,2)   NOT NULL CHECK (cantidad_disponible > 0),
    unidad_medida               VARCHAR(30)     NOT NULL DEFAULT 'kg',
    precio_sugerido             DECIMAL(10,2)   NOT NULL CHECK (precio_sugerido > 0),
    moneda                      VARCHAR(5)      NOT NULL DEFAULT 'PEN',
    latitud                     DECIMAL(9,6),
    longitud                    DECIMAL(9,6),
    ubicacion_oferta            GEOGRAPHY(POINT, 4326),
    direccion_referencia        VARCHAR(255),
    radio_entrega_km            INT             DEFAULT 50,
    condicion_entrega           VARCHAR(30)     DEFAULT 'en_chacra',
    estado                      estado_oferta_enum NOT NULL DEFAULT 'activa',
    fecha_cosecha               DATE,
    fecha_expiracion            TIMESTAMPTZ,
    acepta_negociacion          BOOLEAN         DEFAULT TRUE,
    precio_minimo               DECIMAL(10,2),
    tiene_senasa                BOOLEAN         DEFAULT FALSE,
    numero_cert_senasa          VARCHAR(50),
    vencimiento_cert_senasa     DATE,
    tiene_globalgap             BOOLEAN         DEFAULT FALSE,
    numero_cert_globalgap       VARCHAR(50),
    vencimiento_globalgap       DATE,
    es_organico                 BOOLEAN         DEFAULT FALSE,
    numero_cert_organico        VARCHAR(50),
    vencimiento_cert_organico   DATE,
    fotos_urls                  JSONB           DEFAULT '[]',
    vistas                      INT             DEFAULT 0,
    destacada                   BOOLEAN         DEFAULT FALSE,
    created_at                  TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at                  TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_ofertas_agricultor  ON ofertas(agricultor_id);
CREATE INDEX idx_ofertas_producto    ON ofertas(producto_id);
CREATE INDEX idx_ofertas_estado      ON ofertas(estado);
CREATE INDEX idx_ofertas_fecha_exp   ON ofertas(fecha_expiracion);
CREATE INDEX idx_ofertas_geo         ON ofertas USING GIST(ubicacion_oferta);

-- ============================================================
-- 10. PRECIOS_MERCADO
-- ============================================================

CREATE TABLE precios_mercado (
    id                      UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    producto_id             UUID            NOT NULL REFERENCES productos(id) ON DELETE CASCADE,
    fuente                  VARCHAR(20)     NOT NULL,
    precio_min              DECIMAL(10,2),
    precio_max              DECIMAL(10,2),
    precio_promedio         DECIMAL(10,2),
    variacion_porcentaje    DECIMAL(5,2),
    tendencia               tendencia_enum,
    fecha_precio            DATE            NOT NULL,
    created_at              TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_precios_producto   ON precios_mercado(producto_id);
CREATE INDEX idx_precios_fecha      ON precios_mercado(fecha_precio DESC);
CREATE UNIQUE INDEX idx_precios_uniq ON precios_mercado(producto_id, fuente, fecha_precio);

CREATE TABLE IF NOT EXISTS refresh_tokens (
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    usuario_id  UUID         NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    token       VARCHAR(120) NOT NULL UNIQUE,
    expires_at  TIMESTAMPTZ  NOT NULL,
    revoked     BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_refresh_tokens_usuario ON refresh_tokens(usuario_id);
CREATE INDEX IF NOT EXISTS idx_refresh_tokens_token   ON refresh_tokens(token);
CREATE INDEX IF NOT EXISTS idx_refresh_tokens_valid   ON refresh_tokens(revoked, expires_at);

-- ============================================================
-- 11. NEGOCIACIONES
-- ============================================================

CREATE TABLE negociaciones (
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    oferta_id           UUID            NOT NULL REFERENCES ofertas(id) ON DELETE CASCADE,
    comprador_id        UUID            NOT NULL REFERENCES compradores(id),
    precio_propuesto    DECIMAL(10,2)   NOT NULL,
    cantidad_propuesta  DECIMAL(10,2)   NOT NULL,
    precio_acordado     DECIMAL(10,2),
    cantidad_acordada   DECIMAL(10,2),
    estado              estado_negociacion_enum NOT NULL DEFAULT 'pendiente',
    mensaje_inicial     TEXT,
    fecha_respuesta     TIMESTAMPTZ,
    created_at          TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_negociaciones_oferta    ON negociaciones(oferta_id);
CREATE INDEX idx_negociaciones_comprador ON negociaciones(comprador_id);
CREATE INDEX idx_negociaciones_estado    ON negociaciones(estado);

-- ============================================================
-- 12. PEDIDOS
-- ============================================================

CREATE TABLE pedidos (
    id                          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    oferta_id                   UUID            NOT NULL REFERENCES ofertas(id),
    comprador_id                UUID            NOT NULL REFERENCES compradores(id),
    transportista_id            UUID            REFERENCES transportistas(id),
    estado                      estado_pedido_enum NOT NULL DEFAULT 'pendiente_pago',
    precio_acordado             DECIMAL(10,2)   NOT NULL,
    cantidad_acordada           DECIMAL(10,2)   NOT NULL,
    subtotal                    DECIMAL(12,2)   NOT NULL,
    costo_envio                 DECIMAL(12,2)   DEFAULT 0.00,
    comision_plataforma         DECIMAL(12,2)   DEFAULT 0.00,
    total                       DECIMAL(12,2)   NOT NULL,
    moneda                      VARCHAR(5)      NOT NULL DEFAULT 'PEN',
    metodo_pago                 VARCHAR(30),
    origen_direccion            VARCHAR(255),
    origen_latitud              DECIMAL(9,6),
    origen_longitud             DECIMAL(9,6),
    destino_nombre              VARCHAR(100),
    direccion_entrega           TEXT,
    latitud_entrega             DECIMAL(9,6),
    longitud_entrega            DECIMAL(9,6),
    guia_remision_url           VARCHAR(255),
    guia_remision_numero        VARCHAR(50),
    foto_entrega_url            VARCHAR(255),
    codigo_seguimiento          VARCHAR(30)     UNIQUE,
    fecha_pedido                TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    fecha_confirmacion          TIMESTAMPTZ,
    fecha_entrega_estimada      TIMESTAMPTZ,
    fecha_entrega_real          TIMESTAMPTZ,
    notas_comprador             TEXT,
    created_at                  TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at                  TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_pedidos_oferta         ON pedidos(oferta_id);
CREATE INDEX idx_pedidos_comprador      ON pedidos(comprador_id);
CREATE INDEX idx_pedidos_transportista  ON pedidos(transportista_id);
CREATE INDEX idx_pedidos_estado         ON pedidos(estado);
CREATE INDEX idx_pedidos_codigo         ON pedidos(codigo_seguimiento);
CREATE INDEX idx_pedidos_fecha          ON pedidos(fecha_pedido DESC);

-- ============================================================
-- 13. DETALLE_PEDIDOS
-- ============================================================

CREATE TABLE detalle_pedidos (
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    pedido_id           UUID            NOT NULL REFERENCES pedidos(id) ON DELETE CASCADE,
    producto_id         UUID            NOT NULL REFERENCES productos(id),
    agricultor_id       UUID            NOT NULL REFERENCES agricultores(id),
    cantidad            DECIMAL(10,2)   NOT NULL,
    precio_unitario     DECIMAL(10,2)   NOT NULL,
    subtotal            DECIMAL(12,2)   NOT NULL,
    estado_agricultor   VARCHAR(30)     DEFAULT 'pendiente',
    notas               TEXT
);

CREATE INDEX idx_detalle_pedido      ON detalle_pedidos(pedido_id);
CREATE INDEX idx_detalle_producto    ON detalle_pedidos(producto_id);
CREATE INDEX idx_detalle_agricultor  ON detalle_pedidos(agricultor_id);

-- ============================================================
-- 14. TRACKING_PEDIDO
-- ============================================================

CREATE TABLE tracking_pedido (
    id                      UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    pedido_id               UUID            NOT NULL REFERENCES pedidos(id) ON DELETE CASCADE,
    transportista_id        UUID            NOT NULL REFERENCES transportistas(id),
    latitud                 DECIMAL(9,6)    NOT NULL,
    longitud                DECIMAL(9,6)    NOT NULL,
    ubicacion               GEOGRAPHY(POINT, 4326),
    velocidad_kmh           DECIMAL(5,2),
    distancia_restante_km   DECIMAL(8,2),
    eta                     TIMESTAMPTZ,
    descripcion             VARCHAR(100),
    created_at              TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_tracking_pedido        ON tracking_pedido(pedido_id);
CREATE INDEX idx_tracking_transportista ON tracking_pedido(transportista_id);
CREATE INDEX idx_tracking_created       ON tracking_pedido(created_at DESC);
CREATE INDEX idx_tracking_geo           ON tracking_pedido USING GIST(ubicacion);

-- ============================================================
-- 15. TRANSACCIONES
-- ============================================================

CREATE TABLE transacciones (
    id                      UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    pedido_id               UUID            NOT NULL REFERENCES pedidos(id),
    tipo                    VARCHAR(30)     NOT NULL,
    monto                   DECIMAL(12,2)   NOT NULL,
    moneda                  VARCHAR(5)      NOT NULL DEFAULT 'PEN',
    estado                  VARCHAR(30)     NOT NULL DEFAULT 'pendiente',
    pasarela_pago           VARCHAR(50),
    referencia_externa      VARCHAR(100),
    codigo_autorizacion     VARCHAR(100),
    descripcion             TEXT,
    metadata                JSONB           DEFAULT '{}',
    fecha_transaccion       TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    created_at              TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_transacciones_pedido     ON transacciones(pedido_id);
CREATE INDEX idx_transacciones_estado     ON transacciones(estado);
CREATE INDEX idx_transacciones_referencia ON transacciones(referencia_externa);

-- ============================================================
-- 16. MOVIMIENTOS_WALLET
-- ============================================================

CREATE TABLE movimientos_wallet (
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    wallet_id           UUID            NOT NULL REFERENCES wallets(id) ON DELETE CASCADE,
    pedido_id           UUID            REFERENCES pedidos(id),
    tipo                tipo_movimiento_enum NOT NULL,
    monto               DECIMAL(12,2)   NOT NULL,
    saldo_anterior      DECIMAL(12,2)   NOT NULL,
    saldo_posterior     DECIMAL(12,2)   NOT NULL,
    descripcion         VARCHAR(200),
    created_at          TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_movimientos_wallet  ON movimientos_wallet(wallet_id);
CREATE INDEX idx_movimientos_pedido  ON movimientos_wallet(pedido_id);
CREATE INDEX idx_movimientos_tipo    ON movimientos_wallet(tipo);
CREATE INDEX idx_movimientos_created ON movimientos_wallet(created_at DESC);

-- ============================================================
-- 17. CALIFICACIONES
-- ============================================================

CREATE TABLE calificaciones (
    id              UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    pedido_id       UUID            NOT NULL REFERENCES pedidos(id),
    calificador_id  UUID            NOT NULL REFERENCES users(id),
    calificado_id   UUID            NOT NULL REFERENCES users(id),
    puntuacion      INT             NOT NULL CHECK (puntuacion >= 1 AND puntuacion <= 5),
    comentario      TEXT,
    tipo            VARCHAR(30)     NOT NULL,
    verificado      BOOLEAN         DEFAULT TRUE,
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_calificacion_pedido_calificador UNIQUE (pedido_id, calificador_id)
);

CREATE INDEX idx_calificaciones_pedido      ON calificaciones(pedido_id);
CREATE INDEX idx_calificaciones_calificado  ON calificaciones(calificado_id);
CREATE INDEX idx_calificaciones_calificador ON calificaciones(calificador_id);

-- ============================================================
-- 18. MENSAJES
-- ============================================================

CREATE TABLE mensajes (
    id              UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    remitente_id    UUID            NOT NULL REFERENCES users(id),
    destinatario_id UUID            REFERENCES users(id),
    oferta_id       UUID            REFERENCES ofertas(id) ON DELETE SET NULL,
    pedido_id       UUID            REFERENCES pedidos(id) ON DELETE SET NULL,
    producto_id     UUID            REFERENCES productos(id) ON DELETE SET NULL,
    contenido       TEXT,
    tipo_archivo    VARCHAR(20),
    es_grupo        BOOLEAN         DEFAULT FALSE,
    nombre_grupo    VARCHAR(100),
    leido           BOOLEAN         DEFAULT FALSE,
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_mensajes_remitente    ON mensajes(remitente_id);
CREATE INDEX idx_mensajes_destinatario ON mensajes(destinatario_id);
CREATE INDEX idx_mensajes_oferta       ON mensajes(oferta_id);
CREATE INDEX idx_mensajes_pedido       ON mensajes(pedido_id);
CREATE INDEX idx_mensajes_created      ON mensajes(created_at DESC);

-- ============================================================
-- 19. NOTIFICACIONES
-- ============================================================

CREATE TABLE notificaciones (
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    usuario_id  UUID            NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    tipo        VARCHAR(50)     NOT NULL,
    titulo      VARCHAR(200)    NOT NULL,
    mensaje     TEXT,
    leido       BOOLEAN         DEFAULT FALSE,
    data        JSONB           DEFAULT '{}',
    created_at  TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_notificaciones_usuario ON notificaciones(usuario_id);
CREATE INDEX idx_notificaciones_leido   ON notificaciones(leido);
CREATE INDEX idx_notificaciones_created ON notificaciones(created_at DESC);

-- ============================================================
-- 20. SUBASTAS
-- ============================================================

CREATE TABLE subastas (
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    producto_id         UUID            NOT NULL REFERENCES productos(id),
    agricultor_id       UUID            NOT NULL REFERENCES agricultores(id),
    cantidad_lote       DECIMAL(10,2)   NOT NULL,
    precio_base         DECIMAL(12,2)   NOT NULL,
    precio_actual       DECIMAL(12,2)   NOT NULL,
    incremento_minimo   DECIMAL(12,2)   NOT NULL DEFAULT 10.00,
    fecha_inicio        TIMESTAMPTZ     NOT NULL,
    fecha_fin           TIMESTAMPTZ     NOT NULL,
    estado              estado_subasta_enum NOT NULL DEFAULT 'programada',
    ganador_id          UUID            REFERENCES compradores(id),
    created_at          TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_subasta_fechas CHECK (fecha_fin > fecha_inicio),
    CONSTRAINT chk_precio_actual CHECK (precio_actual >= precio_base)
);

CREATE INDEX idx_subastas_agricultor ON subastas(agricultor_id);
CREATE INDEX idx_subastas_producto   ON subastas(producto_id);
CREATE INDEX idx_subastas_estado     ON subastas(estado);
CREATE INDEX idx_subastas_fecha_fin  ON subastas(fecha_fin);

-- ============================================================
-- 21. PUJAS_SUBASTA
-- ============================================================

CREATE TABLE pujas_subasta (
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    subasta_id  UUID            NOT NULL REFERENCES subastas(id) ON DELETE CASCADE,
    comprador_id UUID           NOT NULL REFERENCES compradores(id),
    monto_puja  DECIMAL(12,2)   NOT NULL,
    created_at  TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_pujas_subasta   ON pujas_subasta(subasta_id);
CREATE INDEX idx_pujas_comprador ON pujas_subasta(comprador_id);

-- ============================================================
-- 22. SUSCRIPCIONES
-- ============================================================

CREATE TABLE suscripciones (
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    comprador_id        UUID            NOT NULL REFERENCES compradores(id),
    agricultor_id       UUID            NOT NULL REFERENCES agricultores(id),
    producto_id         UUID            NOT NULL REFERENCES productos(id),
    cantidad_periodica  DECIMAL(10,2)   NOT NULL,
    frecuencia          frecuencia_enum NOT NULL DEFAULT 'mensual',
    estado              estado_suscripcion_enum NOT NULL DEFAULT 'activa',
    fecha_inicio        DATE            NOT NULL,
    fecha_fin           DATE,
    created_at          TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_suscripcion_fechas CHECK (fecha_fin IS NULL OR fecha_fin > fecha_inicio)
);

CREATE INDEX idx_suscripciones_comprador  ON suscripciones(comprador_id);
CREATE INDEX idx_suscripciones_agricultor ON suscripciones(agricultor_id);
CREATE INDEX idx_suscripciones_producto   ON suscripciones(producto_id);
CREATE INDEX idx_suscripciones_estado     ON suscripciones(estado);

-- ============================================================
-- PRECIOS ESCALONADOS
-- ============================================================

CREATE TABLE precios_escalonados (
    id              UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    oferta_id       UUID            NOT NULL REFERENCES ofertas(id) ON DELETE CASCADE,
    cantidad_desde  DECIMAL(10,2)   NOT NULL,
    cantidad_hasta  DECIMAL(10,2),
    precio_unitario DECIMAL(10,2)   NOT NULL,
    moneda          VARCHAR(5)      NOT NULL DEFAULT 'PEN',
    CONSTRAINT chk_precio_escalonado CHECK (cantidad_hasta IS NULL OR cantidad_hasta > cantidad_desde)
);

CREATE INDEX idx_precios_esc_oferta ON precios_escalonados(oferta_id);

-- ============================================================
-- TRIGGERS: updated_at automático (función)
-- ============================================================

CREATE OR REPLACE FUNCTION trigger_set_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Triggers que usan trigger_set_updated_at se crean después de las tablas correspondientes

-- ============================================================
-- TRIGGERS: actualizar calificacion_promedio (función)
-- ============================================================

CREATE OR REPLACE FUNCTION actualizar_calificacion_promedio()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE users
    SET
        calificacion_promedio = (
            SELECT ROUND(AVG(puntuacion)::NUMERIC, 2)
            FROM calificaciones
            WHERE calificado_id = NEW.calificado_id
        ),
        total_calificaciones = (
            SELECT COUNT(*)
            FROM calificaciones
            WHERE calificado_id = NEW.calificado_id
        )
    WHERE id = NEW.calificado_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- ============================================================
-- TRIGGERS: actualizar saldo_total en wallet (función)
-- ============================================================

CREATE OR REPLACE FUNCTION actualizar_wallet_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- ============================================================
-- TRIGGER: crear wallet automáticamente al crear un usuario (función)
-- ============================================================

CREATE OR REPLACE FUNCTION crear_wallet_usuario()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO wallets (usuario_id, saldo_disponible, saldo_retenido, moneda)
    VALUES (NEW.id, 0.00, 0.00, 'PEN');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- ============================================================
-- TRIGGER: incrementar vistas de oferta (función)
-- ============================================================

CREATE OR REPLACE FUNCTION incrementar_vistas_oferta()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE ofertas SET vistas = vistas + 1 WHERE id = NEW.oferta_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- ============================================================
-- TABLA: registro de vistas de oferta (para disparar el trigger)
-- ============================================================

CREATE TABLE oferta_vistas (
    id        UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    oferta_id UUID NOT NULL REFERENCES ofertas(id) ON DELETE CASCADE,
    usuario_id UUID,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_oferta_vistas_oferta ON oferta_vistas(oferta_id);

-- Crear trigger para incrementar vistas cuando se inserta en oferta_vistas
CREATE TRIGGER trg_incrementar_vistas AFTER INSERT ON oferta_vistas FOR EACH ROW EXECUTE FUNCTION incrementar_vistas_oferta();

-- ============================================================
-- TRIGGER: crear wallet automáticamente al crear un usuario
-- (se crea ahora que 'wallets' y 'users' existen)
-- ============================================================

CREATE TRIGGER trg_crear_wallet
    AFTER INSERT ON users
    FOR EACH ROW EXECUTE FUNCTION crear_wallet_usuario();

-- ============================================================
-- Triggers updated_at en tablas específicas (se crean después)
-- ============================================================

CREATE TRIGGER set_updated_at_users
    BEFORE UPDATE ON users
    FOR EACH ROW EXECUTE FUNCTION trigger_set_updated_at();

CREATE TRIGGER set_updated_at_pedidos
    BEFORE UPDATE ON pedidos
    FOR EACH ROW EXECUTE FUNCTION trigger_set_updated_at();

CREATE TRIGGER set_updated_at_ofertas
    BEFORE UPDATE ON ofertas
    FOR EACH ROW EXECUTE FUNCTION trigger_set_updated_at();

CREATE TRIGGER set_updated_at_wallets
    BEFORE UPDATE ON wallets
    FOR EACH ROW EXECUTE FUNCTION actualizar_wallet_updated_at();

-- ============================================================
-- TRIGGER: actualizar calificacion_promedio (se crea después de la tabla calificaciones)
-- ============================================================

CREATE TRIGGER trg_actualizar_calificacion
    AFTER INSERT ON calificaciones
    FOR EACH ROW EXECUTE FUNCTION actualizar_calificacion_promedio();

-- ============================================================
-- VISTAS ÚTILES
-- ============================================================

CREATE VIEW v_ofertas_activas AS
SELECT
    o.id,
    o.precio_sugerido,
    o.cantidad_disponible,
    o.unidad_medida,
    o.variedad,
    o.calidad,
    o.estado,
    o.fecha_cosecha,
    o.latitud,
    o.longitud,
    o.tiene_senasa,
    o.tiene_globalgap,
    o.es_organico,
    o.acepta_negociacion,
    o.destacada,
    o.vistas,
    o.created_at,
    p.nombre           AS producto_nombre,
    c.nombre           AS categoria_nombre,
    u.nombre           AS agricultor_nombre,
    u.calificacion_promedio AS agricultor_calificacion,
    u.foto_perfil_url  AS agricultor_foto,
    a.nombre_finca,
    a.direccion_parcela
FROM ofertas o
JOIN productos  p ON o.producto_id   = p.id
JOIN categorias c ON p.categoria_id  = c.id
JOIN agricultores a ON o.agricultor_id = a.id
JOIN users      u ON a.usuario_id    = u.id
WHERE o.estado = 'activa'
  AND (o.fecha_expiracion IS NULL OR o.fecha_expiracion > NOW());

CREATE VIEW v_pedidos_resumen AS
SELECT
    ped.id,
    ped.codigo_seguimiento,
    ped.estado,
    ped.total,
    ped.moneda,
    ped.fecha_pedido,
    ped.fecha_entrega_estimada,
    ped.fecha_entrega_real,
    uc.nombre   AS comprador_nombre,
    ua.nombre   AS agricultor_nombre,
    ut.nombre   AS transportista_nombre,
    p.nombre    AS producto_nombre,
    o.variedad
FROM pedidos ped
JOIN compradores  comp ON ped.comprador_id   = comp.id
JOIN users        uc   ON comp.usuario_id    = uc.id
JOIN ofertas      o    ON ped.oferta_id      = o.id
JOIN productos    p    ON o.producto_id      = p.id
JOIN agricultores ag   ON o.agricultor_id    = ag.id
JOIN users        ua   ON ag.usuario_id      = ua.id
LEFT JOIN transportistas tr ON ped.transportista_id = tr.id
LEFT JOIN users           ut ON tr.usuario_id       = ut.id;

-- ============================================================
-- FIN DEL SCRIPT
-- ============================================================
