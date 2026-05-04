package com.ProyectoMercado.grupo2.users.servicesimplements;

import com.ProyectoMercado.grupo2.users.entities.User;
import com.ProyectoMercado.grupo2.users.repositories.IUserRepository;
import com.ProyectoMercado.grupo2.users.servicesinterfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImplement implements IUserService {

    @Autowired
    private IUserRepository uR;

    @Override
    public List<User> list() {
        return uR.findAll();
    }

    @Override
    public void insert(User user) {
        uR.save(user);
    }

    @Override
    public Optional<User> listId(UUID id) {
        return uR.findById(id);
    }

    @Override
    public void update(User user) {
        uR.save(user);
    }

    @Override
    public void delete(UUID id) {
        uR.deleteById(id);
    }
}
