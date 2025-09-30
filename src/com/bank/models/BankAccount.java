package com.bank.models;

import java.time.LocalDate;

public abstract class BankAccount {
    private String accountNumber;
    private Client owner;
    private double balance;
    private LocalDate openingDate;
    private boolean isActive;

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    

    public Client getOwner() { return owner; }
    public void setOwner(Client owner) { this.owner = owner; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public LocalDate getOpeningDate() { return openingDate; }

    public boolean getIsActive() { return isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }

    public void deposit(double amount){
        this.balance += amount;
    }

    public void withdraw(double amount){
        this.balance -= amount;
    }

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
