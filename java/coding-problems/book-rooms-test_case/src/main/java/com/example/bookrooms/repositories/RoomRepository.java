package com.example.bookrooms.repositories;

import com.example.bookrooms.models.Room;

import java.util.Optional;

public interface RoomRepository {
    Room save(Room room);

    Optional<Room> findById(long roomId);
}
