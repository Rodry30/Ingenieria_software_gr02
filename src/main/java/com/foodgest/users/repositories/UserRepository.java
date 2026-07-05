package com.foodgest.users.repositories;

import com.foodgest.users.entities.UserEntities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntities, UUID> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, UUID id);

    // tipo_usuario es un enum nativo de Postgres (tipo_usuario_enum); una query
    // derivada normal envia el parametro como varchar y Postgres lo rechaza
    // (mismo patron que los demas @ColumnTransformer de este proyecto, que solo
    // cubren el INSERT/UPDATE, no comparaciones en WHERE).
    @Query(value = "SELECT EXISTS(SELECT 1 FROM users WHERE tipo_usuario = CAST(:tipoUsuario AS tipo_usuario_enum))", nativeQuery = true)
    boolean existsByTipoUsuario(@Param("tipoUsuario") String tipoUsuario);

    Optional<UserEntities> findByEmail(String email);
}
