package com.example.scaler.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ScheduleLectureRequestDto {

    private List<Long> lectureIds;
    private Long instructorId;
    private Long batchId;
}
