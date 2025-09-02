package com.example.ecom.dtos;

import com.example.ecom.models.Notification;
import lombok.Data;

@Data
public class RegisterUserForNotificationResponseDto {
    private Notification notification;
    private ResponseStatus responseStatus;
}
