package com.bank.service.interfaces;

import com.bank.models.BankAccount;
import com.bank.models.Transaction;

public interface TransactionService {
    public boolean transfer(BankAccount from, BankAccount to, double amount);
    public void deposit(BankAccount to, double amount);
    public Transaction getTransactionHistory(String accountNumber);
}
