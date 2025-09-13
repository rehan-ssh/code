package com.scaler.parking_lot.strategies.pricing;

import com.scaler.parking_lot.models.VehicleType;

import java.util.Date;

public interface PricingStrategy {
    double calculateAmount(Date entryTime, Date exitTime, VehicleType vehicleType);
}
