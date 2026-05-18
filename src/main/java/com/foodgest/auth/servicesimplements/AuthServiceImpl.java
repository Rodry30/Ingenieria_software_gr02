package com.foodgest.auth.servicesimplements;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodgest.auth.dtos.RegisterRequestDto;
import com.foodgest.auth.servicesinterfaces.IAuthService;
import com.foodgest.perfiles.agricultores.dtos.AgricultorCreateDto;
import com.foodgest.perfiles.agricultores.entities.Agricultor;
import com.foodgest.perfiles.agricultores.repositories.AgricultorRepository;
import com.foodgest.perfiles.compradores.dtos.CompradorCreateDto;
import com.foodgest.perfiles.compradores.entities.Comprador;
import com.foodgest.perfiles.compradores.repositories.CompradorRepository;
import com.foodgest.shared.exceptions.BusinessException;
import com.foodgest.users.entities.UserEntities;
import com.foodgest.users.entities.Wallet;
import com.foodgest.users.enums.TipoUsuarioEnum;
import com.foodgest.users.repositories.UserRepository;
import com.foodgest.users.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements IAuthService {

    // MEJORA: BCryptPasswordEncoder se instancia con strength 12 como bean local.
    // Idealmente deberia estar en una clase @Configuration para reutilizarlo,
    // pero si no existe aun esa clase de config, lo creamos aqui inline.
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AgricultorRepository agricultorRepository;

    @Autowired
    private CompradorRepository compradorRepository;

    @Autowired
    private WalletRepository walletRepository;

    // MEJORA: ObjectMapper inyectado como bean (configurado por Spring Boot auto-config)
    // para garantizar que comparte la misma configuracion global (fechas, modulos, etc.)
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional
    public UserEntities register(RegisterRequestDto dto) {

        // Regla 6: solo se permite agricultor o comprador en este endpoint
        if (dto.getTipoUsuario() != TipoUsuarioEnum.agricultor
                && dto.getTipoUsuario() != TipoUsuarioEnum.comprador) {
            throw new BusinessException(
                    "Tipo de usuario no permitido en este endpoint",
                    HttpStatus.BAD_REQUEST);
        }

        // Regla 1: email unico
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException(
                    "El email ya esta registrado",
                    HttpStatus.CONFLICT);
        }

        // ── 1. Crear y persistir el usuario ──────────────────────────────────
        UserEntities usuario = new UserEntities();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        // Regla 2: hash BCrypt strength 12
        usuario.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        usuario.setTelefono(dto.getTelefono());
        // Regla 3: estado = 'pendiente', verificado = false
        usuario.setTipoUsuario(dto.getTipoUsuario().name());
        usuario.setEstado("pendiente");
        usuario.setVerificado(false);

        userRepository.save(usuario);

        // ── 2. Crear perfil especifico ────────────────────────────────────────
        if (dto.getTipoUsuario() == TipoUsuarioEnum.agricultor) {
            // Regla 4: convertir el Map del campo 'perfil' al DTO concreto
            AgricultorCreateDto perfilDto = objectMapper.convertValue(
                    dto.getPerfil(), AgricultorCreateDto.class);
            Agricultor agricultor = perfilDto.toEntity(usuario);
            agricultorRepository.save(agricultor);

        } else {
            CompradorCreateDto perfilDto = objectMapper.convertValue(
                    dto.getPerfil(), CompradorCreateDto.class);
            Comprador comprador = perfilDto.toEntity(usuario);
            compradorRepository.save(comprador);
        }

        // ── 3. Crear wallet inicial ───────────────────────────────────────────
        // Regla 5: saldo_disponible = 0, saldo_retenido = 0, moneda = 'PEN'
        Wallet wallet = Wallet.createDefault(usuario);
        walletRepository.save(wallet);

        return usuario;
    }
}
