package com.example.ecom.services;

import com.example.ecom.adapters.EmailAdapter;
import com.example.ecom.exceptions.ProductNotFoundException;
import com.example.ecom.models.Inventory;
import com.example.ecom.models.Notification;
import com.example.ecom.models.NotificationStatus;
import com.example.ecom.models.Product;
import com.example.ecom.repositories.InventoryRepository;
import com.example.ecom.repositories.NotificationRepository;
import com.example.ecom.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService{

    private InventoryRepository inventoryRepository;
    private ProductRepository productRepository;

    private NotificationRepository notificationRepository;

    private EmailAdapter emailAdapter;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository, ProductRepository productRepository, NotificationRepository notificationRepository, EmailAdapter emailAdapter) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.notificationRepository = notificationRepository;
        this.emailAdapter = emailAdapter;
    }

    @Override
    public Inventory updateInventory(int productId, int quantity) throws ProductNotFoundException {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        Optional<Inventory> inventoryOptional = this.inventoryRepository.findByProduct(product);
        Inventory inventory;
        if(inventoryOptional.isEmpty()){
            inventory = new Inventory();
            inventory.setProduct(product);
            inventory.setQuantity(quantity);
            return this.inventoryRepository.save(inventory);
        }
        inventory = inventoryOptional.get();
        inventory.setQuantity(inventory.getQuantity() + quantity);
        //Send notification to users
        List<Notification> notifications = notificationRepository.findByProduct(product);
        for (Notification notification : notifications) {
            String emailBody = String.format("Dear %s, %s is now back in stock. Grab it ASAP!", notification.getUser().getName(), notification.getProduct().getName());
            String subject = String.format("%s back in stock", notification.getProduct().getName());
            emailAdapter.sendEmail(notification.getUser().getEmail(), subject, emailBody);
            notification.setStatus(NotificationStatus.SENT);
            notificationRepository.save(notification);
        }
        return this.inventoryRepository.save(inventory);
    }
}
