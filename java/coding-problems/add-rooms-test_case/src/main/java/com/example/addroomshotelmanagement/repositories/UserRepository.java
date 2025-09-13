package com.example.addroomshotelmanagement.repositories;

import com.example.addroomshotelmanagement.models.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(long userId);
    User save(User user);
}
