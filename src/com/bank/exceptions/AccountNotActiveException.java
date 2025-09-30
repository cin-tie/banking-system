package com.bank.exceptions;

public class AccountNotActiveException extends Exception {
    public AccountNotActiveException(String message) {
        super(message);
    }
    
    public AccountNotActiveException(String accountNumber, boolean isActive) {
        super(String.format("Account %s is not active", accountNumber));
    }
}