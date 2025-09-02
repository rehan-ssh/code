package com.example.scaler.repositories;

import com.example.scaler.models.LearnerQuestionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LearnerQuestionResponseRepository extends JpaRepository<LearnerQuestionResponse, Long> {

    Optional<LearnerQuestionResponse> findByLearnerIdAndQuestionId(Long learnerId, Long questionId);

    List<LearnerQuestionResponse> findByLearnerIdAndQuestionIdIn(Long learnerId, List<Long> questionIds);
}
