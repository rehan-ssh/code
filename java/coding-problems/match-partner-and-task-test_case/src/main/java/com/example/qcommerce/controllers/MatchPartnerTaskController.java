package com.example.qcommerce.controllers;

import com.example.qcommerce.dtos.MatchPartnerTaskRequestDto;
import com.example.qcommerce.dtos.MatchPartnerTaskResponseDto;
import com.example.qcommerce.dtos.ResponseStatus;
import com.example.qcommerce.models.PartnerTaskMapping;
import com.example.qcommerce.services.MatchPartnerTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MatchPartnerTaskController {

    private MatchPartnerTaskService matchPartnerTaskService;

    @Autowired
    public MatchPartnerTaskController(MatchPartnerTaskService matchPartnerTaskService) {
        this.matchPartnerTaskService = matchPartnerTaskService;
    }

    public MatchPartnerTaskResponseDto matchPartnersAndTasks(MatchPartnerTaskRequestDto requestDto){
        MatchPartnerTaskResponseDto responseDto = new MatchPartnerTaskResponseDto();
        try {
            List<PartnerTaskMapping> partnerTaskMappings = matchPartnerTaskService.matchPartnersAndTasks(requestDto.getPartnerIds(), requestDto.getTaskIds());
            responseDto.setPartnerTaskMappings(partnerTaskMappings);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            return responseDto;
        }catch (Exception e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            return responseDto;
        }
    }
}
