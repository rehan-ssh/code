package com.example.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Expense extends BaseModel{
    private double amount;
    private Date addedAt;
    private String description;
    private String proofUrl;

    @OneToMany(mappedBy = "expense", fetch = FetchType.EAGER)
    private List<ExpenseUser> expenseUsers;
}
