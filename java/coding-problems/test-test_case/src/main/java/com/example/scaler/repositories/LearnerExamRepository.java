package com.example.scaler.repositories;

import com.example.scaler.models.LearnerExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LearnerExamRepository extends JpaRepository<LearnerExam, Long> {

    Optional<LearnerExam> findByLearnerIdAndExamId(Long learnerId, Long examId);
}
