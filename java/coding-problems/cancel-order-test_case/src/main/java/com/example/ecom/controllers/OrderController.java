package com.example.ecom.controllers;

import com.example.ecom.dtos.CancelOrderRequestDto;
import com.example.ecom.dtos.CancelOrderResponseDto;
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

    public CancelOrderResponseDto cancelOrder(CancelOrderRequestDto cancelOrderRequestDto) {
        CancelOrderResponseDto cancelOrderResponseDto = new CancelOrderResponseDto();
        try{
            Order order = orderService.cancelOrder(cancelOrderRequestDto.getOrderId(), cancelOrderRequestDto.getUserId());
            cancelOrderResponseDto.setOrder(order);
            cancelOrderResponseDto.setStatus(ResponseStatus.SUCCESS);
            return cancelOrderResponseDto;
        } catch (Exception e) {
            e.printStackTrace();
            cancelOrderResponseDto.setStatus(ResponseStatus.FAILURE);
            return cancelOrderResponseDto;
        }
    }

}
