package com.example.scaler.dtos;

import lombok.Data;

@Data
public class BroadcastMessageRequestDto {
    private String message;
    private long userId;
    private long batchId;
}
