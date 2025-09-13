package com.example.scaler.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Communication extends BaseModel{
    @ManyToOne
    private Batch batch;
    private String message;
    @ManyToOne
    private User sentBy;
}
