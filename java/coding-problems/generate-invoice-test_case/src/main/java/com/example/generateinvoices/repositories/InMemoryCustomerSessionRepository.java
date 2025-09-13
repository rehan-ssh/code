package com.example.generateinvoices.repositories;

import com.example.generateinvoices.models.CustomerSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryCustomerSessionRepository implements CustomerSessionRepository {
    private Map<Long, CustomerSession> customerSessionMap;
    private static long idCounter = 0;

    public InMemoryCustomerSessionRepository() {
        customerSessionMap = new HashMap<>();
    }

    @Override
    public CustomerSession save(CustomerSession customerSession) {
        if (customerSession.getId() == 0) {
            customerSession.setId(++idCounter);
        }
        customerSessionMap.put(idCounter, customerSession);
        return customerSession;
    }

    @Override
    public Optional<CustomerSession> findActiveCustomerSessionByUserId(long userId) {
        return customerSessionMap.values().stream().filter(customerSession -> customerSession.getUser().getId() == userId &&
                customerSession.isActive()).findFirst();
    }
}