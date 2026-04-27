package com.ProyectoMercado.grupo2.users.repository;

import com.ProyectoMercado.grupo2.users.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
