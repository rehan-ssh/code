package com.example.ecom.dtos;

import lombok.Data;

@Data
public class UpdateInventoryRequestDto {
    private int productId;
    private int quantity;
}
