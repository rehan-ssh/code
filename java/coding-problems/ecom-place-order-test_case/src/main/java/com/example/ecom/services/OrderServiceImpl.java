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
    private AddressRepository addressRepository;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;
    private HighDemandProductRepository highDemandProductRepository;
    private InventoryRepository inventoryRepository;

    @Autowired
    public OrderServiceImpl(UserRepository userRepository, AddressRepository addressRepository, ProductRepository productRepository, OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, HighDemandProductRepository highDemandProductRepository, InventoryRepository inventoryRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.highDemandProductRepository = highDemandProductRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public Order placeOrder(int userId, int addressId, List<Pair<Integer, Integer>> orderDetails) throws UserNotFoundException, InvalidAddressException, OutOfStockException, InvalidProductException, HighDemandProductException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new InvalidAddressException("Address doesn't exist"));
        if(address.getUser().getId() != user.getId()) {
            throw new InvalidAddressException("Address doesn't belong to user");
        }

        Map<Integer, Integer> productQuantityMap = new HashMap<>();
        for(Pair<Integer, Integer> orderDetail: orderDetails) {
            int productId = orderDetail.getFirst();
            int quantity = orderDetail.getSecond();
            if(quantity <= 0) {
                throw new InvalidProductException("Quantity should be greater than 0");
            }
            productQuantityMap.put(productId, quantity);
        }

        //Check if all products exist
        List<Product> products = productRepository.findAllByIdIn(productQuantityMap.keySet().stream().toList());
        if(products.size() != productQuantityMap.size()) {
            throw new InvalidProductException("Some products don't exist");
        }

        // Check if any of the product is a high demand product, if yes check is quantity is within limit
        List<HighDemandProduct> highDemandProductIds = highDemandProductRepository.findAllByProductIdIn(productQuantityMap.keySet().stream().toList());
        if(highDemandProductIds.size() > 0) {
            for(HighDemandProduct highDemandProduct: highDemandProductIds) {
                int productId = highDemandProduct.getProduct().getId();
                int quantity = productQuantityMap.get(productId);
                if(quantity > highDemandProduct.getMaxQuantity()) {
                    throw new HighDemandProductException("Quantity of product " + productId + " is more than limit");
                }
            }
        }

        // Check if all the products are in stock by taking a lock
        List<Inventory> inventories = inventoryRepository.findAllByProductIdIn(productQuantityMap.keySet().stream().toList());
        for(Inventory inventory: inventories) {
            int productId = inventory.getProduct().getId();
            int quantity = productQuantityMap.get(productId);
            if(inventory.getQuantity() < quantity) {
                throw new OutOfStockException("Not enough stock for product " + productId);
            }
        }

        // Update the inventory
        for(Inventory inventory: inventories) {
            int productId = inventory.getProduct().getId();
            int quantity = productQuantityMap.get(productId);
            inventory.setQuantity(inventory.getQuantity() - quantity);
        }
        inventoryRepository.saveAll(inventories);

        // Create the order
        List<OrderDetail> orderDetailsList = new ArrayList<>();
        for(Product product: products) {
            int productId = product.getId();
            int quantity = productQuantityMap.get(productId);
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(product);
            orderDetail.setQuantity(quantity);
            orderDetailsList.add(orderDetail);
        }

        Order order = new Order();
        order.setUser(user);
        order.setDeliveryAddress(address);
        order.setOrderDetails(orderDetailsList);
        order.setOrderStatus(OrderStatus.PLACED);
        order = orderRepository.save(order);

        for (OrderDetail orderDetail: orderDetailsList) {
            orderDetail.setOrder(order);
        }
        orderDetailRepository.saveAll(orderDetailsList);
        return order;

    }
}
