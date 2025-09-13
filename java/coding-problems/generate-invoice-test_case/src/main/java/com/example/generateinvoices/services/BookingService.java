package com.example.generateinvoices.services;

import com.example.generateinvoices.exceptions.CustomerSessionNotFoundException;
import com.example.generateinvoices.models.Invoice;

public interface BookingService {
    Invoice generateInvoice(long userId) throws CustomerSessionNotFoundException;
}
