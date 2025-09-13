package com.example.scaler.controllers;

import com.example.scaler.dtos.RescheduleScheduledLectureRequestDto;
import com.example.scaler.dtos.RescheduleScheduledLectureResponseDto;
import com.example.scaler.dtos.ResponseStatus;
import com.example.scaler.models.ScheduledLecture;
import com.example.scaler.services.ScheduledLectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ScheduledLectureController {

    private ScheduledLectureService scheduledLectureService;

    @Autowired
    public ScheduledLectureController(ScheduledLectureService scheduledLectureService) {
        this.scheduledLectureService = scheduledLectureService;
    }

    public RescheduleScheduledLectureResponseDto rescheduleScheduledLecture(RescheduleScheduledLectureRequestDto requestDto) {
        RescheduleScheduledLectureResponseDto responseDto = new RescheduleScheduledLectureResponseDto();
        try{
            List<ScheduledLecture> scheduledLectures = scheduledLectureService.rescheduleScheduledLecture(requestDto.getScheduledLectureId());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            responseDto.setScheduledLectures(scheduledLectures);
        } catch (Exception e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
}
