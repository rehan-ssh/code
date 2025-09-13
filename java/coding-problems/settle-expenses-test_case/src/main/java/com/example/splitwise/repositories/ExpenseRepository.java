package com.example.splitwise.repositories;

import com.example.splitwise.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    @Query("Select e from Expense e inner join ExpenseUser eu on e.id = eu.expense.id and eu.user.id = :userId left join group_expenses ge on e.id = ge.expense.id where ge.id is null")
    List<Expense> findNonGroupExpensesForUser(@Param("userId") Long userId);
}
