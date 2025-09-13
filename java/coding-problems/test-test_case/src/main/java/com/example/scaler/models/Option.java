package com.example.scaler.models;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Option extends BaseModel{
    private String text;
}
