package com.foodgest.users.servicesinterfaces;

import com.foodgest.users.dtos.UserCreateRequest;
import com.foodgest.users.dtos.UserResponse;
import com.foodgest.users.dtos.UserUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    List<UserResponse> list();
    UserResponse insert(UserCreateRequest request);
    UserResponse listId(UUID id);
    UserResponse update(UUID id,UserUpdateRequest user);
    void delete(UUID id);
}

