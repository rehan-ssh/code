package com.example.ecom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity(name = "inventory")
public class Inventory extends BaseModel{
    @OneToOne
    private Product product;
    private int quantity;
}
