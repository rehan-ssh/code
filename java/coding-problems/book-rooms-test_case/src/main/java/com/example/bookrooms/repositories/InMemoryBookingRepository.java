package com.example.bookrooms.repositories;

import com.example.bookrooms.models.Booking;

import java.util.HashMap;
import java.util.Map;

public class InMemoryBookingRepository implements BookingRepository {
    private Map<Long, Booking> bookingsMap;
    private static long idCounter = 0;

    public InMemoryBookingRepository() {
        bookingsMap = new HashMap<>();
    }
    @Override
    public Booking save(Booking booking) {
        if (booking.getId() == 0) {
            booking.setId(++idCounter);
        }
        bookingsMap.put(booking.getId(), booking);
        return booking;
    }
}
