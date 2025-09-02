package com.example.ecom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "users")
public class User extends BaseModel{
    private String name;
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Preference> preferences;
}
