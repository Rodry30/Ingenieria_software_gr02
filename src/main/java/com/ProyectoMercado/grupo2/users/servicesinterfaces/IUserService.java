package com.ProyectoMercado.grupo2.users.servicesinterfaces;

import com.ProyectoMercado.grupo2.users.entities.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    List<User> list();
    void insert(User user);
    Optional<User> listId(UUID id);
    void update(User user);
    void delete(UUID id);
}
