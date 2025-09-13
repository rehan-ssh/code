package com.example.splitwise.utils;



import com.example.splitwise.models.Expense;
import com.example.splitwise.models.ExpenseType;
import com.example.splitwise.models.ExpenseUser;
import com.example.splitwise.models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseUtils {

    public static Map<User, Double> aggregateExpenses(List<Expense> expenses){
        Map<User, Double> condensedExpenses = new HashMap<>();
        for (Expense expense : expenses) {
            for (ExpenseUser expenseUser : expense.getExpenseUsers()) {
                User user = expenseUser.getUser();
                double amountForThisExpense = expenseUser.getExpenseType().equals(ExpenseType.PAID) ? 1 * expenseUser.getAmount(): -1 * expenseUser.getAmount();
                condensedExpenses.put(user,  amountForThisExpense + condensedExpenses.getOrDefault(user, 0d));
            }
        }
        return condensedExpenses;
    }
}
