package com.bank.exceptions;

public class InsufficientFundsException extends Exception{
    public InsufficientFundsException(String message){
        super(message);
    }

    public InsufficientFundsException(String accountNumber, double currentBalance, double requestedAmount){
        super(String.format("Insufficient funds in account %s. Current balance: %.2f, requested: %.2f", accountNumber, currentBalance, requestedAmount));    
    }
}
