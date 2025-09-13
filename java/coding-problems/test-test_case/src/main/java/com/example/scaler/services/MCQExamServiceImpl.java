package com.example.scaler.services;

import com.example.scaler.exceptions.InvalidExamException;
import com.example.scaler.exceptions.InvalidLearnerException;
import com.example.scaler.models.*;
import com.example.scaler.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MCQExamServiceImpl implements MCQExamService{

    private LearnerRepository learnerRepository;
    private ExamRepository examRepository;
    private LearnerExamRepository learnerExamRepository;
    private QuestionRepository questionRepository;
    private OptionRepository optionRepository;
    private LearnerQuestionResponseRepository learnerQuestionResponseRepository;

    @Autowired
    public MCQExamServiceImpl(LearnerRepository learnerRepository, ExamRepository examRepository, LearnerExamRepository learnerExamRepository, QuestionRepository questionRepository, OptionRepository optionRepository, LearnerQuestionResponseRepository learnerQuestionResponseRepository) {
        this.learnerRepository = learnerRepository;
        this.examRepository = examRepository;
        this.learnerExamRepository = learnerExamRepository;
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
        this.learnerQuestionResponseRepository = learnerQuestionResponseRepository;
    }

    @Override
    public LearnerExam startExam(Long learnerId, Long examId) throws InvalidLearnerException, InvalidExamException {
        Learner learner = this.learnerRepository.findById(learnerId).orElseThrow(() -> new InvalidLearnerException("Learner not found"));

        Exam exam = this.examRepository.findById(examId).orElseThrow(() -> new InvalidExamException("Exam not found"));

        Optional<LearnerExam> optional = this.learnerExamRepository.findByLearnerIdAndExamId(learnerId, examId);
        if(optional.isPresent()){
            throw new InvalidExamException("Exam already started");
        }
        LearnerExam learnerExam = new LearnerExam();
        learnerExam.setLearner(learner);
        learnerExam.setExam(exam);
        learnerExam.setStartedAt(new Date());
        learnerExam.setStatus(ExamStatus.STARTED);
        return this.learnerExamRepository.save(learnerExam);
    }

    @Override
    public LearnerExam submitExam(Long learnerId, Long examId) throws InvalidLearnerException, InvalidExamException {
        Learner learner = this.learnerRepository.findById(learnerId).orElseThrow(() -> new InvalidLearnerException("Learner not found"));

        Exam exam = this.examRepository.findById(examId).orElseThrow(() -> new InvalidExamException("Exam not found"));

        LearnerExam learnerExam = this.learnerExamRepository.findByLearnerIdAndExamId(learnerId, examId).orElseThrow(() -> new InvalidExamException("Exam not started"));

        if(learnerExam.getStatus() == ExamStatus.ENDED){
            throw new InvalidExamException("Exam already submitted");
        }
        learnerExam.setEndedAt(new Date());
        learnerExam.setStatus(ExamStatus.ENDED);

        //Calculate score
        int obtainedScore = 0;
        List<Question> questions = this.questionRepository.findByExamId(examId);
        Map<Long, Question> questionMap = questions.stream().collect(Collectors.toMap(Question::getId, question -> question));
        List<Long> questionIds = questions.stream().map(Question::getId).toList();
        List<LearnerQuestionResponse> learnerResponses = this.learnerQuestionResponseRepository.findByLearnerIdAndQuestionIdIn(learnerId, questionIds);
        for(LearnerQuestionResponse learnerResponse : learnerResponses){
            long questionId = learnerResponse.getQuestion().getId();
            long correctOptionId = questionMap.get(questionId).getCorrectOption().getId();
            if(learnerResponse.getOption().getId() == correctOptionId){
                obtainedScore += questionMap.get(questionId).getScore();
            }
        }
        learnerExam.setScoreObtained(obtainedScore);
        return learnerExamRepository.save(learnerExam);
    }

    @Override
    public LearnerQuestionResponse answerQuestion(Long learnerId, Long questionId, Long optionId) throws InvalidLearnerException, InvalidExamException {
        Learner learner = this.learnerRepository.findById(learnerId).orElseThrow(() -> new InvalidLearnerException("Learner not found"));

        Question question = this.questionRepository.findById(questionId).orElseThrow(() -> new InvalidExamException("Question not found"));

        this.examRepository.findById(question.getExam().getId()).orElseThrow(() -> new InvalidExamException("Exam not found"));

        Option option = this.optionRepository.findById(optionId).orElseThrow(() -> new InvalidExamException("Option not found"));

        if(question.getOptions().stream().map(Option::getId).noneMatch(id -> id.equals(optionId))){
            throw new InvalidExamException("Option not found in question");
        }

        Optional<LearnerQuestionResponse> optionalAttempt = this.learnerQuestionResponseRepository.findByLearnerIdAndQuestionId(learnerId, questionId);
        LearnerQuestionResponse lqr = optionalAttempt.orElseGet(LearnerQuestionResponse::new);
        lqr.setLearner(learner);
        lqr.setQuestion(question);
        lqr.setOption(option);
        return learnerQuestionResponseRepository.save(lqr);
    }
}
