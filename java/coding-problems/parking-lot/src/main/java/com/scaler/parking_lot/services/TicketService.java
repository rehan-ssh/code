package com.scaler.parking_lot.services;

import com.scaler.parking_lot.exceptions.*;
import com.scaler.parking_lot.models.AdditionalService;
import com.scaler.parking_lot.models.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketService {
    // Do not modify the method signatures, feel free to add new methods
    public Ticket generateTicket(long gateId, String registrationNumber, String vehicleType, List<String> additionalServices) throws InvalidGateException, InvalidParkingLotException, ParkingSpotNotAvailableException, UnsupportedAdditionalService, AdditionalServiceNotSupportedByVehicle;
}
