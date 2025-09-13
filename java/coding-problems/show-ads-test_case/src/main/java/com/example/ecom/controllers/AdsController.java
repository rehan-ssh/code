package com.example.ecom.controllers;

import com.example.ecom.dtos.GetAdvertisementForUserRequestDto;
import com.example.ecom.dtos.GetAdvertisementForUserResponseDto;
import com.example.ecom.dtos.ResponseStatus;
import com.example.ecom.exceptions.UserNotFoundException;
import com.example.ecom.models.Advertisement;
import com.example.ecom.services.AdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AdsController {

    private AdsService adsService;

    @Autowired
    public AdsController(AdsService adsService) {
        this.adsService = adsService;
    }

    public GetAdvertisementForUserResponseDto getAdvertisementForUser(GetAdvertisementForUserRequestDto requestDto){
        GetAdvertisementForUserResponseDto responseDto = new GetAdvertisementForUserResponseDto();
        try{
            Advertisement advertisement = adsService.getAdvertisementForUser(requestDto.getUserId());
            responseDto.setAdvertisement(advertisement);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            return responseDto;
        } catch (UserNotFoundException e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            return responseDto;
        }
    }
}
