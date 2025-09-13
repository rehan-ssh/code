package com.example.ecom.services;

import com.example.ecom.exceptions.GiftCardDoesntExistException;
import com.example.ecom.exceptions.GiftCardExpiredException;
import com.example.ecom.models.GiftCard;

public interface GiftCardService {
    public GiftCard createGiftCard(double amount);

    public GiftCard redeemGiftCard(int giftCardId, double amountToRedeem) throws GiftCardDoesntExistException, GiftCardExpiredException;
}
