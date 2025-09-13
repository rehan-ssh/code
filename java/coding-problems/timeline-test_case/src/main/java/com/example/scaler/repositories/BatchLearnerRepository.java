package com.example.scaler.repositories;

import com.example.scaler.models.BatchLearner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchLearnerRepository extends JpaRepository<BatchLearner, Long> {

    List<BatchLearner> findByLearnerIdOrderByEntryDate(Long learnerId);
}
