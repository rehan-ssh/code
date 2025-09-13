package com.example.scaler.dtos;

import com.example.scaler.models.LearnerQuestionResponse;
import lombok.Data;

@Data
public class AnswerQuestionResponseDto {
    private ResponseStatus responseStatus;
    private LearnerQuestionResponse response;
}
