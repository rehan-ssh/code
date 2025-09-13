package com.example.splitwise.models;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity(name = "users")
public class User extends BaseModel{
    private String name;
    private String phoneNumber;
    private String password;
}
