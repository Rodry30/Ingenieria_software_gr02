package com.foodgest.auth.servicesimplements;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodgest.auth.JwtTokenProvider;
import com.foodgest.auth.dtos.AuthTokenResponseDto;
import com.foodgest.auth.dtos.LoginRequestDto;
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
import com.foodgest.users.enums.TipoUsuarioEnum;
import com.foodgest.users.repositories.UserRepository;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements IAuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AgricultorRepository agricultorRepository;

    @Autowired
    private CompradorRepository compradorRepository;

    // MEJORA: ObjectMapper inyectado como bean (configurado por Spring Boot auto-config)
    // para garantizar que comparte la misma configuracion global (fechas, modulos, etc.)
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Validator validator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public AuthTokenResponseDto register(RegisterRequestDto dto) {

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
            AgricultorCreateDto perfilDto = convertPerfil(dto.getPerfil(), AgricultorCreateDto.class);

            var violations = validator.validate(perfilDto);
            if (!violations.isEmpty()) {
            String detalles = violations.stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .sorted()
                .collect(java.util.stream.Collectors.joining("; "));
            throw new BusinessException(
                "Perfil de agricultor invalido: " + detalles,
                HttpStatus.BAD_REQUEST);
            }
            Agricultor agricultor = perfilDto.toEntity(usuario);
            agricultorRepository.save(agricultor);

        } else {
            CompradorCreateDto perfilDto = convertPerfil(dto.getPerfil(), CompradorCreateDto.class);

            var violations = validator.validate(perfilDto);
            if (!violations.isEmpty()) {
            String detalles = violations.stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .sorted()
                .collect(java.util.stream.Collectors.joining("; "));
            throw new BusinessException(
                "Perfil de comprador invalido: " + detalles,
                HttpStatus.BAD_REQUEST);
            }
            Comprador comprador = perfilDto.toEntity(usuario);
            compradorRepository.save(comprador);
        }

        // La wallet se crea automaticamente en PostgreSQL (trigger trg_crear_wallet).

        String token = jwtTokenProvider.generateToken(usuario);
        return AuthTokenResponseDto.of(token, usuario);
    }

    @Override
    @Transactional
    public AuthTokenResponseDto login(LoginRequestDto dto) {
        UserEntities usuario = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new BusinessException(
                        "Credenciales invalidas",
                        HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPasswordHash())) {
            throw new BusinessException(
                    "Credenciales invalidas",
                    HttpStatus.UNAUTHORIZED);
        }

        if ("inactivo".equals(usuario.getEstado()) || "suspendido".equals(usuario.getEstado())) {
            throw new BusinessException(
                    "Cuenta " + usuario.getEstado() + ". Contacte al administrador.",
                    HttpStatus.FORBIDDEN);
        }

        usuario.setUltimoLogin(LocalDateTime.now());
        userRepository.save(usuario);

        String token = jwtTokenProvider.generateToken(usuario);
        return AuthTokenResponseDto.of(token, usuario);
    }

    private <T> T convertPerfil(java.util.Map<String, Object> perfil, Class<T> targetClass) {
        try {
            return objectMapper.convertValue(perfil, targetClass);
        } catch (IllegalArgumentException ex) {
            throw new BusinessException(
                    "El campo perfil tiene un formato invalido. Revise los campos requeridos.",
                    HttpStatus.BAD_REQUEST);
        }
    }
}
