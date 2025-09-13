package com.example.ecom.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class DeliveryEstimateResponseDto {
    private Date expectedDeliveryDate;
    private ResponseStatus responseStatus;
}
