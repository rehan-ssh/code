package com.example.scaler.controllers;

import com.example.scaler.dtos.*;
import com.example.scaler.models.*;
import com.example.scaler.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TestMCQExamController {

    @Autowired
    private LearnerRepository learnerRepository;
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private LearnerExamRepository learnerExamRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private LearnerQuestionResponseRepository learnerQuestionResponseRepository;
    @Autowired
    private MCQExamController mcqExamController;

    private List<Question> questions;
    private Exam exam;
    private Learner learner;

    @BeforeEach
    public void setupDummyData() {

        exam = new Exam();
        exam.setName("Exam 1");
        exam.setTotalScore(3);
        exam = examRepository.save(exam);

        List<Option> options = getOptions();

        Question question = new Question();
        question.setName("Question 1");
        question.setScore(1);
        question.setExam(exam);
        question.setOptions(options);
        question.setCorrectOption(options.get(0));
        question = questionRepository.save(question);

        options = getOptions();
        Question question2 = new Question();
        question2.setName("Question 2");
        question2.setScore(1);
        question2.setExam(exam);
        question2.setOptions(options);
        question2.setCorrectOption(options.get(1));
        question2 = questionRepository.save(question2);

        options = getOptions();
        Question question3 = new Question();
        question3.setName("Question 3");
        question3.setScore(1);
        question3.setExam(exam);
        question3.setOptions(options);
        question3.setCorrectOption(options.get(2));
        question3 = questionRepository.save(question3);

        questions = Arrays.asList(question, question2, question3);

        learner = new Learner();
        learner.setName("Learner 1");
        learner = learnerRepository.save(learner);
    }

    private List<Option> getOptions(){
        Option option1 = new Option();
        option1.setText("Option 1");
        option1 = optionRepository.save(option1);

        Option option2 = new Option();
        option2.setText("Option 2");
        option2 = optionRepository.save(option2);

        Option option3 = new Option();
        option3.setText("Option 3");
        option3 = optionRepository.save(option3);

        Option option4 = new Option();
        option4.setText("Option 4");
        option4 = optionRepository.save(option4);

        return Arrays.asList(option1, option2, option3, option4);
    }

    @Test
    public void testAll3Functionalities_Success() {
        StartExamRequestDto requestDto = new StartExamRequestDto();
        requestDto.setExamId(exam.getId());
        requestDto.setLearnerId(learner.getId());
        StartExamResponseDto responseDto = mcqExamController.startExam(requestDto);
        assertNotNull(responseDto, "Response dto should not be null");
        assertNotNull(responseDto.getLearnerExam(), "Learner exam obj should not be null");
        assertEquals(ExamStatus.STARTED, responseDto.getLearnerExam().getStatus(), "Exam status should be started");

        AnswerQuestionRequestDto requestDto1 = new AnswerQuestionRequestDto();
        requestDto1.setLearnerId(learner.getId());
        requestDto1.setQuestionId(questions.get(0).getId());
        requestDto1.setOptionId(questions.get(0).getCorrectOption().getId());
        AnswerQuestionResponseDto responseDto1 = mcqExamController.answerQuestion(requestDto1);
        assertNotNull(responseDto1, "Response dto should not be null");
        assertNotNull(responseDto1.getResponse(), "Learner question response obj should not be null");
        assertEquals(questions.get(0).getCorrectOption().getId(), responseDto1.getResponse().getOption().getId(), "Option id should be same");

        AnswerQuestionRequestDto requestDto2 = new AnswerQuestionRequestDto();
        requestDto2.setLearnerId(learner.getId());
        requestDto2.setQuestionId(questions.get(1).getId());
        requestDto2.setOptionId(questions.get(1).getOptions().get(0).getId());
        AnswerQuestionResponseDto responseDto2 = mcqExamController.answerQuestion(requestDto2);

        SubmitExamRequestDto requestDto3 = new SubmitExamRequestDto();
        requestDto3.setLearnerId(learner.getId());
        requestDto3.setExamId(exam.getId());
        SubmitExamResponseDto responseDto3 = mcqExamController.submitExam(requestDto3);
        assertNotNull(responseDto3, "Response dto should not be null");
        assertNotNull(responseDto3.getLearnerExam(), "Learner exam obj should not be null");
        assertEquals(ExamStatus.ENDED, responseDto3.getLearnerExam().getStatus(), "Exam status should be ended");
        assertEquals(1, responseDto3.getLearnerExam().getScoreObtained(), "Score obtained should be 1");
    }



    @Test
    public void testStartExam_LearnerNotFound() {
        StartExamRequestDto requestDto = new StartExamRequestDto();
        requestDto.setExamId(exam.getId());
        requestDto.setLearnerId(learner.getId() * 100);
        StartExamResponseDto  responseDto = mcqExamController.startExam(requestDto);
        assertNotNull(responseDto, "Response dto should not be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus(), "Response status should be failure");
    }

    @Test
    public void testStartExam_ExamNotFound() {
        StartExamRequestDto requestDto = new StartExamRequestDto();
        requestDto.setExamId(exam.getId() * 100);
        requestDto.setLearnerId(learner.getId());
        StartExamResponseDto  responseDto = mcqExamController.startExam(requestDto);
        assertNotNull(responseDto, "Response dto should not be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus(), "Response status should be failure");
    }

    @Test
    public void testStartExam_ExamAlreadyStarted() {
        StartExamRequestDto requestDto = new StartExamRequestDto();
        requestDto.setExamId(exam.getId());
        requestDto.setLearnerId(learner.getId());
        StartExamResponseDto  responseDto = mcqExamController.startExam(requestDto);
        responseDto = mcqExamController.startExam(requestDto);
        assertNotNull(responseDto, "Response dto should not be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus(), "Response status should be failure");

    }

    @Test
    public void answerQuestion_LearnerNotFound() {
        AnswerQuestionRequestDto requestDto = new AnswerQuestionRequestDto();
        requestDto.setLearnerId(learner.getId() * 100);
        requestDto.setQuestionId(questions.get(0).getId());
        requestDto.setOptionId(questions.get(0).getCorrectOption().getId());
        AnswerQuestionResponseDto responseDto = mcqExamController.answerQuestion(requestDto);
        assertNotNull(responseDto, "Response dto should not be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus(), "Response status should be failure");
    }

    @Test
    public void answerQuestion_QuestionNotFound() {
        AnswerQuestionRequestDto requestDto = new AnswerQuestionRequestDto();
        requestDto.setLearnerId(learner.getId());
        requestDto.setQuestionId(questions.get(0).getId() * 100);
        requestDto.setOptionId(questions.get(0).getCorrectOption().getId());
        AnswerQuestionResponseDto responseDto = mcqExamController.answerQuestion(requestDto);
        assertNotNull(responseDto, "Response dto should not be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus(), "Response status should be failure");
    }

    @Test
    public void answerQuestion_OptionNotFound() {
        AnswerQuestionRequestDto requestDto = new AnswerQuestionRequestDto();
        requestDto.setLearnerId(learner.getId());
        requestDto.setQuestionId(questions.get(0).getId());
        requestDto.setOptionId(questions.get(0).getCorrectOption().getId() * 100);
        AnswerQuestionResponseDto responseDto = mcqExamController.answerQuestion(requestDto);
        assertNotNull(responseDto, "Response dto should not be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus(), "Response status should be failure");
    }

    @Test
    public void answerQuestion_OptionDoesNotBelongToQuestion() {
        AnswerQuestionRequestDto requestDto = new AnswerQuestionRequestDto();
        requestDto.setLearnerId(learner.getId());
        requestDto.setQuestionId(questions.get(0).getId());
        requestDto.setOptionId(questions.get(1).getCorrectOption().getId());
        AnswerQuestionResponseDto responseDto = mcqExamController.answerQuestion(requestDto);
        assertNotNull(responseDto, "Response dto should not be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus(), "Response status should be failure");
    }

    @Test
    public void submitTest_LearnerNotFound() {
        SubmitExamRequestDto requestDto = new SubmitExamRequestDto();
        requestDto.setLearnerId(learner.getId() * 100);
        requestDto.setExamId(exam.getId());
        SubmitExamResponseDto responseDto = mcqExamController.submitExam(requestDto);
        assertNotNull(responseDto, "Response dto should not be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus(), "Response status should be failure");
    }

    @Test
    public void submitTest_ExamNotFound() {
        SubmitExamRequestDto requestDto = new SubmitExamRequestDto();
        requestDto.setLearnerId(learner.getId());
        requestDto.setExamId(exam.getId() * 100);
        SubmitExamResponseDto responseDto = mcqExamController.submitExam(requestDto);
        assertNotNull(responseDto, "Response dto should not be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus(), "Response status should be failure");
    }

    @Test
    public void submitTest_ExamNotStarted() {
        SubmitExamRequestDto requestDto = new SubmitExamRequestDto();
        requestDto.setLearnerId(learner.getId());
        requestDto.setExamId(exam.getId());
        SubmitExamResponseDto responseDto = mcqExamController.submitExam(requestDto);
        assertNotNull(responseDto, "Response dto should not be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus(), "Response status should be failure");
    }

    @Test
    public void submitTest_ExamAlreadySubmitted() {
        StartExamRequestDto requestDto = new StartExamRequestDto();
        requestDto.setExamId(exam.getId());
        requestDto.setLearnerId(learner.getId());
        StartExamResponseDto  responseDto = mcqExamController.startExam(requestDto);
        SubmitExamRequestDto requestDto1 = new SubmitExamRequestDto();
        requestDto1.setLearnerId(learner.getId());
        requestDto1.setExamId(exam.getId());
        SubmitExamResponseDto responseDto1 = mcqExamController.submitExam(requestDto1);
        responseDto1 = mcqExamController.submitExam(requestDto1);
        assertNotNull(responseDto1, "Response dto should not be null");
        assertEquals(ResponseStatus.FAILURE, responseDto1.getResponseStatus(), "Response status should be failure");
    }

}
