package com.ProyectoMercado.grupo2.users.repositories;

import com.ProyectoMercado.grupo2.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {
}
