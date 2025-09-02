package com.example.scaler.dtos;

import lombok.Data;

@Data
public class AnswerQuestionRequestDto {
    private long learnerId;
    private long questionId;
    private long optionId;
}
