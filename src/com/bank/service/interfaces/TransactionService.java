package com.bank.service.interfaces;

import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import com.bank.exceptions.AccountNotActiveException;
import com.bank.exceptions.InsufficientFundsException;
import com.bank.exceptions.InvalidAmountException;
import com.bank.models.Transaction;

public interface TransactionService {
    public void transfer(String fromAccountNumber, String toAccountNumber, double amount) throws AccountNotFoundException, InsufficientFundsException, InvalidAmountException, AccountNotActiveException;
    public void deposit(String accountNumber, double amount) throws AccountNotFoundException, InvalidAmountException, AccountNotActiveException;
    public void withdraw(String accountNumber, double amount) throws AccountNotFoundException, InsufficientFundsException, InvalidAmountException, AccountNotActiveException;
    public List<Transaction> getTransactionHistory(String accountNumber) throws AccountNotFoundException;
    public Transaction getTransactionById(String transactionId);
}
