package com.example.ecom.services;

import com.example.ecom.exceptions.*;
import com.example.ecom.models.*;
import com.example.ecom.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService{
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;
    private InventoryRepository inventoryRepository;

    @Autowired
    public OrderServiceImpl(UserRepository userRepository, ProductRepository productRepository, OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, InventoryRepository inventoryRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Order cancelOrder(int orderId, int userId) throws UserNotFoundException, OrderNotFoundException, OrderDoesNotBelongToUserException, OrderCannotBeCancelledException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found"));
        if(order.getUser().getId() != user.getId()) {
            throw new OrderDoesNotBelongToUserException("Order doesn't belong to user");
        }
        if(order.getOrderStatus() != OrderStatus.PLACED) {
            throw new OrderCannotBeCancelledException("Order cannot be cancelled as its status is " + order.getOrderStatus().toString());
        }
        List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrder(order);
        for(OrderDetail orderDetail: orderDetails) {
            Inventory inventory = inventoryRepository.findByProduct(orderDetail.getProduct());
            inventory.setQuantity(inventory.getQuantity() + orderDetail.getQuantity());
            inventoryRepository.save(inventory);
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }
}
