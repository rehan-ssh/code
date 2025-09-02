package com.scaler.parking_lot.strategies.assignment;

import com.scaler.parking_lot.models.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EqualDistributionAssignmentStrategy implements SpotAssignmentStrategy {

    @Override
    public Optional<ParkingSpot> assignSpot(ParkingLot parkingLot, VehicleType vehicleType) {
        // For a given vehicle type, calculate the current floor wise distribution of the parking spots
        // Assign the spot to the floor with the least number of spots for the given vehicle type
        // If there are multiple floors with the same number of spots, assign the spot to the floor with the lowest floor number
        // If there are no spots available, return Optional.empty()
        Map<Long, Integer> floorWiseDistribution = new HashMap<>();
        for (ParkingFloor parkingFloor : parkingLot.getParkingFloors()) {
            if(parkingFloor.getStatus().equals(FloorStatus.OPERATIONAL)){
                for (ParkingSpot spot : parkingFloor.getSpots()) {
                    if(spot.getStatus().equals(ParkingSpotStatus.AVAILABLE) && spot.getSupportedVehicleType().equals(vehicleType)){
                        floorWiseDistribution.put(parkingFloor.getId(), floorWiseDistribution.getOrDefault(parkingFloor.getId(), 0) + 1);
                    }
                }
            }
        }

        long maxFloorId = -1;
        int maxSpots = Integer.MIN_VALUE;
        for (Map.Entry<Long, Integer> entry : floorWiseDistribution.entrySet()) {
            if(entry.getValue() > maxSpots){
                maxSpots = entry.getValue();
                maxFloorId = entry.getKey();
            }
        }
        // Set the status of the spot to OCCUPIED
        long finalMaxFloorId = maxFloorId;
        ParkingFloor parkingFloor = parkingLot.getParkingFloors().stream().filter(floor -> floor.getId() == finalMaxFloorId).findFirst().get();
        for (ParkingSpot spot : parkingFloor.getSpots()) {
            if(spot.getStatus().equals(ParkingSpotStatus.AVAILABLE) && spot.getSupportedVehicleType().equals(vehicleType)){
                spot.setStatus(ParkingSpotStatus.OCCUPIED);
                return Optional.of(spot);
            }
        }
        return Optional.empty();
    }
}
