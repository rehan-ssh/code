package com.example.ecom.controllers;

import com.example.ecom.dtos.*;
import com.example.ecom.services.GiftCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GiftCardController {

    public GiftCardService giftCardService;

    @Autowired
    public GiftCardController(GiftCardService giftCardService) {
        this.giftCardService = giftCardService;
    }

    public CreateGiftCardResponseDto createGiftCard(CreateGiftCardRequestDto requestDto){
        CreateGiftCardResponseDto responseDto = new CreateGiftCardResponseDto();
        responseDto.setGiftCard(giftCardService.createGiftCard(requestDto.getAmount()));
        responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return responseDto;
    }

    public RedeemGiftCardResponseDto redeemGiftCard(RedeemGiftCardRequestDto requestDto){
        RedeemGiftCardResponseDto responseDto = new RedeemGiftCardResponseDto();
        try {
            responseDto.setGiftCard(giftCardService.redeemGiftCard(requestDto.getGiftCardId(), requestDto.getAmountToRedeem()));
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
}
