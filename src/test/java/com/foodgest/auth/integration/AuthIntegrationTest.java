package com.foodgest.auth.integration;

import com.foodgest.users.entities.UserEntities;
import com.foodgest.users.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test de Integracion Real con Testcontainers PostgreSQL + PostGIS.
 * Valida reglas de base de datos como Unicidad de Email (HU-01) y RUC (HU-02).
 */
@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
public class AuthIntegrationTest {

    // Imagen Docker con PostGIS para soportar el campo GEOGRAPHY de agricultores
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            DockerImageName.parse("postgis/postgis:15-3.3")
            .asCompatibleSubstituteFor("postgres")
    );

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("HU-01 (BD): Validar que email duplicado lanza excepcion a nivel BD (Unique Constraint)")
    void testUniqueEmailConstraint() {
        // Arrange
        UserEntities user1 = new UserEntities();
        user1.setNombre("User One");
        user1.setEmail("unique@test.com");
        user1.setPasswordHash("hash123");
        user1.setTipoUsuario("agricultor");
        user1.setEstado("pendiente");
        user1.setTelefono("123456");
        
        userRepository.saveAndFlush(user1);

        UserEntities user2 = new UserEntities();
        user2.setNombre("User Two");
        user2.setEmail("unique@test.com"); // Mismo email
        user2.setPasswordHash("hash456");
        user2.setTipoUsuario("comprador");
        user2.setEstado("pendiente");
        user2.setTelefono("654321");

        // Act & Assert
        // Al intentar guardar un usuario con email existente, JPA/Postgres lanza DataIntegrityViolationException
        assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
            userRepository.saveAndFlush(user2);
        });
    }

    @Test
    @DisplayName("HU-01 (BD): Persistencia correcta de UUID y fechas automaticas")
    void testEntityPersistence() {
        UserEntities user = new UserEntities();
        user.setNombre("User Three");
        user.setEmail("three@test.com");
        user.setPasswordHash("hash123");
        user.setTipoUsuario("agricultor");
        user.setEstado("pendiente");
        user.setTelefono("111111");

        UserEntities saved = userRepository.save(user);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getUpdatedAt()).isNotNull();
    }
}
