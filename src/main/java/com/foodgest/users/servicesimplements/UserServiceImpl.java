package com.foodgest.users.servicesimplements;

import com.foodgest.users.entities.User;
import com.foodgest.users.repositories.UserRepository;
import com.foodgest.users.servicesinterfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List; import java.util.Optional; import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired private UserRepository uR;
    @Override public List<User> list() { return uR.findAll(); }
    @Override public void insert(User u) { uR.save(u); }
    @Override public Optional<User> listId(UUID id) { return uR.findById(id); }
    @Override public void update(User u) { uR.save(u); }
    @Override public void delete(UUID id) { uR.deleteById(id); }
}
