package com.example.ecom.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "order_details")
public class OrderDetail extends BaseModel{
    @ManyToOne
    private Order order;
    @ManyToOne
    private Product product;
    private int quantity;
}
