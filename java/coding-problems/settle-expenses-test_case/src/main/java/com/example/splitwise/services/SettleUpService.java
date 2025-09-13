package com.example.splitwise.services;


import com.example.splitwise.exceptions.InvalidGroupException;
import com.example.splitwise.exceptions.InvalidUserException;
import com.example.splitwise.models.Transaction;

import java.util.List;

public interface SettleUpService {
    public List<Transaction> settleGroup(long groupId) throws InvalidGroupException;

    public List<Transaction> settleUser(long userId) throws InvalidUserException;
}
