package com.example.generateinvoices.controllers;

import com.example.generateinvoices.dtos.GenerateInvoiceRequestDto;
import com.example.generateinvoices.dtos.GenerateInvoiceResponseDto;
import com.example.generateinvoices.dtos.ResponseStatus;
import com.example.generateinvoices.models.Invoice;
import com.example.generateinvoices.services.BookingService;

public class BookingController {
    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public GenerateInvoiceResponseDto generateInvoice(GenerateInvoiceRequestDto requestDto) {
        GenerateInvoiceResponseDto responseDto = new GenerateInvoiceResponseDto();
        try {
            Invoice invoice = bookingService.generateInvoice(requestDto.getUserId());
            responseDto.setInvoice(invoice);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            return responseDto;
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            return responseDto;
        }
    }
}
