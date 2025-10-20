package com.bank.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bank.exceptions.AccountDeactivationException;
import com.bank.exceptions.AccountNotActiveException;
import com.bank.exceptions.InsufficientFundsException;
import com.bank.exceptions.InvalidAmountException;

public class SavingsAccount extends BankAccount{

    private BigDecimal minimumBalance;
    private int monthlyWithdrawalLimit;
    private int withdrawalsThisMonth;

    SavingsAccount(String id, Client owner, BigDecimal balance, LocalDateTime openAt, boolean isActive, BigDecimal interestRate, BigDecimal overdraftLimit, BigDecimal minimumBalance,  int monthlyWithdrawalLimit){
        super(id, owner, BigDecimal.ZERO, openAt, isActive, interestRate, overdraftLimit);
        this.minimumBalance = minimumBalance;
        this.monthlyWithdrawalLimit = monthlyWithdrawalLimit;
        this.withdrawalsThisMonth = 0;
    }

    public BigDecimal getMinimumBalance() { return minimumBalance; }
    public int getMonthlyWithdrawalLimit() { return monthlyWithdrawalLimit; }
    public int getWithdrawalsThisMonth() { return withdrawalsThisMonth; }

    public void setMinimumBalance(BigDecimal minimumBalance){ this.minimumBalance = minimumBalance; }
    public void setMonthlyWithdrawalLimit(int monthlyWithdrawalLimit){ this.monthlyWithdrawalLimit = monthlyWithdrawalLimit; }

    public void applyInterest() throws AccountNotActiveException, InvalidAmountException{
        if(getIsActive() && getBalance().compareTo(BigDecimal.ZERO) > 0){
            BigDecimal interest = getBalance().multiply(getInterestRate().divide(BigDecimal.valueOf(100)));    
            deposit(interest);
        }
    }

    @Override
    public void withdraw(BigDecimal amount) throws InsufficientFundsException, InvalidAmountException, AccountNotActiveException {
        if(withdrawalsThisMonth >= monthlyWithdrawalLimit){
            throw new InsufficientFundsException("Monthly withdrawal limit reached");
        }

        BigDecimal remainingBalance = getBalance().subtract(amount);
        
        if(remainingBalance.compareTo(minimumBalance) < 0){
            throw new InsufficientFundsException(String.format("Cannot go below minimum balance of %s", minimumBalance.toString()));
        }        
        super.withdraw(amount);
        this.withdrawalsThisMonth++;
    }

    public void resetMonthlyDrawals(){
        this.withdrawalsThisMonth = 0;
    }

    @Override
    public void deactivate() throws AccountDeactivationException, InsufficientFundsException, InvalidAmountException, AccountNotActiveException {
        if(getBalance() == null || getBalance().compareTo(BigDecimal.ZERO) == -1)
            throw new AccountDeactivationException(getAccountNumber(), getBalance());
        withdraw(getBalance());
        this.setIsActive(false);
    }
}
