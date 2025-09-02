package com.example.ecom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Notification extends BaseModel{
    @ManyToOne
    private Product product;
    @ManyToOne
    private User user;
    private NotificationStatus status;
}
