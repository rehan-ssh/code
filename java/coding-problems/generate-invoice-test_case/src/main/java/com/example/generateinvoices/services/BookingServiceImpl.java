package com.example.generateinvoices.services;

import com.example.generateinvoices.exceptions.CustomerSessionNotFoundException;
import com.example.generateinvoices.models.*;
import com.example.generateinvoices.repositories.BookingRepository;
import com.example.generateinvoices.repositories.CustomerSessionRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BookingServiceImpl implements BookingService {
    private BookingRepository bookingRepository;
    private CustomerSessionRepository customerSessionRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, CustomerSessionRepository customerSessionRepository) {
        this.bookingRepository = bookingRepository;
        this.customerSessionRepository = customerSessionRepository;
    }

    @Override
    public Invoice generateInvoice(long userId) throws CustomerSessionNotFoundException {
        Optional<CustomerSession> optionalCustomerSession = customerSessionRepository.findActiveCustomerSessionByUserId(userId);
        if (optionalCustomerSession.isEmpty()) {
            throw new CustomerSessionNotFoundException("Customer session not found");
        }
        CustomerSession customerSession = optionalCustomerSession.get();
        customerSession.setCustomerSessionStatus(CustomerSessionStatus.ENDED);
        customerSessionRepository.save(customerSession);

        List<Booking> bookings = bookingRepository.findBookingByCustomerSession(customerSession.getId());
        Map<Room, Integer> roomsBooked = new HashMap<>();
        for (Booking booking : bookings) {
            for (Map.Entry<Room, Integer> entry : booking.getBookedRooms().entrySet()) {
                roomsBooked.put(entry.getKey(), roomsBooked.getOrDefault(entry.getKey(), 0) + entry.getValue());
            }
        }

        double totalInvoiceAmount = 0;
        for (Map.Entry<Room, Integer> entry : roomsBooked.entrySet()) {
            totalInvoiceAmount += entry.getKey().getPrice() * entry.getValue();
        }

        double gst = totalInvoiceAmount * 0.18;
        double serviceCharge = totalInvoiceAmount * 0.1;
        totalInvoiceAmount = totalInvoiceAmount + gst + serviceCharge;

        Invoice invoice = new Invoice();
        invoice.setGst(gst);
        invoice.setServiceCharge(serviceCharge);
        invoice.setTotalAmount(totalInvoiceAmount);
        invoice.setBookedRooms(roomsBooked);
        return invoice;
    }
}
