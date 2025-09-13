package com.example.bookrooms.models;

import java.util.Map;

public class Booking extends BaseModel {
    private CustomerSession customerSession;
    private Map<Room, Integer> bookedRooms;

    public CustomerSession getCustomerSession() {
        return customerSession;
    }

    public void setCustomerSession(CustomerSession customerSession) {
        this.customerSession = customerSession;
    }

    public Map<Room, Integer> getBookedRooms() {
        return bookedRooms;
    }

    public void setBookedRooms(Map<Room, Integer> bookedRooms) {
        this.bookedRooms = bookedRooms;
    }
}
