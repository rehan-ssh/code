package com.example.ecom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Advertisement extends BaseModel{
    private String name;
    private String description;
    @ManyToOne
    private Preference preference;
}
