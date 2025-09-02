package com.example.scaler.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Entity(name="batches")
public class Batch extends BaseModel{

    private String name;
    @Enumerated
    private Schedule schedule;

}
