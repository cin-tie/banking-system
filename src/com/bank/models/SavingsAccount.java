package com.bank.models;

import java.time.LocalDate;

import com.bank.exceptions.AccountNotActiveException;
import com.bank.exceptions.InsufficientFundsException;
import com.bank.exceptions.InvalidAmountException;

public class SavingsAccount extends BankAccount{
    private double interestRate;

    SavingsAccount(String accountNumber, Client owner, double balance, LocalDate openingDate, boolean isActive, double interestRate){
        super(accountNumber, owner, balance, openingDate, isActive);
        this.interestRate = interestRate;
    }

    public double getInterestRate() { return interestRate; }
    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException, InvalidAmountException, AccountNotActiveException {
        if(!getIsActive())
            throw new AccountNotActiveException(getAccountNumber(), getIsActive());
        
        if(amount <= 0)
            throw new InvalidAmountException(amount);

        if(amount > getBalance())
            throw new InsufficientFundsException(String.format("Overdraft limit exceeded in account %s. Current balance: %.2f, requested: %.2f", getAccountNumber(), getBalance(), amount));
    
        setBalance(getBalance() - amount);
    }

    public void applyInterest() throws AccountNotActiveException{
        if(!getIsActive())
            throw new AccountNotActiveException(getAccountNumber(), getIsActive());
        
        double interest = getBalance() * interestRate / 100;
        setBalance(getBalance() + interest);
    }
}
