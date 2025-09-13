package com.scaler.parking_lot.respositories;

import com.scaler.parking_lot.models.Gate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryGateRepositoryImpl implements GateRepository {

    private Map<Long, Gate> gateMap;
    private static int id = 1;

    public InMemoryGateRepositoryImpl() {
        this.gateMap = new HashMap<>();
    }

    public Optional<Gate> findById(long gateId) {
        return Optional.ofNullable(gateMap.get(gateId));
    }

    @Override
    public Gate save(Gate gate) {
        if(gate.getId() == 0){
            gate.setId(id++);
        }
        gateMap.put(gate.getId(), gate);
        return gate;
    }
}
