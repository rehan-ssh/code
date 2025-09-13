package com.example.generateinvoices.repositories;

import com.example.generateinvoices.models.Booking;

import java.util.List;

public interface BookingRepository {
    Booking save(Booking booking);

    List<Booking> findBookingByCustomerSession(long customerSessionId);
}
