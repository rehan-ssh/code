package com.example.scaler.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class LearnerQuestionResponse extends BaseModel{

    @ManyToOne
    private Learner learner;
    @ManyToOne
    private Question question;
    @ManyToOne
    private Option option;
}
