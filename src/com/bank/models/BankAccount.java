package com.bank.models;

import java.time.LocalDate;

import com.bank.exceptions.AccountNotActiveException;
import com.bank.exceptions.InsufficientFundsException;
import com.bank.exceptions.InvalidAmountException;
import com.bank.models.enums.AccountType;

public abstract class BankAccount {
    private String accountNumber;
    private Client owner;
    private double balance;
    private LocalDate openingDate;
    private boolean isActive;
    private AccountType accountType;

    BankAccount(String accountNumber, Client owner, double balance, LocalDate openingDate, boolean isActive){
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = balance;
        this.openingDate = openingDate;
        this.isActive = isActive;
    }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    

    public Client getOwner() { return owner; }
    public void setOwner(Client owner) { this.owner = owner; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public LocalDate getOpeningDate() { return openingDate; }

    public boolean getIsActive() { return isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }

    public AccountType getAccountType() { return accountType; }
    protected void setAccountType(AccountType accountType) { this.accountType = accountType; }

    public void deposit(double amount) throws InvalidAmountException, AccountNotActiveException{
        if(!isActive)
            throw new AccountNotActiveException(accountNumber, isActive);
        
        if(amount <= 0)
            throw new InvalidAmountException(amount);

        balance += amount;

    }

    public abstract void withdraw(double amount) throws InsufficientFundsException, InvalidAmountException, AccountNotActiveException;

    public String getAccountInfo(){
        return String.format(
            "Account number: %s | Owner: %s %s | Balance: %.2f | Type: %s | Opened: %s | Status: %s",
            accountNumber,
            owner.getFirstName(),
            owner.getLastName(),
            balance,
            this.getClass().getSimpleName(),
            openingDate,
            isActive ? "Active" : "Non-active"
        );
    }
    
    @Override 
    public String toString(){
        return String.format("%s, %s, %.2f, %s. %s", accountNumber, owner.toString(), balance, openingDate.toString(), String.valueOf(isActive));
    }
}
