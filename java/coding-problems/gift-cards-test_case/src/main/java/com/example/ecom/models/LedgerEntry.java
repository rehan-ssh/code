package com.example.ecom.models;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class LedgerEntry extends BaseModel{

    private TransactionType transactionType;
    private double amount;
    private Date createdAt;
}
