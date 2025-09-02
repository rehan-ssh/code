package com.example.bmsbookticket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity(name = "tickets")
public class Ticket extends BaseModel{

    @ManyToOne
    private Show show;

    @OneToMany
    List<Seat> seats;
    private Date timeOfBooking;

    @ManyToOne
    private User user;

    @Enumerated(value = jakarta.persistence.EnumType.ORDINAL)
    private TicketStatus status;
}
