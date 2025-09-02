package com.example.scaler.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Entity(name="users")
public class User extends BaseModel{

    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private UserType userType;
}
