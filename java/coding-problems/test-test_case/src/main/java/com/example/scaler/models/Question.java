package com.example.scaler.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Question extends BaseModel{
    private String name;
    private String description;
    @ManyToOne
    private Exam exam;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Option> options;
    @OneToOne(fetch = FetchType.EAGER)
    private Option correctOption;
    private int score;
}
