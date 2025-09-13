package com.example.ecom.dtos;

import com.example.ecom.models.Inventory;
import lombok.Data;

@Data
public class UpdateInventoryResponseDto {
    private Inventory inventory;
    private ResponseStatus responseStatus;
}
