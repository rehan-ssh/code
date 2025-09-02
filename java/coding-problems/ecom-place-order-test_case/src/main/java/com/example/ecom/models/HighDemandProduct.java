package com.example.ecom.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity(name = "high_demand_products")
public class HighDemandProduct extends BaseModel{
    @OneToOne
    private Product product;
    private int maxQuantity;
}
