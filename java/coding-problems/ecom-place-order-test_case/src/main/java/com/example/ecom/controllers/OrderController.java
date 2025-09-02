package com.example.ecom.controllers;

import com.example.ecom.dtos.PlaceOrderRequestDto;
import com.example.ecom.dtos.PlaceOrderResponseDto;
import com.example.ecom.dtos.ResponseStatus;
import com.example.ecom.models.Order;
import com.example.ecom.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public PlaceOrderResponseDto placeOrder(PlaceOrderRequestDto placeOrderRequestDto) {
        PlaceOrderResponseDto placeOrderResponseDto = new PlaceOrderResponseDto();
        try{
            Order order = orderService.placeOrder(placeOrderRequestDto.getUserId(), placeOrderRequestDto.getAddressId(), placeOrderRequestDto.getOrderDetails());
            placeOrderResponseDto.setOrder(order);
            placeOrderResponseDto.setStatus(ResponseStatus.SUCCESS);
            return placeOrderResponseDto;
        } catch (Exception e) {
            e.printStackTrace();
            placeOrderResponseDto.setStatus(ResponseStatus.FAILURE);
            return placeOrderResponseDto;
        }
    }

}
