package com.example.bookrooms.repositories;

import com.example.bookrooms.models.Room;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryRoomRepository implements RoomRepository {
    private Map<Long, Room> roomsMap;
    private static long idCounter = 0;

    public InMemoryRoomRepository() {
        roomsMap = new HashMap<>();
    }

    @Override
    public Room save(Room room) {
        if (room.getId() == 0) {
            room.setId(++idCounter);
        }
        roomsMap.put(room.getId(), room);
        return room;
    }

    @Override
    public Optional<Room> findById(long roomId) {
        return roomsMap.values().stream().filter(room -> room.getId() == roomId).findFirst();
    }
}
