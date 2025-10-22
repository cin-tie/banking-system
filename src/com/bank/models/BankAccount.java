package com.bank.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.bank.exceptions.AccountDeactivationException;
import com.bank.exceptions.AccountNotActiveException;
import com.bank.exceptions.InsufficientFundsException;
import com.bank.exceptions.InvalidAmountException;
import com.bank.models.enums.TransactionStatus;
import com.bank.models.enums.TransactionType;

public abstract class BankAccount {
    private final String id;
    private Client owner;
    private BigDecimal balance;
    private LocalDateTime openAt;
    private boolean isActive;
    private BigDecimal interestRate;
    private BigDecimal overdraftLimit;
    private List<Transaction> transactions;


    BankAccount(String id, Client owner, BigDecimal balance, LocalDateTime openAt, boolean isActive, BigDecimal interestRate, BigDecimal overdraftLimit) {
        this.id = id;
        this.owner = owner;
        this.balance = balance;
        this.openAt = openAt;
        this.isActive = isActive;
        this.interestRate = interestRate;
        this.overdraftLimit = overdraftLimit;
        this.transactions = new ArrayList<>();
    }

    public String getAccountNumber() { return id; }
    public Client getOwner() { return owner; }
    public BigDecimal getBalance() { return balance; }
    public LocalDateTime getOpenAt() { return openAt; }
    public boolean getIsActive() { return isActive; }
    public BigDecimal getOverdraftLimit() { return overdraftLimit; }
    public BigDecimal getInterestRate() { return interestRate; }
    public List<Transaction> getTransactions() { return transactions; }

    public void setInterestRate(BigDecimal interestRate) { this.interestRate = interestRate; }
    public void setOverdraftLimit(BigDecimal overdraftLimit) { this.overdraftLimit = overdraftLimit; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }

    public Transaction deposit(BigDecimal amout) throws InvalidAmountException, AccountNotActiveException{
        
        validateAccountActive();
        validatePositiveAmount(amout);
        
        this.balance = this.balance.add(amout);
        
        Transaction depositTransaction = new Transaction(UUID.randomUUID().toString(), null, this, amout, LocalDateTime.now(), TransactionType.DEPOSIT, TransactionStatus.SUCCESS);
        transactions.add(depositTransaction);
        return depositTransaction;
    }

    public Transaction withdraw(BigDecimal amount) throws InsufficientFundsException, InvalidAmountException, AccountNotActiveException{
        validateAccountActive();
        validatePositiveAmount(amount);
        validateSufficientFunds(amount);

        this.balance = this.balance.subtract(amount);

        Transaction withdrawalTransaction = new Transaction(id, this, null, amount, LocalDateTime.now(), TransactionType.WITHDRAWAL, TransactionStatus.SUCCESS);
        transactions.add(withdrawalTransaction);
        return withdrawalTransaction;
    }

    public Transaction transfer(BigDecimal amount, BankAccount recipient) throws InsufficientFundsException, InvalidAmountException, AccountNotActiveException{
        validateAccountActive();
        
        if(recipient == null || !recipient.getIsActive()){
            throw new AccountNotActiveException("Recipient account is not active");
        }

        validatePositiveAmount(amount);
        validateSufficientFunds(amount);

        Transaction transferTransaction = new Transaction(UUID.randomUUID().toString(), this, recipient, amount, LocalDateTime.now(), TransactionType.TRANSFER, TransactionStatus.IN_PROCESS);

        BigDecimal[] backupBalance = {this.balance, recipient.balance};

        if(approveTransaction(transferTransaction) && recipient.approveTransaction(transferTransaction)){
            try{
                processTransaction(transferTransaction);
                recipient.processTransaction(transferTransaction);
                transferTransaction.setStatus(TransactionStatus.SUCCESS);
            }
            catch (Exception e){
                rollbackTransaction(transferTransaction, backupBalance);
                transferTransaction.setStatus(TransactionStatus.FAILED);
                throw new RuntimeException("Transfer failed: " + e.getMessage(), e);
            }
        } 
        else{
            transferTransaction.setStatus(TransactionStatus.CANCELED);
            throw new RuntimeException("Transaction canceled");
        }

        transactions.add(transferTransaction);
        recipient.getTransactions().add(transferTransaction);
        return transferTransaction;
    }

    public boolean approveTransaction(Transaction transaction){
        if(!isActive)
            return false;
        
        if(transaction.getFromAccount().equals(this)){
            BigDecimal availableBalance = balance.add(overdraftLimit);
            return transaction.getAmount().compareTo(availableBalance) <= 0;
        }

        return true;
    }

    protected void processTransaction(Transaction transaction){
        switch (transaction.getType()) {
            case TRANSFER:
                if(transaction.getFromAccount().equals(this)){
                    this.balance = this.balance.subtract(transaction.getAmount());
                } else if(transaction.getToAccount().equals(this)){
                    this.balance = this.balance.add(transaction.getAmount());
                }
                break;
        
            default:
                break;
        }
    }

    protected void rollbackTransaction(Transaction transaction, BigDecimal[] backupBalance){
        switch (transaction.getType()) {
            case TRANSFER:
                if (transaction.getFromAccount().equals(this)) {
                    this.balance = backupBalance[0];
                } else if (transaction.getToAccount().equals(this)) {
                    this.balance = backupBalance[1];
                }
                break;

            default:
                break;
        }
    }

    public void applyInterest() throws AccountNotActiveException, InvalidAmountException{
        validateAccountActive();
        validatePositiveAmount(balance);

        BigDecimal interest = getBalance().multiply(getInterestRate().divide(BigDecimal.valueOf(100)));    
        deposit(interest);
    }

    public void deactivate() throws AccountDeactivationException, InsufficientFundsException, InvalidAmountException, AccountNotActiveException {
        if(balance == null || balance.compareTo(BigDecimal.ZERO) == -1)
            throw new AccountDeactivationException(id, balance);
        isActive = false;
    }

    public void activate(){
        isActive = true;
    }

    protected void validateAccountActive() throws AccountNotActiveException{
        if(!isActive)
            throw new AccountNotActiveException(id, isActive);
    }

    protected void validatePositiveAmount(BigDecimal amount) throws InvalidAmountException{
        if(amount == null || amount.compareTo(BigDecimal.ZERO) != 1)
            throw new InvalidAmountException(amount);
    }

    protected void validateSufficientFunds(BigDecimal amount) throws InsufficientFundsException{
        BigDecimal availableBalance = balance.add(overdraftLimit);
        if(amount.compareTo(availableBalance) == 1)
            throw new InsufficientFundsException(id, availableBalance, amount);
    }
    
    @Override 
    public String toString(){
        return String.format("%s, %s, %.2f, %s. %s", id, owner.toString(), balance, openAt.toString(), String.valueOf(isActive));
    }
    
}
