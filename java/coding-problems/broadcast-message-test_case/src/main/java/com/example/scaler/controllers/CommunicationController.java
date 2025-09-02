package com.example.scaler.controllers;

import com.example.scaler.dtos.BroadcastMessageRequestDto;
import com.example.scaler.dtos.BroadcastMessageResponseDto;
import com.example.scaler.dtos.ResponseStatus;
import com.example.scaler.models.Communication;
import com.example.scaler.services.CommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CommunicationController {

    private final CommunicationService communicationService;

    @Autowired
    public CommunicationController(CommunicationService communicationService) {
        this.communicationService = communicationService;
    }

    public BroadcastMessageResponseDto broadcastMessage(BroadcastMessageRequestDto requestDto) {
        BroadcastMessageResponseDto response = new BroadcastMessageResponseDto();
        try {
            Communication communication = communicationService.broadcastMessage(requestDto.getBatchId(), requestDto.getUserId(), requestDto.getMessage());
            response.setStatus(ResponseStatus.SUCCESS);
            response.setCommunication(communication);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(ResponseStatus.FAILURE);
        }
        return response;
    }
}
