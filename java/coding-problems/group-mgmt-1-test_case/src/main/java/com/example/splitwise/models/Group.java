package com.example.splitwise.models;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity(name = "groups")
public class Group extends BaseModel{
    private String name;
    private String description;
}
