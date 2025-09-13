package com.example.ecom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "products")
public class Product extends BaseModel{
    private String name;
    private String description;
    private double price;
    @ManyToOne(fetch = FetchType.EAGER)
    private Seller seller;
}
