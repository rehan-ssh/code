package com.example.ecom.controllers;

import com.example.ecom.dtos.ResponseStatus;
import com.example.ecom.dtos.UpdateInventoryRequestDto;
import com.example.ecom.dtos.UpdateInventoryResponseDto;
import com.example.ecom.models.Inventory;
import com.example.ecom.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class InventoryController {

    private InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public UpdateInventoryResponseDto updateInventory(UpdateInventoryRequestDto requestDto) {
        UpdateInventoryResponseDto responseDto = new UpdateInventoryResponseDto();
        try{
            Inventory inventory = inventoryService.updateInventory(requestDto.getProductId(), requestDto.getQuantity());
            responseDto.setInventory(inventory);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            return responseDto;
        } catch (Exception e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            return responseDto;
        }
    }
}
