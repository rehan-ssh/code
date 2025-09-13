package com.example.ecom.controllers;

import com.example.ecom.dtos.*;
import com.example.ecom.models.*;
import com.example.ecom.repositories.InventoryRepository;
import com.example.ecom.repositories.NotificationRepository;
import com.example.ecom.repositories.ProductRepository;
import com.example.ecom.repositories.UserRepository;
import com.example.ecom.services.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestControllers {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationController notificationController;

    @Autowired
    private InventoryController inventoryController;

    private User user;
    private User user2;
    private List<Product> products;

    @BeforeEach
    public  void insertDummyData(){


        User u1 = new User();
        u1.setName("Test User");
        u1.setEmail("test@scaler.com");
        user = userRepository.save(u1);

        User u2 = new User();
        u2.setName("Test User 2");
        u2.setEmail("test2@scaler.com");
        user2 = userRepository.save(u2);

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

    }


    @BeforeEach
    public void cleanUp(){
        notificationRepository.deleteAll();
        inventoryRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testRegisterUserForNotification_Success(){
        Product product = products.get(0);
        inventoryRepository.findByProduct(product).ifPresent(inventory -> {
            inventory.setQuantity(0);
            inventoryRepository.save(inventory);
        });
        RegisterUserForNotificationRequestDto requestDto = new RegisterUserForNotificationRequestDto();
        requestDto.setProductId(products.get(0).getId());
        requestDto.setUserId(user.getId());

        RegisterUserForNotificationResponseDto responseDto = notificationController.registerUser(requestDto);
        assertNotNull(responseDto, "Response should not be null");
        assertEquals(ResponseStatus.SUCCESS, responseDto.getResponseStatus(), "Response status should be success");
        assertNotNull(responseDto.getNotification(), "Notification should not be null");
        assertEquals(1, notificationRepository.count(), "Notification should be created");
        assertEquals(NotificationStatus.PENDING, notificationRepository.findAll().get(0).getStatus(), "Notification status should be pending");
    }
    @Test
    public void testRegisterUserForNotification_UserNotfound_Failure(){
        Product product = products.get(0);
        inventoryRepository.findByProduct(product).ifPresent(inventory -> {
            inventory.setQuantity(0);
            inventoryRepository.save(inventory);
        });
        RegisterUserForNotificationRequestDto requestDto = new RegisterUserForNotificationRequestDto();
        requestDto.setProductId(products.get(0).getId());
        requestDto.setUserId(user.getId() * 100);

        RegisterUserForNotificationResponseDto responseDto = notificationController.registerUser(requestDto);
        assertNotNull(responseDto, "Response should not be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus(), "Response status should be success");
        assertNull(responseDto.getNotification(), "Notification should be null");
        assertEquals(0, notificationRepository.count(), "Notification should not be created");
    }

    @Test
    public void testRegisterUserForNotification_ProductNotFound_Failure(){
        RegisterUserForNotificationRequestDto requestDto = new RegisterUserForNotificationRequestDto();
        requestDto.setProductId(products.get(0).getId()* 100);
        requestDto.setUserId(user.getId());

        RegisterUserForNotificationResponseDto responseDto = notificationController.registerUser(requestDto);
        assertNotNull(responseDto, "Response should not be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus(), "Response status should be success");
        assertNull(responseDto.getNotification(), "Notification should be null");
        assertEquals(0, notificationRepository.count(), "Notification should not be created");
    }

    @Test
    public void testRegisterUserForNotification_ProductInStock_Failure(){
        RegisterUserForNotificationRequestDto requestDto = new RegisterUserForNotificationRequestDto();
        requestDto.setProductId(products.get(0).getId());
        requestDto.setUserId(user.getId());

        RegisterUserForNotificationResponseDto responseDto = notificationController.registerUser(requestDto);
        assertNotNull(responseDto, "Response should not be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus(), "Response status should be success");
        assertNull(responseDto.getNotification(), "Notification should be null");
        assertEquals(0, notificationRepository.count(), "Notification should not be created");
    }

    @Test
    public void testDeregisterUser_Success(){
        Product product = products.get(0);
        inventoryRepository.findByProduct(product).ifPresent(inventory -> {
            inventory.setQuantity(0);
            inventoryRepository.save(inventory);
        });
        RegisterUserForNotificationRequestDto requestDto = new RegisterUserForNotificationRequestDto();
        requestDto.setProductId(products.get(0).getId());
        requestDto.setUserId(user.getId());

        RegisterUserForNotificationResponseDto responseDto = notificationController.registerUser(requestDto);
        Notification notification = responseDto.getNotification();

        DeregisterUserForNotificationRequestDto deregisterRequestDto = new DeregisterUserForNotificationRequestDto();
        deregisterRequestDto.setNotificationId(notification.getId());
        deregisterRequestDto.setUserId(user.getId());

        DeregisterUserForNotificationResponseDto deregisterUserForNotificationResponseDto = notificationController.deregisterUser(deregisterRequestDto);
        assertNotNull(deregisterUserForNotificationResponseDto, "Response should not be null");
        assertEquals(ResponseStatus.SUCCESS, deregisterUserForNotificationResponseDto.getResponseStatus(), "Response status should be success");
        assertEquals(0, notificationRepository.count(), "Notification should be deleted");

    }

    @Test
    public void testDeregisterUser_UserNotFound_Failure(){
        Product product = products.get(0);
        inventoryRepository.findByProduct(product).ifPresent(inventory -> {
            inventory.setQuantity(0);
            inventoryRepository.save(inventory);
        });
        RegisterUserForNotificationRequestDto requestDto = new RegisterUserForNotificationRequestDto();
        requestDto.setProductId(products.get(0).getId());
        requestDto.setUserId(user.getId());

        RegisterUserForNotificationResponseDto responseDto = notificationController.registerUser(requestDto);
        Notification notification = responseDto.getNotification();

        DeregisterUserForNotificationRequestDto deregisterRequestDto = new DeregisterUserForNotificationRequestDto();
        deregisterRequestDto.setNotificationId(notification.getId());
        deregisterRequestDto.setUserId(user.getId()*100);

        DeregisterUserForNotificationResponseDto deregisterUserForNotificationResponseDto = notificationController.deregisterUser(deregisterRequestDto);
        assertNotNull(deregisterUserForNotificationResponseDto, "Response should not be null");
        assertEquals(ResponseStatus.FAILURE, deregisterUserForNotificationResponseDto.getResponseStatus(), "Response status should be failure");
        assertEquals(1, notificationRepository.count(), "Notification should not be deleted");
    }


    @Test
    public void testDeregisterUser_NotificationNotFound_Failure(){
        Product product = products.get(0);
        inventoryRepository.findByProduct(product).ifPresent(inventory -> {
            inventory.setQuantity(0);
            inventoryRepository.save(inventory);
        });
        RegisterUserForNotificationRequestDto requestDto = new RegisterUserForNotificationRequestDto();
        requestDto.setProductId(products.get(0).getId());
        requestDto.setUserId(user.getId());

        RegisterUserForNotificationResponseDto responseDto = notificationController.registerUser(requestDto);
        Notification notification = responseDto.getNotification();

        DeregisterUserForNotificationRequestDto deregisterRequestDto = new DeregisterUserForNotificationRequestDto();
        deregisterRequestDto.setNotificationId(notification.getId()*100);
        deregisterRequestDto.setUserId(user.getId());

        DeregisterUserForNotificationResponseDto deregisterUserForNotificationResponseDto = notificationController.deregisterUser(deregisterRequestDto);
        assertNotNull(deregisterUserForNotificationResponseDto, "Response should not be null");
        assertEquals(ResponseStatus.FAILURE, deregisterUserForNotificationResponseDto.getResponseStatus(), "Response status should be failure");
        assertEquals(1, notificationRepository.count(), "Notification should not be deleted");
    }


    @Test
    public void testDeregisterUser_NotificationDoesntBelongToUser_Failure(){
        Product product = products.get(0);
        inventoryRepository.findByProduct(product).ifPresent(inventory -> {
            inventory.setQuantity(0);
            inventoryRepository.save(inventory);
        });
        RegisterUserForNotificationRequestDto requestDto = new RegisterUserForNotificationRequestDto();
        requestDto.setProductId(products.get(0).getId());
        requestDto.setUserId(user.getId());

        RegisterUserForNotificationResponseDto responseDto = notificationController.registerUser(requestDto);
        Notification notification = responseDto.getNotification();

        DeregisterUserForNotificationRequestDto deregisterRequestDto = new DeregisterUserForNotificationRequestDto();
        deregisterRequestDto.setNotificationId(notification.getId());
        deregisterRequestDto.setUserId(user2.getId());

        DeregisterUserForNotificationResponseDto deregisterUserForNotificationResponseDto = notificationController.deregisterUser(deregisterRequestDto);
        assertNotNull(deregisterUserForNotificationResponseDto, "Response should not be null");
        assertEquals(ResponseStatus.FAILURE, deregisterUserForNotificationResponseDto.getResponseStatus(), "Response status should be failure");
        assertEquals(1, notificationRepository.count(), "Notification should not be deleted");
    }

    @Test
    public void testUpdateStockWithNotifications_UsesAdapter(){
        Reflections reflections = new Reflections(InventoryService.class.getPackageName(), new SubTypesScanner(false));
        Set<Class<? extends InventoryService>> implementations = reflections.getSubTypesOf(InventoryService.class);
        assertEquals(1, implementations.size(), "Only one implementation of NotificationService should exist. Please modify the existing implementation instead of creating a new one.");
        Class<? extends InventoryService> notificationServiceClass = implementations.iterator().next();
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


    @Test
    public void testUpdateStockWithNotifications_Success(){
        Product product = products.get(0);
        inventoryRepository.findByProduct(product).ifPresent(inventory -> {
            inventory.setQuantity(0);
            inventoryRepository.save(inventory);
        });
        RegisterUserForNotificationRequestDto requestDto = new RegisterUserForNotificationRequestDto();
        requestDto.setProductId(products.get(0).getId());
        requestDto.setUserId(user.getId());

        notificationController.registerUser(requestDto);

        UpdateInventoryRequestDto updateInventoryRequestDto = new UpdateInventoryRequestDto();
        updateInventoryRequestDto.setProductId(product.getId());
        updateInventoryRequestDto.setQuantity(10);

        UpdateInventoryResponseDto updateInventoryResponseDto = inventoryController.updateInventory(updateInventoryRequestDto);
        assertNotNull(updateInventoryResponseDto, "Response should not be null");
        assertEquals(ResponseStatus.SUCCESS, updateInventoryResponseDto.getResponseStatus(), "Response status should be success");
        List<Notification> notifications = notificationRepository.findByProduct(product);
        assertEquals(1, notifications.size(), "Notification should not be deleted");
        assertEquals(NotificationStatus.SENT, notifications.get(0).getStatus(), "Notification status should be notified");
    }

    @Test
    public void testUpdateStockWithNotifications_ProductNotFound_Failure(){

        UpdateInventoryRequestDto updateInventoryRequestDto = new UpdateInventoryRequestDto();
        updateInventoryRequestDto.setProductId(products.get(0).getId()* 100);
        updateInventoryRequestDto.setQuantity(10);

        UpdateInventoryResponseDto updateInventoryResponseDto = inventoryController.updateInventory(updateInventoryRequestDto);
        assertNotNull(updateInventoryResponseDto, "Response should not be null");
        assertEquals(ResponseStatus.FAILURE, updateInventoryResponseDto.getResponseStatus(), "Response status should be failure");
    }



}
