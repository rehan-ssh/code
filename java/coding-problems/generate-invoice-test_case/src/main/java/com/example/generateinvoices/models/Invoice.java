package com.example.generateinvoices.models;

import java.util.Map;

public class Invoice extends BaseModel {
    private Map<Room, Integer> bookedRooms;
    private double totalAmount;
    private double gst;
    private double serviceCharge;

    public Map<Room, Integer> getBookedRooms() {
        return bookedRooms;
    }

    public void setBookedRooms(Map<Room, Integer> bookedRooms) {
        this.bookedRooms = bookedRooms;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getGst() {
        return gst;
    }

    public void setGst(double gst) {
        this.gst = gst;
    }

    public double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }
}
