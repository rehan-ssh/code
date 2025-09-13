package com.example.ecom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class DeliveryHub extends BaseModel{
    @OneToOne
    private Address address;
    private String name;
}
