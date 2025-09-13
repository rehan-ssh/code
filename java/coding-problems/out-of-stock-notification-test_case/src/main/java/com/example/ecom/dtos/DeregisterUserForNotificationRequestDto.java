package com.example.ecom.dtos;

import lombok.Data;

@Data
public class DeregisterUserForNotificationRequestDto {
    private int userId;
    private int notificationId;
}
