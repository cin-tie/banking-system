package com.bank.models;

import java.time.LocalDateTime;

import com.bank.models.enums.TransactionType;

public record Transaction(String id, String fromAccount, String toAccount, double amount, LocalDateTime timestamp, TransactionType type) {}
