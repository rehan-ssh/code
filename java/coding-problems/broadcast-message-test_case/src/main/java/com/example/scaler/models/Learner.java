package com.example.scaler.models;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Learner extends BaseModel{

    private String name;
    private String email;
    private String phoneNumber;
}
