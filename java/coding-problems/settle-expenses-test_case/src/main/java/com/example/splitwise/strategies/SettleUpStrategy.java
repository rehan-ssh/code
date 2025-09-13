package com.example.splitwise.strategies;

import com.example.splitwise.models.Transaction;
import com.example.splitwise.models.User;

import java.util.List;
import java.util.Map;

public interface SettleUpStrategy {

    public List<Transaction> settleUp(Map<User, Double> aggregateExpenses);
}
