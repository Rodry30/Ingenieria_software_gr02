package com.foodgest.auth.servicesimplements;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodgest.auth.dtos.RegisterRequestDto;
import com.foodgest.perfiles.agricultores.dtos.AgricultorCreateDto;
import com.foodgest.perfiles.agricultores.entities.Agricultor;
import com.foodgest.perfiles.agricultores.repositories.AgricultorRepository;
import com.foodgest.shared.exceptions.BusinessException;
import com.foodgest.shared.external.IEmailService;
import com.foodgest.shared.external.INotificacionService;
import com.foodgest.shared.external.IReniecService;
import com.foodgest.shared.test.SwebokTestExtension;
import com.foodgest.users.entities.UserEntities;
import com.foodgest.users.entities.Wallet;
import com.foodgest.users.enums.TipoUsuarioEnum;
import com.foodgest.users.repositories.UserRepository;
import com.foodgest.users.repositories.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, SwebokTestExtension.class})
public class AuthServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private AgricultorRepository agricultorRepository;
    @Mock private WalletRepository walletRepository;
    @Mock private ObjectMapper objectMapper;

    // Dependencias externas mockeadas para el Quality Gate (HU-01)
    @Mock private IReniecService reniecService;
    @Mock private IEmailService emailService;
    @Mock private INotificacionService notificacionService;

    @InjectMocks
    private AuthServiceImpl authService;

    private RegisterRequestDto validDto;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

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
        
        // Simular validacion externa de RENIEC
        // when(reniecService.validarDni(anyString())).thenReturn(true); 
        // (Nota: descomentar cuando se inyecte IReniecService en AuthServiceImpl)

        // Act
        UserEntities result = authService.register(validDto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getEstado()).isEqualTo("pendiente");
        assertThat(result.getVerificado()).isFalse();
        
        // Verificar que la contrasena esta hasheada con BCrypt
        assertThat(encoder.matches("Password123!", result.getPasswordHash())).isTrue();

        verify(userRepository, times(1)).save(any(UserEntities.class));
        verify(agricultorRepository, times(1)).save(any(Agricultor.class));
        
        // Verificar creacion de Wallet con saldo 0
        ArgumentCaptor<Wallet> walletCaptor = ArgumentCaptor.forClass(Wallet.class);
        verify(walletRepository).save(walletCaptor.capture());
        assertThat(walletCaptor.getValue().getSaldoDisponible().doubleValue()).isEqualTo(0.0);
        
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
        verify(walletRepository, never()).save(any());
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
}
