INSERT INTO users (
    id,
    nombre,
    email,
    password_hash,
    tipo_usuario,
    estado,
    telefono,
    direccion,
    ciudad,
    departamento,
    codigo_postal,
    foto_perfil_url,
    verificado,
    created_at,
    updated_at
)
VALUES
    (
        uuid_generate_v4(),
        'Juan Perez',
        'juan.perez@agromarket.com',
        '$2a$10$dummyHashForTestingOnly1234567890',
        'agricultor',
        'activo',
        '987654321',
        'Av. Los Cultivos 123',
        'Lima',
        'Lima',
        '15001',
        NULL,
        true,
        NOW(),
        NOW()
    ),
    (
        uuid_generate_v4(),
        'Maria Lopez',
        'maria.lopez@agromarket.com',
        '$2a$10$dummyHashForTestingOnly0987654321',
        'comprador',
        'activo',
        '912345678',
        'Jr. Comercio 456',
        'Arequipa',
        'Arequipa',
        '04001',
        NULL,
        true,
        NOW(),
        NOW()
    )
    -- Nota: antes habia un tercer seed 'Admin Sistema' con un password_hash
    -- invalido (nadie podia loguearse con el). Se quito porque su sola
    -- presencia (tipo_usuario='admin') bloquea POST /api/auth/bootstrap-admin,
    -- que exige que no exista ningun admin todavia. El primer admin real se
    -- crea ahora con ese endpoint.
    ON CONFLICT (email) DO NOTHING;