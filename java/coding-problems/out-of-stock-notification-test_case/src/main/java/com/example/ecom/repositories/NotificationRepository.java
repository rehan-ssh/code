package com.example.ecom.repositories;

import com.example.ecom.models.Notification;
import com.example.ecom.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findByProduct(Product product);

}
