package com.example.bookrooms.controllers;

import com.example.bookrooms.dtos.MakeBookingRequestDto;
import com.example.bookrooms.dtos.MakeBookingResponseDto;
import com.example.bookrooms.dtos.ResponseStatus;
import com.example.bookrooms.models.Booking;
import com.example.bookrooms.services.BookingService;

public class BookingController {
    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public MakeBookingResponseDto makeBooking(MakeBookingRequestDto makeBookingRequestDto) {
        MakeBookingResponseDto responseDto = new MakeBookingResponseDto();
        try {
            Booking booking = bookingService.makeBooking(makeBookingRequestDto.getUserId(), makeBookingRequestDto.getBookedRooms());
            responseDto.setBooking(booking);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
}
