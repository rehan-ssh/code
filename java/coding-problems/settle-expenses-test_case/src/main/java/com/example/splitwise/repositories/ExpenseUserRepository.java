package com.example.splitwise.repositories;

import com.example.splitwise.models.ExpenseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseUserRepository extends JpaRepository<ExpenseUser, Long> {
}
