package com.example.ecom.dtos;

import lombok.Data;

@Data
public class RegisterUserForNotificationRequestDto {
    private int userId;
    private int productId;
}
