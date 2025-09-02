package com.example.scaler.controllers;

import com.example.scaler.dtos.ResponseStatus;
import com.example.scaler.dtos.ScheduleLectureRequestDto;
import com.example.scaler.dtos.ScheduleLecturesResponseDto;
import com.example.scaler.models.ScheduledLecture;
import com.example.scaler.services.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class LectureController {

    private LectureService lectureService;

    @Autowired
    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    public ScheduleLecturesResponseDto scheduleLectures(ScheduleLectureRequestDto requestDto) {
        ScheduleLecturesResponseDto responseDto = new ScheduleLecturesResponseDto();
        try{
            List<ScheduledLecture> scheduledLectures = lectureService.scheduleLectures(requestDto.getLectureIds(), requestDto.getInstructorId(), requestDto.getBatchId());
            responseDto.setScheduledLectures(scheduledLectures);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
}
