package com.bank.models;

import java.time.LocalDate;

import com.bank.exceptions.AccountNotActiveException;
import com.bank.exceptions.InsufficientFundsException;
import com.bank.exceptions.InvalidAmountException;
import com.bank.models.enums.AccountType;

public class CheckingAccount extends BankAccount{
    private double overdraftLimit;
    
    CheckingAccount(Client owner, double balance, LocalDate openingDate, boolean isActive, double overdraftLimit){
        super(owner, balance, openingDate, isActive);
        this.overdraftLimit = overdraftLimit;
        this.setAccountType(AccountType.CHECKING);
    }
    
    public double getOverdraftLimit() { return overdraftLimit; }
    public void setOverdraftLimit(double overdraftLimit) { this.overdraftLimit = overdraftLimit; }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException, InvalidAmountException, AccountNotActiveException {
        if(!getIsActive())
            throw new AccountNotActiveException(getAccountNumber(), getIsActive());
        
        if(amount <= 0)
            throw new InvalidAmountException(amount);

        double availableBalance = getBalance() + overdraftLimit;
        if(amount > availableBalance)
            throw new InsufficientFundsException(String.format("Overdraft limit exceeded in account %s. Current balance: %.2f, requested: %.2f", getAccountNumber(), availableBalance, amount));

        setBalance(getBalance() - amount);
    }

    @Override
    public boolean canWithdraw(double amount) {
        return amount <= getBalance() + overdraftLimit;
    }
}
