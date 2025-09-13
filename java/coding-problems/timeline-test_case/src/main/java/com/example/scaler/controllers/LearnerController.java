package com.example.scaler.controllers;

import com.example.scaler.dtos.FetchTimelineRequestDto;
import com.example.scaler.dtos.FetchTimelineResponseDto;
import com.example.scaler.dtos.ResponseStatus;
import com.example.scaler.services.LearnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class LearnerController {

    private final LearnerService learnerService;

    @Autowired
    public LearnerController(LearnerService learnerService) {
        this.learnerService = learnerService;
    }

    public FetchTimelineResponseDto fetchTimeline(FetchTimelineRequestDto requestDto){
        FetchTimelineResponseDto responseDto = new FetchTimelineResponseDto();
        try{
            responseDto.setLectures(learnerService.fetchTimeline(requestDto.getLearnerId()));
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
}
