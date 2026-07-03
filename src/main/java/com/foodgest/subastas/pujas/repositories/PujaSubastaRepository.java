package com.foodgest.subastas.pujas.repositories;

import com.foodgest.subastas.pujas.entities.PujaSubasta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PujaSubastaRepository extends JpaRepository<PujaSubasta, UUID> {
    List<PujaSubasta> findBySubastaIdOrderByMontoPujaDesc(UUID subastaId);
    Optional<PujaSubasta> findFirstBySubastaIdOrderByMontoPujaDesc(UUID subastaId);
}

