package com.example.scaler.services;

import com.example.scaler.exceptions.InvalidExamException;
import com.example.scaler.exceptions.InvalidLearnerException;
import com.example.scaler.models.LearnerExam;
import com.example.scaler.models.LearnerQuestionResponse;

public interface MCQExamService {

    public LearnerExam startExam(Long learnerId, Long examId) throws InvalidLearnerException, InvalidExamException;

    public LearnerExam submitExam(Long learnerId, Long examId) throws InvalidLearnerException, InvalidExamException;

    public LearnerQuestionResponse answerQuestion(Long learnerId, Long questionId, Long optionId) throws InvalidLearnerException, InvalidExamException;
}
