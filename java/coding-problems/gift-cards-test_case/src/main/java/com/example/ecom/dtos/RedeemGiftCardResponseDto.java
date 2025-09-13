package com.example.ecom.dtos;

import com.example.ecom.models.GiftCard;
import lombok.Data;

@Data
public class RedeemGiftCardResponseDto {
    private GiftCard giftCard;
    private ResponseStatus responseStatus;
}
