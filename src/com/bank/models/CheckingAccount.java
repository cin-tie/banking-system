package com.bank.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bank.exceptions.AccountNotActiveException;
import com.bank.exceptions.InsufficientFundsException;
import com.bank.exceptions.InvalidAmountException;


public class CheckingAccount extends BankAccount{

    public CheckingAccount(String id, Client owner, BigDecimal balance, LocalDateTime openAt, boolean isActive, BigDecimal interestRate, BigDecimal overdraftLimit){
        super(id, owner, balance, openAt, isActive, interestRate, overdraftLimit);
    }

    @Override
    public void withdraw(BigDecimal amount) throws InsufficientFundsException, InvalidAmountException, AccountNotActiveException{
        BigDecimal resultBalance = getBalance().subtract(amount);
        boolean isActive = resultBalance.compareTo(BigDecimal.ZERO) != -1;
        super.withdraw(amount);
        setIsActive(isActive);
    }    
}
