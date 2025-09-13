package com.example.splitwise.dtos;

import com.example.splitwise.models.Transaction;
import lombok.Data;

import java.util.List;

@Data
public class SettleUserResponseDto {
    private ResponseStatus responseStatus;
    private List<Transaction> transactions;
}
