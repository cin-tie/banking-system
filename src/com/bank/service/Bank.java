package com.bank.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.bank.models.BankAccount;
import com.bank.models.Client;
import com.bank.models.Transaction;
import com.bank.service.interfaces.NotificationService;
import com.bank.service.interfaces.TransactionService;

public class Bank {
    private String bankName;
    private String bankCode;
    private LocalDate establishedDate;

    private Map<String, Client> clienst;
    private Map<String, BankAccount> accounts;
    private List<Transaction> transactions;

    private TransactionService transactionService;
    private NotificationService notificationService;

}
