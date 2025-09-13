package com.scaler.parking_lot.respositories;

import com.scaler.parking_lot.models.ParkingLot;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryParkingLotRepositoryImpl implements ParkingLotRepository{

    private Map<Long, ParkingLot> parkingLotMap;
    private static int id = 1;

    public InMemoryParkingLotRepositoryImpl() {
        this.parkingLotMap = new HashMap<>();
    }

    public Optional<ParkingLot> getParkingLotByGateId(long gateId) {
        return this.parkingLotMap.values().stream().filter(parkingLot -> parkingLot.getGates().stream().anyMatch(gate -> gate.getId() == gateId)).findFirst();
    }

    public Optional<ParkingLot> getParkingLotById(long id) {
        return Optional.ofNullable(this.parkingLotMap.get(id));
    }

    @Override
    public ParkingLot save(ParkingLot parkingLot) {
        if(parkingLot.getId() == 0) {
            parkingLot.setId(id++);
        }
        this.parkingLotMap.put(parkingLot.getId(), parkingLot);
        return parkingLot;
    }
}
