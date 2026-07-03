package com.foodgest.users.repositories;

import com.foodgest.users.entities.MovimientoWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MovimientoWalletRepository extends JpaRepository<MovimientoWallet, UUID> {
    List<MovimientoWallet> findByWalletIdOrderByCreatedAtDesc(UUID walletId);
}

