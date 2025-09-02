package com.example.ecom.services;

import com.example.ecom.exceptions.*;
import com.example.ecom.models.Order;
import org.springframework.data.util.Pair;

import java.util.List;

public interface OrderService {

    public Order placeOrder(int userId, int addressId, List<Pair<Integer, Integer>> orderDetails) throws UserNotFoundException, InvalidAddressException, OutOfStockException, InvalidProductException, HighDemandProductException;
}
