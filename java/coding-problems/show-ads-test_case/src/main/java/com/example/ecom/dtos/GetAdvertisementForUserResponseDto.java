package com.example.ecom.dtos;

import com.example.ecom.models.Advertisement;
import lombok.Data;

@Data
public class GetAdvertisementForUserResponseDto {
    private Advertisement advertisement;
    private ResponseStatus responseStatus;
}
