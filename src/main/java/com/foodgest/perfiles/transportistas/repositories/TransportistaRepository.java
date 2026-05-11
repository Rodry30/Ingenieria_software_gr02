package com.foodgest.perfiles.transportistas.repositories;

import com.foodgest.perfiles.transportistas.entities.TransportistaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransportistaRepository extends JpaRepository<TransportistaEntity, UUID> {
    boolean existsByUsuarioId(UUID usuarioId);
    boolean existsByDni(String dni);
    boolean existsByPlacaVehiculo(String placaVehiculo);
}
