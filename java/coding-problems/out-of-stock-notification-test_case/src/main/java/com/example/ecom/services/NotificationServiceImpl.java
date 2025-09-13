package com.example.ecom.services;

import com.example.ecom.exceptions.*;
import com.example.ecom.models.*;
import com.example.ecom.repositories.InventoryRepository;
import com.example.ecom.repositories.NotificationRepository;
import com.example.ecom.repositories.ProductRepository;
import com.example.ecom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService{

    private UserRepository userRepository;
    private NotificationRepository notificationRepository;
    private ProductRepository productRepository;
    private InventoryRepository inventoryRepository;

    @Autowired
    public NotificationServiceImpl(UserRepository userRepository, NotificationRepository notificationRepository, ProductRepository productRepository, InventoryRepository inventoryRepository) {
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Notification registerUser(int userId, int productId) throws UserNotFoundException, ProductNotFoundException, ProductInStockException {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        Inventory inventory = this.inventoryRepository.findByProduct(product).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        if(inventory.getQuantity() != 0){
            throw new ProductInStockException("Product is in stock");
        }
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setProduct(product);
        notification.setStatus(NotificationStatus.PENDING);
        return this.notificationRepository.save(notification);
    }

    @Override
    public void deregisterUser(int userId, int notificationId) throws UserNotFoundException, NotificationNotFoundException, UnAuthorizedException {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Notification notification = this.notificationRepository.findById(notificationId).orElseThrow(() -> new NotificationNotFoundException("No notification found"));
        if(notification.getUser().getId() != user.getId()){
            throw new UnAuthorizedException("Notification doesnot belong to user");
        }
        this.notificationRepository.delete(notification);
    }
}
