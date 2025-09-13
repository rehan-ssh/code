package com.example.bookrooms.services;

import com.example.bookrooms.exceptions.InvalidRoomException;
import com.example.bookrooms.exceptions.UserNotFoundException;
import com.example.bookrooms.models.*;
import com.example.bookrooms.repositories.BookingRepository;
import com.example.bookrooms.repositories.CustomerSessionRepository;
import com.example.bookrooms.repositories.RoomRepository;
import com.example.bookrooms.repositories.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BookingServiceImpl implements BookingService {
    private CustomerSessionRepository customerSessionRepository;
    private UserRepository userRepository;
    private RoomRepository roomRepository;
    private BookingRepository bookingRepository;

    public  BookingServiceImpl(CustomerSessionRepository customerSessionRepository, UserRepository userRepository,
                               RoomRepository roomRepository, BookingRepository bookingRepository) {
        this.customerSessionRepository = customerSessionRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Booking makeBooking(long userId, Map<Long, Integer> roomsToBeBooked) throws UserNotFoundException, InvalidRoomException {
        Optional<CustomerSession> optionalCustomerSession = customerSessionRepository.findActiveCustomerSessionByUserId(userId);
        CustomerSession customerSession;
        if (optionalCustomerSession.isEmpty()) {
            customerSession = new CustomerSession();
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isEmpty()) {
                throw new UserNotFoundException("User not found");
            }
            customerSession.setUser(optionalUser.get());
            customerSession.setCustomerSessionStatus(CustomerSessionStatus.ACTIVE);
            customerSession = customerSessionRepository.save(customerSession);
        } else {
            customerSession = optionalCustomerSession.get();
        }

        Booking booking = new Booking();
        booking.setCustomerSession(customerSession);
        Map<Room, Integer> bookedRoomsQtyMap = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : roomsToBeBooked.entrySet()) {
            Optional<Room> optionalRoom = roomRepository.findById(entry.getKey());
            if (optionalRoom.isEmpty()) {
                throw new InvalidRoomException("Room doesn't exist");
            } else {
                bookedRoomsQtyMap.put(optionalRoom.get(), entry.getValue());
            }
        }
        booking.setBookedRooms(bookedRoomsQtyMap);
        booking = bookingRepository.save(booking);
        return booking;
    }
}
