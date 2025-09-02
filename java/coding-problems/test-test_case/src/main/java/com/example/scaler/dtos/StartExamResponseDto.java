package com.example.scaler.dtos;

import com.example.scaler.models.LearnerExam;
import lombok.Data;

@Data
public class StartExamResponseDto {
    private ResponseStatus responseStatus;
    private LearnerExam learnerExam;
}
