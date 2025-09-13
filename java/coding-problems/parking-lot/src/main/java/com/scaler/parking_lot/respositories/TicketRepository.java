package com.scaler.parking_lot.respositories;

import com.scaler.parking_lot.models.*;

import java.util.Date;
import java.util.Optional;

public interface TicketRepository {
    // Do not modify the method signature, feel free to add new methods

    public Ticket save(Ticket ticket);

    public Optional<Ticket> getTicketById(long ticketId);
}
