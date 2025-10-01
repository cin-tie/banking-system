package com.bank.service.implementations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.security.auth.login.AccountNotFoundException;

import com.bank.exceptions.AccountNotActiveException;
import com.bank.exceptions.InsufficientFundsException;
import com.bank.exceptions.InvalidAmountException;
import com.bank.models.BankAccount;
import com.bank.models.Transaction;
import com.bank.models.enums.TransactionType;
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
    public void deposit(String accountNumber, double amount) throws AccountNotFoundException, InvalidAmountException, AccountNotActiveException{
        validateAmount(amount);
        BankAccount account = getAccount(accountNumber);

        account.deposit(amount);

        Transaction transaction = createTransaction(null, accountNumber, amount, TransactionType.DEPOSIT);
        saveTransaction(transaction, accountNumber);
    }

    @Override
    public void withdraw(String accountNumber, double amount) throws AccountNotFoundException, InsufficientFundsException, InvalidAmountException, AccountNotActiveException {
        validateAmount(amount);
        BankAccount account = getAccount(accountNumber);

        if(!account.canWithdraw(amount))
            throw new InsufficientFundsException(accountNumber, account.getBalance(), amount);

        account.withdraw(amount);

        Transaction transaction = createTransaction(accountNumber, null, amount, TransactionType.WITHDRAWAL);
        saveTransaction(transaction, accountNumber);
    }

    @Override
    public void transfer(String fromAccountNumber, String toAccountNumber, double amount) throws AccountNotFoundException, InsufficientFundsException, InvalidAmountException, AccountNotActiveException {
        validateAmount(amount);

        BankAccount fromAccount = getAccount(fromAccountNumber);
        BankAccount toAccount = getAccount(toAccountNumber);

        if(!fromAccount.canWithdraw(amount))
            throw new InsufficientFundsException(fromAccountNumber, fromAccount.getBalance(), amount);
        
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);

        Transaction outgoingTransaction = createTransaction(fromAccountNumber, toAccountNumber, amount, TransactionType.TRANSFER_OUT);
        Transaction incomingTransaction = createTransaction(fromAccountNumber, toAccountNumber, amount, TransactionType.TRANSFER_IN);
    
        saveTransaction(outgoingTransaction, fromAccountNumber);
        saveTransaction(incomingTransaction, toAccountNumber);
    }

    @Override
    public List<Transaction> getTransactionHistory(String accountNumber) throws AccountNotFoundException {
        BankAccount account = accounts.get(accountNumber);
        if(account == null)
            throw new AccountNotFoundException(String.format("Account not found: %s", accountNumber));

        return transactionHistory.getOrDefault(accountNumber, new ArrayList<>());
    }

    @Override
    public Transaction getTransactionById(String transactionId) {
        return transactions.get(transactionId);
    }

    private void validateAmount(double amount) throws InvalidAmountException{
        if(amount <= 0)
            throw new InvalidAmountException(String.format("Amount must be positive and non-null: %2f", amount));
    }

    private void saveTransaction(Transaction transaction, String accountNumber){
        transactions.put(transaction.id(), transaction);
        transactionHistory.computeIfAbsent(accountNumber, k -> new ArrayList<>()).add(transaction);
    }

    private BankAccount getAccount(String accountNumber) throws AccountNotFoundException, AccountNotActiveException{
        BankAccount account = accounts.get(accountNumber);
        if(account == null)
            throw new AccountNotFoundException(String.format("Account not found: %s", accountNumber));
        if(!account.getIsActive())
            throw new AccountNotActiveException(accountNumber, account.getIsActive());
        return account;
    }

    private Transaction createTransaction(String fromAccountNumber, String toAccountNumber, double amount, TransactionType type){
        String transactionId = "TXN" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8);
        return new Transaction(transactionId, fromAccountNumber, toAccountNumber, amount, LocalDateTime.now(), type);
    }
}
