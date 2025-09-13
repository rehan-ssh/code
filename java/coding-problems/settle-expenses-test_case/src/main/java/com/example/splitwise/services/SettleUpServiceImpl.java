package com.example.splitwise.services;

import com.example.splitwise.exceptions.InvalidGroupException;
import com.example.splitwise.exceptions.InvalidUserException;
import com.example.splitwise.models.Expense;
import com.example.splitwise.models.GroupExpense;
import com.example.splitwise.models.Transaction;
import com.example.splitwise.models.User;
import com.example.splitwise.repositories.ExpenseRepository;
import com.example.splitwise.repositories.GroupExpenseRepository;
import com.example.splitwise.repositories.GroupRepository;
import com.example.splitwise.repositories.UserRepository;
import com.example.splitwise.strategies.SettleUpStrategy;
import com.example.splitwise.utils.ExpenseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SettleUpServiceImpl implements SettleUpService{

    private GroupExpenseRepository groupExpenseRepository;
    private ExpenseRepository expenseRepository;
    private GroupRepository groupRepository;
    private UserRepository userRepository;
    private SettleUpStrategy settleUpStrategy;

    @Autowired
    public SettleUpServiceImpl(GroupExpenseRepository groupExpenseRepository, ExpenseRepository expenseRepository, GroupRepository groupRepository, UserRepository userRepository, SettleUpStrategy settleUpStrategy) {
        this.groupExpenseRepository = groupExpenseRepository;
        this.expenseRepository = expenseRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.settleUpStrategy = settleUpStrategy;
    }

    @Override
    public List<Transaction> settleGroup(long groupId) throws InvalidGroupException {

        this.groupRepository.findById(groupId).orElseThrow(() -> new InvalidGroupException("Group not found"));

        List<GroupExpense> groupExpenses = groupExpenseRepository.findByGroupId(groupId);
        List<Expense> expenses = groupExpenses.stream().map(GroupExpense::getExpense).collect(Collectors.toList());
        Map<User, Double> aggregateExpenses = ExpenseUtils.aggregateExpenses(expenses);
        return settleUpStrategy.settleUp(aggregateExpenses);
    }

    @Override
    public List<Transaction> settleUser(long userId) throws InvalidUserException {
        this.userRepository.findById(userId).orElseThrow(() -> new InvalidUserException("User not found"));
        List<Expense> expenses = this.expenseRepository.findNonGroupExpensesForUser(userId);
        Map<User, Double> aggregateExpenses = ExpenseUtils.aggregateExpenses(expenses);
        return settleUpStrategy.settleUp(aggregateExpenses);
    }
}
