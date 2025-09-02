package com.example.ecom.services;

import com.example.ecom.exceptions.ProductNotFoundException;
import com.example.ecom.models.Inventory;

public interface InventoryService {

    public Inventory updateInventory(int productId, int quantity) throws ProductNotFoundException;
}
