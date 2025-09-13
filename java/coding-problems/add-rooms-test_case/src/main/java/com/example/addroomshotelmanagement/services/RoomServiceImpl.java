package com.example.addroomshotelmanagement.services;

import com.example.addroomshotelmanagement.exceptions.UnAuthorizedAccess;
import com.example.addroomshotelmanagement.exceptions.UserNotFoundException;
import com.example.addroomshotelmanagement.models.Room;
import com.example.addroomshotelmanagement.models.RoomType;
import com.example.addroomshotelmanagement.models.User;
import com.example.addroomshotelmanagement.models.UserType;
import com.example.addroomshotelmanagement.repositories.RoomRepository;
import com.example.addroomshotelmanagement.repositories.UserRepository;

import java.util.Optional;

public class RoomServiceImpl implements RoomService {
    private RoomRepository roomRepository;
    private UserRepository userRepository;

    public RoomServiceImpl(RoomRepository roomRepository, UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Room addRoom(long userId, String roomName, double price, String roomType, String description) throws UserNotFoundException, UnAuthorizedAccess {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        User user = optionalUser.get();
        if (!user.getUserType().equals(UserType.ADMIN)) {
            throw new UnAuthorizedAccess("User is not an admin");
        }

        Room room = new Room();
        room.setName(roomName);
        room.setRoomType(RoomType.valueOf(roomType));
        room.setPrice(price);
        room.setDescription(description);
        roomRepository.add(room);
        return room;
    }
}
