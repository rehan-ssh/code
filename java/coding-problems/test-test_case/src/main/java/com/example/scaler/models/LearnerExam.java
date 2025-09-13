package com.example.scaler.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

import static jakarta.persistence.EnumType.STRING;

@Data
@Entity
public class LearnerExam extends BaseModel{

    @ManyToOne
    private Learner learner;

    @ManyToOne
    private Exam exam;

    private Date startedAt;

    private Date endedAt;

    @Enumerated(value = STRING)
    private ExamStatus status;

    private int scoreObtained;
}
