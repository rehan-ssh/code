package com.example.splitwise.models;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class GroupMember extends BaseModel{
    @ManyToOne
    private Group group;
    @ManyToOne
    private User user;
    @ManyToOne
    private User addedBy;
    private Date addedAt;
}
