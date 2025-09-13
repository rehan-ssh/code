package com.example.splitwise.controllers;


import com.example.splitwise.dtos.*;
import com.example.splitwise.models.Transaction;
import com.example.splitwise.services.SettleUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class SettleUpController {

    public SettleUpService settleUpService;

    @Autowired
    public SettleUpController(SettleUpService settleUpService) {
        this.settleUpService = settleUpService;
    }

    public SettleGroupResponseDto settleGroup(SettleGroupRequestDto dto){
        SettleGroupResponseDto responseDto = new SettleGroupResponseDto();
        try {
            List<Transaction> transactions = settleUpService.settleGroup(dto.getGroupId());
            responseDto.setTransactions(transactions);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }

    public SettleUserResponseDto settleUser(SettleUserRequestDto requestDto){
        SettleUserResponseDto responseDto = new SettleUserResponseDto();
        try{
            List<Transaction> transactions = settleUpService.settleUser(requestDto.getUserId());
            responseDto.setTransactions(transactions);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }catch (Exception e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
}
