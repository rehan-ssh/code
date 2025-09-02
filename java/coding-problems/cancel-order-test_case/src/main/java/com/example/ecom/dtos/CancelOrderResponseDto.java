package com.example.ecom.dtos;

import com.example.ecom.models.Order;
import lombok.Data;

@Data
public class CancelOrderResponseDto {
    private ResponseStatus status;
    private Order order;
}
