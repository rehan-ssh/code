package com.example.splitwise.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ExpenseUser extends BaseModel{

    @ManyToOne(fetch = FetchType.EAGER)
    private Expense expense;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    private double amount;
    @Enumerated(value = EnumType.ORDINAL)
    private ExpenseType expenseType;
}
