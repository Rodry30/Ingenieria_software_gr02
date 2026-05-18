package com.foodgest.auth.servicesinterfaces;

import com.foodgest.auth.dtos.RegisterRequestDto;
import com.foodgest.users.entities.UserEntities;

public interface IAuthService {
    /**
     * Registra un nuevo usuario junto con su perfil (agricultor o comprador) y su wallet.
     * Toda la operacion ocurre dentro de una unica transaccion.
     *
     * @param dto datos del registro validados por el controller
     * @return la entidad UserEntities persistida (sin datos sensibles al exponerse via DTO)
     * @throws com.foodgest.shared.exceptions.BusinessException si el email ya existe o el tipo no es permitido
     */
    UserEntities register(RegisterRequestDto dto);
}
