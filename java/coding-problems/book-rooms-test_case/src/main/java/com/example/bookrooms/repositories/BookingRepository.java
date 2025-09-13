package com.example.bookrooms.repositories;

import com.example.bookrooms.models.Booking;

public interface BookingRepository {
    Booking save(Booking booking);
}
