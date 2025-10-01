package com.bank.service.interfaces;

import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import com.bank.exceptions.InsufficientFundsException;
import com.bank.exceptions.InvalidAmountException;
import com.bank.models.BankAccount;
import com.bank.models.Transaction;

public interface TransactionService {
    public boolean transfer(BankAccount from, BankAccount to, double amount) throws AccountNotFoundException, InsufficientFundsException, InvalidAmountException;
    public void deposit(BankAccount to, double amount) throws AccountNotFoundException, InvalidAmountException;
    public void withdraw(String accountNumber, double amount) throws AccountNotFoundException, InsufficientFundsException, InvalidAmountException;
    public List<Transaction> getTransactionHistory(String accountNumber) throws AccountNotFoundException;
    public Transaction getTransactionById(String transactionId);
}
