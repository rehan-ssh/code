package com.example.ecom.dtos;

import com.example.ecom.models.Order;
import lombok.Data;

@Data
public class PlaceOrderResponseDto {
    private Order order;
    private ResponseStatus status;
}
