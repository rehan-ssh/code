package com.example.ecom.controllers;

import com.example.ecom.dtos.*;
import com.example.ecom.models.Notification;
import com.example.ecom.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    private NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public RegisterUserForNotificationResponseDto registerUser(RegisterUserForNotificationRequestDto requestDto) {
        RegisterUserForNotificationResponseDto responseDto = new RegisterUserForNotificationResponseDto();
        try {
            Notification notification = notificationService.registerUser(requestDto.getUserId(), requestDto.getProductId());
            responseDto.setNotification(notification);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e){
            e.printStackTrace();
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }

    public DeregisterUserForNotificationResponseDto deregisterUser(DeregisterUserForNotificationRequestDto requestDto) {
        DeregisterUserForNotificationResponseDto responseDto = new DeregisterUserForNotificationResponseDto();
        try {
            notificationService.deregisterUser(requestDto.getUserId(), requestDto.getNotificationId());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
}
