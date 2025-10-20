package com.bank.exceptions;

import java.math.BigDecimal;

public class InvalidAmountException extends Exception{
    public InvalidAmountException(String message){
        super(message);
    }
    
    public InvalidAmountException(BigDecimal amount){
        super(String.format("Incorrect amount: %s. Amount must be positive.", amount.toString()));
    }
}
