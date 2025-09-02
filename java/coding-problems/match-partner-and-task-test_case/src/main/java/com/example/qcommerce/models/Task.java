package com.example.qcommerce.models;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Task extends BaseModel{
    private long customerId;
    @Embedded
    private Location pickupLocation;
}
