package com.example.ecom.controllers;

import com.example.ecom.dtos.PlaceOrderRequestDto;
import com.example.ecom.dtos.PlaceOrderResponseDto;
import com.example.ecom.dtos.ResponseStatus;
import com.example.ecom.models.*;
import com.example.ecom.repositories.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

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
    private AddressRepository addressRepository;
    @Autowired
    private HighDemandProductRepository highDemandProductRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderController orderController;

    private User user;
    private List<Product> products;

    @BeforeEach
    public  void insertDummyData(){
        Address address1 = new Address();
        address1.setBuilding("Test Building");
        address1.setCity("Test City");
        address1.setZipCode("123456");
        address1.setCountry("Test Country");
        address1.setFloor(1);
        address1.setRoomNo("Test Room");
        address1.setState("Test State");
        address1.setStreet("Test Street");

        Address address2 = new Address();
        address2.setBuilding("Test Building #2");
        address2.setCity("Test City #2");
        address2.setZipCode("123456");
        address2.setCountry("Test Country #2");
        address2.setFloor(1);
        address2.setRoomNo("Test Room #2");
        address2.setState("Test State #2");
        address2.setStreet("Test Street #2");

        User u1 = new User();
        u1.setName("Test User");
        u1.setEmail("test@scaler.com");
        u1.setAddresses(List.of(address1, address2));
        user = userRepository.save(u1);

        address1.setUser(user);
        address2.setUser(user);
        address1 = addressRepository.save(address1);
        address2 = addressRepository.save(address2);

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

        HighDemandProduct highDemandProduct = new HighDemandProduct();
        highDemandProduct.setProduct(product3);
        highDemandProduct.setMaxQuantity(2);
        highDemandProduct = highDemandProductRepository.save(highDemandProduct);

    }


    @AfterEach
    public void cleanUp(){
        List<Order> orders = orderRepository.findAll();
        for(Order order: orders){
            order.setOrderDetails(null);
            order.setUser(null);
            order.setDeliveryAddress(null);
            orderRepository.save(order);
        }
        orderDetailRepository.deleteAll();
        orderRepository.deleteAll();
        addressRepository.deleteAll();
        highDemandProductRepository.deleteAll();
        inventoryRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void placeOrder_Success(){
        List<Inventory> beforeOrdering = inventoryRepository.findAllById(List.of(products.get(0).getId(), products.get(1).getId(), products.get(2).getId()));
        List<Pair<Integer, Integer>> orderDetails = List.of(Pair.of(products.get(0).getId(), 1), Pair.of(products.get(1).getId(), 1),
                Pair.of(products.get(2).getId(), 1));
        PlaceOrderRequestDto placeOrderRequestDto = new PlaceOrderRequestDto();
        placeOrderRequestDto.setUserId(user.getId());
        placeOrderRequestDto.setAddressId(user.getAddresses().get(0).getId());
        placeOrderRequestDto.setOrderDetails(orderDetails);

        PlaceOrderResponseDto placeOrderResponseDto = orderController.placeOrder(placeOrderRequestDto);
        assertEquals(ResponseStatus.SUCCESS, placeOrderResponseDto.getStatus(), "Status should be success");
        assertEquals(OrderStatus.PLACED, placeOrderResponseDto.getOrder().getOrderStatus(), "Order status should be placed");
        assertEquals(3, placeOrderResponseDto.getOrder().getOrderDetails().size(), "There should be 3 items in the order");

        orderRepository.findById(placeOrderResponseDto.getOrder().getId()).ifPresent(order -> {
            assertEquals(user.getId(), order.getUser().getId(), "User id should be same");
            user.getAddresses().stream().filter(address -> address.getId() == order.getDeliveryAddress().getId()).findFirst().ifPresent(address -> {
                assertEquals(address.getId(), order.getDeliveryAddress().getId(), "Address id should be same");
            });
        });

        List<Inventory> afterOrdering = inventoryRepository.findAllById(List.of(products.get(0).getId(), products.get(1).getId(), products.get(2).getId()));
        for(int i = 0; i < beforeOrdering.size(); i++){
            assertEquals(beforeOrdering.get(i).getQuantity() - orderDetails.get(i).getSecond(), afterOrdering.get(i).getQuantity(), "Quantity should be updated");
        }

    }

    @Test
    public void placeOrder_IncorrectAddress_Failure(){
        List<Pair<Integer, Integer>> orderDetails = List.of(Pair.of(products.get(0).getId(), 1), Pair.of(products.get(1).getId(), 1),
                Pair.of(products.get(2).getId(), 1));
        PlaceOrderRequestDto placeOrderRequestDto = new PlaceOrderRequestDto();
        placeOrderRequestDto.setUserId(user.getId());
        placeOrderRequestDto.setAddressId(user.getAddresses().get(0).getId() + 100);
        placeOrderRequestDto.setOrderDetails(orderDetails);

        PlaceOrderResponseDto placeOrderResponseDto = orderController.placeOrder(placeOrderRequestDto);
        assertEquals(ResponseStatus.FAILURE, placeOrderResponseDto.getStatus(), "Status should be failure");
        assertNull(placeOrderResponseDto.getOrder(), "Order should be null");

    }

    @Test
    public void placeOrder_NonExistingUser_Failure(){
        List<Pair<Integer, Integer>> orderDetails = List.of(Pair.of(products.get(0).getId(), 1), Pair.of(products.get(1).getId(), 1),
                Pair.of(products.get(2).getId(), 1));
        PlaceOrderRequestDto placeOrderRequestDto = new PlaceOrderRequestDto();
        placeOrderRequestDto.setUserId(user.getId() + 1000);
        placeOrderRequestDto.setAddressId(user.getAddresses().get(0).getId());
        placeOrderRequestDto.setOrderDetails(orderDetails);

        PlaceOrderResponseDto placeOrderResponseDto = orderController.placeOrder(placeOrderRequestDto);
        assertEquals(ResponseStatus.FAILURE, placeOrderResponseDto.getStatus(), "Status should be failure");
        assertNull(placeOrderResponseDto.getOrder(), "Order should be null");

    }

    @Test
    public void placeOrder_NonExistingProduct_Failure(){
        List<Pair<Integer, Integer>> orderDetails = List.of(Pair.of(products.get(0).getId(), 1), Pair.of(products.get(1).getId(), 1),
                Pair.of(products.get(2).getId()+1000, 1));
        PlaceOrderRequestDto placeOrderRequestDto = new PlaceOrderRequestDto();
        placeOrderRequestDto.setUserId(user.getId());
        placeOrderRequestDto.setAddressId(user.getAddresses().get(0).getId());
        placeOrderRequestDto.setOrderDetails(orderDetails);

        PlaceOrderResponseDto placeOrderResponseDto = orderController.placeOrder(placeOrderRequestDto);
        assertEquals(ResponseStatus.FAILURE, placeOrderResponseDto.getStatus(), "Status should be failure");
        assertNull(placeOrderResponseDto.getOrder(), "Order should be null");

    }


    @Test
    public void placeOrder_OverPurchaseHighDemandProduct_Failure(){
        List<Pair<Integer, Integer>> orderDetails = List.of(Pair.of(products.get(0).getId(), 1), Pair.of(products.get(1).getId(), 1),
                Pair.of(products.get(2).getId(), 100));
        PlaceOrderRequestDto placeOrderRequestDto = new PlaceOrderRequestDto();
        placeOrderRequestDto.setUserId(user.getId());
        placeOrderRequestDto.setAddressId(user.getAddresses().get(0).getId());
        placeOrderRequestDto.setOrderDetails(orderDetails);

        PlaceOrderResponseDto placeOrderResponseDto = orderController.placeOrder(placeOrderRequestDto);
        assertEquals(ResponseStatus.FAILURE, placeOrderResponseDto.getStatus(), "Status should be failure");
        assertNull(placeOrderResponseDto.getOrder(), "Order should be null");

    }

    @Test
    public void placeOrder_OverPurchaseNonHighDemandProducts_Failure(){
        List<Pair<Integer, Integer>> orderDetails = List.of(Pair.of(products.get(0).getId(), 20), Pair.of(products.get(1).getId(), 15),
                Pair.of(products.get(2).getId(), 1));
        PlaceOrderRequestDto placeOrderRequestDto = new PlaceOrderRequestDto();
        placeOrderRequestDto.setUserId(user.getId());
        placeOrderRequestDto.setAddressId(user.getAddresses().get(0).getId());
        placeOrderRequestDto.setOrderDetails(orderDetails);

        PlaceOrderResponseDto placeOrderResponseDto = orderController.placeOrder(placeOrderRequestDto);
        assertEquals(ResponseStatus.FAILURE, placeOrderResponseDto.getStatus(), "Status should be failure");
        assertNull(placeOrderResponseDto.getOrder(), "Order should be null");

    }
}
