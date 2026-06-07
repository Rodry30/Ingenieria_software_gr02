package com.foodgest.users.repositories;

import com.foodgest.users.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    Optional<Wallet> findByUsuario_Id(UUID usuarioId);
    boolean existsByUsuario_Id(UUID usuarioId);
}
