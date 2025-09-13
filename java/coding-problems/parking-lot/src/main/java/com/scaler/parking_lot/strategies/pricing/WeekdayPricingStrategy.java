package com.scaler.parking_lot.strategies.pricing;

import com.scaler.parking_lot.models.VehicleType;
import com.scaler.parking_lot.utils.DateTimeUtils;

import java.util.Date;

public class WeekdayPricingStrategy implements PricingStrategy{

    @Override
    public double calculateAmount(Date entryTime, Date exitTime, VehicleType vehicleType) {
        int hours = DateTimeUtils.calculateHours(entryTime, exitTime);
        return hours * 10;
    }
}
