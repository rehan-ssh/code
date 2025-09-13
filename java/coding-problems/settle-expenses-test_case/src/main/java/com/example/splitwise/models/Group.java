package com.example.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "groups")
public class Group extends BaseModel{
    private String name;
    private String description;

    @ManyToMany
    private List<User> users;
    @ManyToMany
    private List<User> admins;
}
