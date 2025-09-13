package com.scaler.parking_lot.respositories;

import com.scaler.parking_lot.models.Vehicle;
import com.scaler.parking_lot.models.VehicleType;

import java.util.Optional;

public interface VehicleRepository {
    // Do not modify the method signatures, feel free to add new methods

    public Optional<Vehicle> getVehicleByRegistrationNumber(String registrationNumber);

    public Vehicle save(Vehicle vehicle);
}
