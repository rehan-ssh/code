package com.example.ecom.controllers;

import com.example.ecom.dtos.DeliveryEstimateRequestDto;
import com.example.ecom.dtos.DeliveryEstimateResponseDto;
import com.example.ecom.dtos.ResponseStatus;
import com.example.ecom.models.*;
import com.example.ecom.repositories.*;
import com.example.ecom.services.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestProductController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeliveryHubRepository deliveryHubRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private ProductController productController;


    private User user;
    private List<Product> products;

    @BeforeEach
    public  void insertDummyData(){
        Address userAddress = new Address();
        userAddress.setBuilding("Test Building");
        userAddress.setCity("Test City");
        userAddress.setZipCode("123456");
        userAddress.setCountry("Test Country");
        userAddress.setFloor(1);
        userAddress.setRoomNo("Test Room");
        userAddress.setState("Test State");
        userAddress.setStreet("Test Street");
        userAddress.setLatitude(27.0343);
        userAddress.setLongitude(99.23421);

        Address sellerAddress = new Address();
        sellerAddress.setBuilding("Test Building #2");
        sellerAddress.setCity("Test City #2");
        sellerAddress.setZipCode("123456");
        sellerAddress.setCountry("Test Country #2");
        sellerAddress.setFloor(1);
        sellerAddress.setRoomNo("Test Room #2");
        sellerAddress.setState("Test State #2");
        sellerAddress.setStreet("Test Street #2");
        sellerAddress.setLatitude(27.5343);
        sellerAddress.setLongitude(99.63421);

        Address deliveryHubAddress = new Address();
        deliveryHubAddress.setBuilding("Test Building #3");
        deliveryHubAddress.setCity("Test City #3");
        deliveryHubAddress.setZipCode("123456");
        deliveryHubAddress.setCountry("Test Country #3");
        deliveryHubAddress.setFloor(1);
        deliveryHubAddress.setRoomNo("Test Room #3");
        deliveryHubAddress.setState("Test State #3");
        deliveryHubAddress.setStreet("Test Street #3");
        deliveryHubAddress.setLatitude(27.58943);
        deliveryHubAddress.setLongitude(99.78421);

        userAddress = addressRepository.save(userAddress);
        sellerAddress = addressRepository.save(sellerAddress);
        deliveryHubAddress = addressRepository.save(deliveryHubAddress);

        User u1 = new User();
        u1.setName("Test User");
        u1.setEmail("test@scaler.com");
        u1.setAddresses(List.of(userAddress));
        user = userRepository.save(u1);

        Seller seller = new Seller();
        seller.setName("Test Seller");
        seller.setAddress(sellerAddress);
        seller.setEmail("seller@ecomm.com");
        sellerRepository.save(seller);

        DeliveryHub deliveryHub = new DeliveryHub();
        deliveryHub.setAddress(deliveryHubAddress);
        deliveryHub.setName("Test Hub");
        deliveryHubRepository.save(deliveryHub);

        Product product1 = new Product();
        product1.setName("Test Product 1");
        product1.setPrice(100);
        product1.setDescription("Test Description 1");
        product1.setSeller(seller);
        product1 = productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Test Product 2");
        product2.setPrice(200);
        product2.setDescription("Test Description 2");
        product2.setSeller(seller);
        product2 = productRepository.save(product2);

        Product product3 = new Product();
        product3.setName("Test Product 3");
        product3.setPrice(300);
        product3.setDescription("Test Description 3");
        product3.setSeller(seller);
        product3 = productRepository.save(product3);

        products = List.of(product1, product2, product3);

    }


    @AfterEach
    public void cleanUp(){
        productRepository.deleteAll();

        sellerRepository.deleteAll();
        deliveryHubRepository.deleteAll();
        userRepository.deleteAll();
        addressRepository.deleteAll();

    }

    @Test
    public void testEstimateDeliveryTime_Success(){
        Address address = user.getAddresses().get(0);
        Product product = products.get(0);

        DeliveryEstimateRequestDto deliveryEstimateRequestDto = new DeliveryEstimateRequestDto();
        deliveryEstimateRequestDto.setAddressId(address.getId());
        deliveryEstimateRequestDto.setProductId(product.getId());

        DeliveryEstimateResponseDto deliveryEstimateResponseDto = productController.estimateDeliveryTime(deliveryEstimateRequestDto);
        assertNotNull(deliveryEstimateResponseDto, "Response should not be null");
        assertEquals(ResponseStatus.SUCCESS, deliveryEstimateResponseDto.getResponseStatus(), "Response status should be success");
        Date now = new Date();
        assertNotNull(deliveryEstimateResponseDto.getExpectedDeliveryDate(), "Expected delivery date should not be null");
        assertTrue(deliveryEstimateResponseDto.getExpectedDeliveryDate().after(now), "Expected delivery date should be sometime in the future");
    }

    @Test
    public void testEstimateDeliveryTime_InvalidProductId_Failure(){
        Address address = user.getAddresses().get(0);
        Product product = products.get(0);

        DeliveryEstimateRequestDto deliveryEstimateRequestDto = new DeliveryEstimateRequestDto();
        deliveryEstimateRequestDto.setAddressId(address.getId());
        deliveryEstimateRequestDto.setProductId(product.getId()*100);

        DeliveryEstimateResponseDto deliveryEstimateResponseDto = productController.estimateDeliveryTime(deliveryEstimateRequestDto);
        assertNotNull(deliveryEstimateResponseDto, "Response should not be null");
        assertEquals(ResponseStatus.FAILURE, deliveryEstimateResponseDto.getResponseStatus(), "Response status should be failure");
        assertNull(deliveryEstimateResponseDto.getExpectedDeliveryDate(), "Expected delivery date should be null");
    }

    @Test
    public void testEstimateDeliveryTime_InvalidUserId_Failure(){
        Address address = user.getAddresses().get(0);
        Product product = products.get(0);

        DeliveryEstimateRequestDto deliveryEstimateRequestDto = new DeliveryEstimateRequestDto();
        deliveryEstimateRequestDto.setAddressId(address.getId()*100);
        deliveryEstimateRequestDto.setProductId(product.getId());

        DeliveryEstimateResponseDto deliveryEstimateResponseDto = productController.estimateDeliveryTime(deliveryEstimateRequestDto);
        assertNotNull(deliveryEstimateResponseDto, "Response should not be null");
        assertEquals(ResponseStatus.FAILURE, deliveryEstimateResponseDto.getResponseStatus(), "Response status should be failure");
        assertNull(deliveryEstimateResponseDto.getExpectedDeliveryDate(), "Expected delivery date should be null");
    }

    @Test
    public void testEstimateDeliveryTime_UsesAdapter(){
        Reflections reflections = new Reflections(ProductService.class.getPackageName(), new SubTypesScanner(false));
        Set<Class<? extends ProductService>> implementations = reflections.getSubTypesOf(ProductService.class);
        assertEquals(1, implementations.size(), "Only one implementation of NotificationService should exist. Please modify the existing implementation instead of creating a new one.");
        Class<? extends ProductService> notificationServiceClass = implementations.iterator().next();
        Field[] declaredFields = notificationServiceClass.getDeclaredFields();
        boolean emailAdapterFound = false;
        for (Field declaredField : declaredFields) {
            if(declaredField.getName().toLowerCase().contains("adapter")){
                emailAdapterFound = true;
                break;
            }
        }
        assertTrue(emailAdapterFound, "We should use Adapter design pattern to send emails. Please create an adapter for sending emails and use it in the InventoryService implementation.");
    }

}
