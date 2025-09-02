package com.example.scaler.dtos;

import com.example.scaler.models.Communication;
import lombok.Data;

@Data
public class BroadcastMessageResponseDto {

    private ResponseStatus status;
    private Communication communication;
}
