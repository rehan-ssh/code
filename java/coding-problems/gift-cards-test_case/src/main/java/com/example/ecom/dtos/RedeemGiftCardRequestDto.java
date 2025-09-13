package com.example.ecom.dtos;

import lombok.Data;

@Data
public class RedeemGiftCardRequestDto {
    private double amountToRedeem;
    private int giftCardId;
}
