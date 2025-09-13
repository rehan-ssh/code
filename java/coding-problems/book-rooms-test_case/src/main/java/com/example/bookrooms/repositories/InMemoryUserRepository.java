package com.example.bookrooms.repositories;

import com.example.bookrooms.models.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepository {
    private Map<Long, User> userMap;
    private static long id = 0;

    public InMemoryUserRepository() {
        this.userMap = new HashMap<>();
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(this.userMap.get(id));
    }

    @Override
    public User save(User user) {
        if(user.getId() == 0){
            user.setId(++id);
        }
        this.userMap.put(user.getId(), user);
        return user;
    }
}
