package com.bank.exceptions;

import java.math.BigDecimal;

public class InsufficientFundsException extends Exception{
    public InsufficientFundsException(String message){
        super(message);
    }

    public InsufficientFundsException(String accountNumber, BigDecimal currentBalance, BigDecimal requestedAmount){
        super(String.format("Insufficient funds in account %s. Current balance: %s, requested: %s", accountNumber, currentBalance.toString(), requestedAmount.toString()));    
    }
}
