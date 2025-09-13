package com.example.bookrooms.services;

import com.example.bookrooms.exceptions.InvalidRoomException;
import com.example.bookrooms.exceptions.UserNotFoundException;
import com.example.bookrooms.models.Booking;

import java.util.Map;

public interface BookingService {
    Booking makeBooking(long userId, Map<Long, Integer> roomsToBeBooked) throws UserNotFoundException, InvalidRoomException;
}
