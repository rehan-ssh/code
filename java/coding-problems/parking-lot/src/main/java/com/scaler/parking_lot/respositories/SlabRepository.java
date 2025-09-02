package com.scaler.parking_lot.respositories;

import com.scaler.parking_lot.models.Slab;
import com.scaler.parking_lot.models.VehicleType;

import java.util.List;

public interface SlabRepository {

    public List<Slab> getSortedSlabsByVehicleType(VehicleType vehicleType);

    public Slab save(Slab slab);
}
