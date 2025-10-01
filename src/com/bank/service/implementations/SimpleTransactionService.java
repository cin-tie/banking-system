package com.bank.service.implementations;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.security.auth.login.AccountNotFoundException;

import com.bank.exceptions.InvalidAmountException;
import com.bank.models.BankAccount;
import com.bank.models.Transaction;
import com.bank.service.interfaces.TransactionService;

public class SimpleTransactionService implements TransactionService{
    private Map<String, BankAccount> accounts;
    private Map<String, List<Transaction>> transactionHistory;
    private Map<String, Transaction> transactions;

    public SimpleTransactionService(){
        this.accounts = new ConcurrentHashMap<>();
        this.transactionHistory = new ConcurrentHashMap<>();
        this.transactions = new ConcurrentHashMap<>();
    }

    public SimpleTransactionService(Map<String, BankAccount> existingAccounts){
        this.accounts = existingAccounts;
        this.transactionHistory = new ConcurrentHashMap<>();
        this.transactions = new ConcurrentHashMap<>();
    }

    @Override
    public void deposit(String accountNumber, double amount) throws AccountNotFoundException, InvalidAmountException{
        valid
    }
}
