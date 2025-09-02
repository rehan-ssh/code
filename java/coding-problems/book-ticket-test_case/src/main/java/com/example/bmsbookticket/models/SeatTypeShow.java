package com.example.bmsbookticket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "seat_type_shows")
public class SeatTypeShow extends BaseModel{
    @ManyToOne
    private Show show;

    @Enumerated(value = EnumType.ORDINAL)
    private SeatType seatType;
    private double price;
}
