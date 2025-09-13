package com.scaler.parking_lot.services;

import com.scaler.parking_lot.exceptions.InvalidGateException;
import com.scaler.parking_lot.exceptions.TicketNotFoundException;
import com.scaler.parking_lot.models.Invoice;

public interface InvoiceService {
    public Invoice createInvoice(long ticketId, long gateId) throws TicketNotFoundException, InvalidGateException;
}
