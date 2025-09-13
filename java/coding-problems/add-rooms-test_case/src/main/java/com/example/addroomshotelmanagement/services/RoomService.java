package com.example.addroomshotelmanagement.services;

import com.example.addroomshotelmanagement.exceptions.UnAuthorizedAccess;
import com.example.addroomshotelmanagement.exceptions.UserNotFoundException;
import com.example.addroomshotelmanagement.models.Room;

public interface RoomService {
    Room addRoom(long userId, String roomName, double price, String roomType, String description) throws UserNotFoundException, UnAuthorizedAccess;
}
