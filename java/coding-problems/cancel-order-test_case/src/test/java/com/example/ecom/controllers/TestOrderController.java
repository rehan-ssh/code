package com.example.ecom.controllers;

import com.example.ecom.dtos.CancelOrderRequestDto;
import com.example.ecom.dtos.CancelOrderResponseDto;
import com.example.ecom.dtos.ResponseStatus;
import com.example.ecom.models.*;
import com.example.ecom.repositories.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class TestOrderController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderController orderController;

    private User user;
    private List<Product> products;
    private Order order;

    @BeforeEach
    public  void insertDummyData(){
        User u1 = new User();
        u1.setName("Test User");
        u1.setEmail("test@scaler.com");
        user = userRepository.save(u1);


        Product product1 = new Product();
        product1.setName("Test Product 1");
        product1.setPrice(100);
        product1.setDescription("Test Description 1");
        product1 = productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Test Product 2");
        product2.setPrice(200);
        product2.setDescription("Test Description 2");
        product2 = productRepository.save(product2);

        Product product3 = new Product();
        product3.setName("Test Product 3");
        product3.setPrice(300);
        product3.setDescription("Test Description 3");
        product3 = productRepository.save(product3);

        products = List.of(product1, product2, product3);

        Inventory inventory1 = new Inventory();
        inventory1.setProduct(product1);
        inventory1.setQuantity(10);
        inventory1 = inventoryRepository.save(inventory1);

        Inventory inventory2 = new Inventory();
        inventory2.setProduct(product2);
        inventory2.setQuantity(10);
        inventory2 = inventoryRepository.save(inventory2);

        Inventory inventory3 = new Inventory();
        inventory3.setProduct(product3);
        inventory3.setQuantity(10);
        inventory3 = inventoryRepository.save(inventory3);


        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.PLACED);
        order = orderRepository.save(order);

        List<OrderDetail> orderDetails = new ArrayList<>();
        for(Product product: products){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(product);
            orderDetail.setQuantity(1);
            orderDetails.add(orderDetail);
        }
        orderDetailRepository.saveAll(orderDetails);
        order.setOrderDetails(orderDetails);
        this.order = orderRepository.save(order);
    }


    @AfterEach
    public void cleanUp(){
        List<Order> orders = orderRepository.findAll();
        for(Order order: orders){
            order.setOrderDetails(null);
            order.setUser(null);
            orderRepository.save(order);
        }
        orderDetailRepository.deleteAll();
        orderRepository.deleteAll();
        inventoryRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testCancelOrder_Success(){
        List<Inventory> beforeOrdering = inventoryRepository.findAll();

        CancelOrderRequestDto requestDto = new CancelOrderRequestDto();
        requestDto.setOrderId(order.getId());
        requestDto.setUserId(user.getId());

        CancelOrderResponseDto responseDto = orderController.cancelOrder(requestDto);
        assertEquals(ResponseStatus.SUCCESS, responseDto.getStatus());
        assertEquals(OrderStatus.CANCELLED, responseDto.getOrder().getOrderStatus());

        List<Inventory> afterOrdering = inventoryRepository.findAll();
        for(int i=0; i<beforeOrdering.size(); i++){
            assertEquals(beforeOrdering.get(i).getQuantity()+1, afterOrdering.get(i).getQuantity(), "Inventory quantity should be increased by 1");
        }
    }

    @Test
    public void testCancelOrder_UserNotFound(){
        CancelOrderRequestDto requestDto = new CancelOrderRequestDto();
        requestDto.setOrderId(order.getId());
        requestDto.setUserId(1000);

        CancelOrderResponseDto responseDto = orderController.cancelOrder(requestDto);
        assertEquals(ResponseStatus.FAILURE, responseDto.getStatus());
        assertNull(responseDto.getOrder());

    }


    @Test
    public void testCancelOrder_OrderNotFound(){
        CancelOrderRequestDto requestDto = new CancelOrderRequestDto();
        requestDto.setOrderId(1000);
        requestDto.setUserId(user.getId());

        CancelOrderResponseDto responseDto = orderController.cancelOrder(requestDto);
        assertEquals(ResponseStatus.FAILURE, responseDto.getStatus());
        assertNull(responseDto.getOrder());

    }

    @Test
    public void testCancelOrder_OrderDoesntBelongToUser() {
        User user2 = new User();
        user2.setName("Test User 2");
        user2.setEmail("test2@scaler.com");
        user2 = userRepository.save(user2);

        CancelOrderRequestDto requestDto = new CancelOrderRequestDto();
        requestDto.setOrderId(order.getId());
        requestDto.setUserId(user2.getId());

        CancelOrderResponseDto responseDto = orderController.cancelOrder(requestDto);
        assertEquals(ResponseStatus.FAILURE, responseDto.getStatus());
        assertNull(responseDto.getOrder());
    }

    @Test
    public void testCancelOrder_OrderAlreadyShipped(){
        order.setOrderStatus(OrderStatus.SHIPPED);
        orderRepository.save(order);

        CancelOrderRequestDto requestDto = new CancelOrderRequestDto();
        requestDto.setOrderId(order.getId());
        requestDto.setUserId(user.getId());

        CancelOrderResponseDto responseDto = orderController.cancelOrder(requestDto);
        assertEquals(ResponseStatus.FAILURE, responseDto.getStatus());
        assertNull(responseDto.getOrder());
    }

    @Test
    public void testCancelOrder_OrderAlreadyDelivered(){
        order.setOrderStatus(OrderStatus.DELIVERED);
        orderRepository.save(order);

        CancelOrderRequestDto requestDto = new CancelOrderRequestDto();
        requestDto.setOrderId(order.getId());
        requestDto.setUserId(user.getId());

        CancelOrderResponseDto responseDto = orderController.cancelOrder(requestDto);
        assertEquals(ResponseStatus.FAILURE, responseDto.getStatus());
        assertNull(responseDto.getOrder());
    }

}
