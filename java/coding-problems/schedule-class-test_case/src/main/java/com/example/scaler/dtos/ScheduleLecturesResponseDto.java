package com.example.scaler.dtos;

import com.example.scaler.models.ScheduledLecture;
import lombok.Data;

import java.util.List;

@Data
public class ScheduleLecturesResponseDto {
    private List<ScheduledLecture> scheduledLectures;
    private ResponseStatus responseStatus;
}
