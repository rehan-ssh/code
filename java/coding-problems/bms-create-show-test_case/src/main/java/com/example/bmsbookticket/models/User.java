package com.example.bmsbookticket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Entity(name = "users")
public class User extends BaseModel{

    private String name;
    private String email;
    private String password;
    @Enumerated(value = jakarta.persistence.EnumType.ORDINAL)
    private UserType userType;
}
