package com.example.ecom.dtos;

import com.example.ecom.models.GiftCard;
import lombok.Data;

@Data
public class CreateGiftCardResponseDto {
    private GiftCard giftCard;
    private ResponseStatus responseStatus;
}
