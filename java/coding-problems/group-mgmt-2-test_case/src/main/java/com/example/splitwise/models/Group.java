package com.example.splitwise.models;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.Date;

@Data
@Entity(name = "groups")
public class Group extends BaseModel{
    private String name;
    private String description;
    private Date createdAt;
}
