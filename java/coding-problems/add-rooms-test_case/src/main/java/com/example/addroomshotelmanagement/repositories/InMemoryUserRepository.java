package com.example.addroomshotelmanagement.repositories;

import com.example.addroomshotelmanagement.models.User;

import java.util.*;

public class InMemoryUserRepository implements UserRepository {
    private Map<Long, User> userMap;
    private static long id = 0;

    public InMemoryUserRepository() {
        userMap = new HashMap<>();
    }

    @Override
    public Optional<User> findById(long userId) {
        return Optional.of(this.userMap.get(userId));
    }

    @Override
    public User save(User user) {
        if (user.getId() == 0) {
            user.setId(++id);
        }
        this.userMap.put(user.getId(), user);
        return user;
    }
}
