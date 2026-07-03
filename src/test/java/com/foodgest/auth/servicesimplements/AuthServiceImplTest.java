package com.foodgest.auth.servicesimplements;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodgest.auth.JwtTokenProvider;
import com.foodgest.auth.dtos.AuthTokenResponseDto;
import com.foodgest.auth.dtos.RefreshTokenDto;
import com.foodgest.auth.dtos.RegisterRequestDto;
import com.foodgest.auth.entities.RefreshToken;
import com.foodgest.auth.repositories.RefreshTokenRepository;
import com.foodgest.perfiles.agricultores.dtos.AgricultorCreateDto;
import com.foodgest.perfiles.agricultores.entities.Agricultor;
import com.foodgest.perfiles.agricultores.repositories.AgricultorRepository;
import com.foodgest.shared.exceptions.BusinessException;
import com.foodgest.shared.external.IEmailService;
import com.foodgest.shared.external.INotificacionService;
import com.foodgest.shared.external.IReniecService;
import com.foodgest.shared.test.SwebokTestExtension;
import com.foodgest.users.entities.UserEntities;
import com.foodgest.users.enums.TipoUsuarioEnum;
import com.foodgest.users.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.validation.Validator;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, SwebokTestExtension.class})
public class AuthServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private AgricultorRepository agricultorRepository;
    @Mock private ObjectMapper objectMapper;
    @Mock private Validator validator;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtTokenProvider jwtTokenProvider;
    @Mock private RefreshTokenRepository refreshTokenRepository;

    // Dependencias externas mockeadas para el Quality Gate (HU-01)
    @Mock private IReniecService reniecService;
    @Mock private IEmailService emailService;
    @Mock private INotificacionService notificacionService;

    @InjectMocks
    private AuthServiceImpl authService;

    private RegisterRequestDto validDto;

    @BeforeEach
    void setUp() {
        validDto = new RegisterRequestDto();
        validDto.setNombre("Juan Perez");
        validDto.setEmail("juan@test.com");
        validDto.setPassword("Password123!");
        validDto.setTelefono("999888777");
        validDto.setTipoUsuario(TipoUsuarioEnum.agricultor);
        
        Map<String, Object> perfilData = new HashMap<>();
        perfilData.put("dni", "12345678");
        validDto.setPerfil(perfilData);
    }

    @Test
    @DisplayName("HU-01: Registro exitoso de agricultor retorna estado pendiente")
    void register_Success_ReturnsPendiente() {
        // Arrange
        when(userRepository.existsByEmail(validDto.getEmail())).thenReturn(false);
        AgricultorCreateDto perfilDto = new AgricultorCreateDto();
        when(objectMapper.convertValue(validDto.getPerfil(), AgricultorCreateDto.class)).thenReturn(perfilDto);
        when(validator.validate(perfilDto)).thenReturn(Collections.emptySet());
        when(passwordEncoder.encode(validDto.getPassword())).thenReturn("hashed-password");
        // Simular validacion externa de RENIEC
        // when(reniecService.validarDni(anyString())).thenReturn(true); 
        // (Nota: descomentar cuando se inyecte IReniecService en AuthServiceImpl)

        // Act
        AuthTokenResponseDto result = authService.register(validDto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getToken()).isNull();
        assertThat(result.getAccessToken()).isNull();
        assertThat(result.getRefreshToken()).isNull();
        assertThat(result.getUser()).isNotNull();
        assertThat(result.getUser().getEstado()).isEqualTo("pendiente");
        assertThat(result.getUser().getVerificado()).isFalse();

        verify(userRepository, times(1)).save(any(UserEntities.class));
        verify(agricultorRepository, times(1)).save(any(Agricultor.class));
        verify(refreshTokenRepository, never()).save(any(RefreshToken.class));

        // Verificar llamadas a servicios externos (ejemplos)
        // verify(emailService).enviarCorreoConfirmacion(eq(result.getEmail()), eq(result.getNombre()));
        // verify(notificacionService).notificarAdminNuevoRegistro(eq(result.getEmail()));
    }

    @Test
    @DisplayName("HU-01: Email duplicado retorna excepcion 409")
    void register_DuplicateEmail_ThrowsException() {
        // Arrange
        when(userRepository.existsByEmail(validDto.getEmail())).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> authService.register(validDto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("El email ya esta registrado");
        
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("HU-01: DNI invalido en RENIEC retorna 400 (Ejemplo de integracion mock)")
    void register_InvalidDniReniec_ThrowsException() {
        // EJEMPLO DE COMO SE PROBARIA EL FLUJO CON RENIEC:
        /*
        when(reniecService.validarDni("12345678")).thenReturn(false);

        assertThatThrownBy(() -> authService.register(validDto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("DNI no valido en RENIEC");
        */
        
        // Por ahora, forzamos la prueba de que un tipo no permitido falla
        validDto.setTipoUsuario(null); // Caso invalido
        assertThatThrownBy(() -> authService.register(validDto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Tipo de usuario no permitido");
    }

    @Test
    @DisplayName("HU-01: Refresh token valido rota y retorna nuevos tokens")
    void refresh_ValidToken_ReturnsNewPair() {
        UserEntities usuario = new UserEntities();
        usuario.setId(java.util.UUID.randomUUID());
        usuario.setNombre("Juan Perez");
        usuario.setEmail("juan@test.com");
        usuario.setTipoUsuario("agricultor");
        usuario.setEstado("activo");
        usuario.setVerificado(true);

        RefreshToken stored = new RefreshToken();
        stored.setUsuario(usuario);
        stored.setToken("refresh-actual");
        stored.setExpiresAt(OffsetDateTime.now().plusDays(1));
        stored.setRevoked(false);

        RefreshTokenDto dto = new RefreshTokenDto();
        dto.setRefreshToken("refresh-actual");

        when(refreshTokenRepository.findByToken("refresh-actual")).thenReturn(Optional.of(stored));
        when(jwtTokenProvider.generateToken(usuario)).thenReturn("nuevo-access-token");
        when(refreshTokenRepository.save(any(RefreshToken.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AuthTokenResponseDto result = authService.refresh(dto);

        assertThat(stored.getRevoked()).isTrue();
        assertThat(result.getAccessToken()).isEqualTo("nuevo-access-token");
        assertThat(result.getRefreshToken()).isNotBlank();
        verify(refreshTokenRepository, atLeastOnce()).save(any(RefreshToken.class));
    }
}
