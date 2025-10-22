package com.bank.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bank.exceptions.InvalidAmountException;
import com.bank.models.enums.TransactionStatus;
import com.bank.models.enums.TransactionType;

public class Transaction{
    private String id;
    private BankAccount fromAccount;
    private BankAccount toAccount;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private TransactionType type;
    private TransactionStatus status;

    public Transaction(String id, BankAccount fromAccount, BankAccount toAccount, BigDecimal amount, LocalDateTime timestamp, TransactionType type, TransactionStatus status) throws InvalidAmountException{
        if(amount.compareTo(BigDecimal.ZERO) != 1)
            throw new InvalidAmountException(amount);
        
        this.id = id;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.timestamp = timestamp;
        this.type = type;
        this.status = status;
    }

    public String getId(){ return id; }
    public BankAccount getFromAccount(){ return fromAccount; }
    public BankAccount getToAccount(){ return toAccount; }
    public BigDecimal getAmount(){ return amount; }
    public LocalDateTime getTimeStamp(){ return timestamp; }
    public TransactionType getType(){ return type; }
    public TransactionStatus getStatus() { return status; }

    public void setStatus(TransactionStatus status){ this.status = status; }
}
