package com.example.ecom.services;

import com.example.ecom.exceptions.AddressNotFoundException;
import com.example.ecom.exceptions.ProductNotFoundException;

import java.util.Date;

public interface ProductService {
    public Date estimateDeliveryDate(int productId, int addressId) throws ProductNotFoundException, AddressNotFoundException;
}
