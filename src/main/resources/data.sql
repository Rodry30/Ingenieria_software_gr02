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
<<<<<<< HEAD
    codigo_postal,
    foto_perfil_url,
=======
>>>>>>> origin/rodrigo
    verificado,
    created_at,
    updated_at
)
VALUES
(
    uuid_generate_v4(),
<<<<<<< HEAD
    'Juan',
=======
    'Juan Perez',
>>>>>>> origin/rodrigo
    'juan.perez@agromarket.com',
    '$2a$10$dummyHashForTestingOnly1234567890',
    'agricultor',
    'activo',
    '987654321',
    'Av. Los Cultivos 123',
    'Lima',
    'Lima',
<<<<<<< HEAD
    '15001',
    NULL,
=======
>>>>>>> origin/rodrigo
    true,
    NOW(),
    NOW()
),
(
    uuid_generate_v4(),
<<<<<<< HEAD
    'Maria',
=======
    'Maria Lopez',
>>>>>>> origin/rodrigo
    'maria.lopez@agromarket.com',
    '$2a$10$dummyHashForTestingOnly0987654321',
    'comprador',
    'activo',
    '912345678',
    'Jr. Comercio 456',
    'Arequipa',
    'Arequipa',
<<<<<<< HEAD
    '04001',
    NULL,
=======
>>>>>>> origin/rodrigo
    true,
    NOW(),
    NOW()
),
(
    uuid_generate_v4(),
<<<<<<< HEAD
    'Admin',
=======
    'Admin Sistema',
>>>>>>> origin/rodrigo
    'admin@agromarket.com',
    '$2a$10$dummyHashForTestingOnly1122334455',
    'admin',
    'activo',
    '900000000',
    'Centro Empresarial',
    'Cusco',
    'Cusco',
<<<<<<< HEAD
    '08001',
    NULL,
=======
>>>>>>> origin/rodrigo
    true,
    NOW(),
    NOW()
)
ON CONFLICT (email) DO NOTHING;
