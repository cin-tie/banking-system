package com.bank.exceptions;

public class InvalidAmountException extends Exception{
    public InvalidAmountException(String message){
        super(message);
    }
    public InvalidAmountException(double amount){
        super(String.format("Incorrect amount: %.2f. Amount must be positive.", amount));
    }
}
