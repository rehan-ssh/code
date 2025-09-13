package com.example.bookrooms.repositories;

import com.example.bookrooms.models.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(long id);

    User save(User user);
}
