package com.bank.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.bank.models.Address;
import com.bank.models.BankAccount;
import com.bank.models.CheckingAccount;
import com.bank.models.Client;
import com.bank.models.SavingsAccount;
import com.bank.models.Transaction;
import com.bank.models.enums.TransactionType;
import com.bank.service.implementations.EmailNotificationService;
import com.bank.service.interfaces.NotificationService;

public class Bank {
    private String name;
    private String code;
    private Map<String, BankAccount> accounts;
    private Map<String, Client> clients;
    private Map<String, Transaction> transactions;
    private NotificationService notificationService;

    public Bank(String name, String code){
        this.name = name;
        this.code = code;
        this.accounts = new HashMap<>();
        this.clients = new HashMap<>();
        this.transactions = new HashMap<>();
        this.notificationService = new EmailNotificationService();
    }

    public String getName() { return name; }
    public String getCode() { return code; }
    public Map<String, BankAccount> getAccounts() { return accounts; }
    public Map<String, Client> getClients() { return clients; }
    public Map<String, Transaction> getTransactions() { return transactions; }
    public NotificationService getNotificationService() { return notificationService; }


    public Client registerClient(String firstName, String lastName, String email, String phoneNumber, Address address){
        String clientId = generateClientId();
        Client client = new Client(clientId, firstName, lastName, email, phoneNumber, address);
        clients.put(clientId, client);
        System.out.println("Client registered: " + firstName + " " + lastName + " (ID: " + clientId + ")");
        return client;
    }

    public Client findClientById(String clientId){
        return clients.get(clientId);
    }

    public List<Client> getAllClients(){
        return new ArrayList<>(clients.values());
    }

    public BankAccount createCheckingAccount(Client client, BigDecimal initialDeposit, BigDecimal interestRate, BigDecimal overdraftLimit){
        String accountId = generateAccountId("CHK");
        CheckingAccount account = new CheckingAccount(accountId, client, initialDeposit, LocalDateTime.now(), true, interestRate, overdraftLimit);
        accounts.put(accountId, account);
        System.out.println("Checking account created: " + accountId + " for client: " + client.getFirstName() + " " + client.getLastName());
        return account;
    }

    public BankAccount createSavingsAccount(Client client, BigDecimal initialDeposit, BigDecimal interestRate, BigDecimal overdraftLimit, BigDecimal minimumBalance,  int monthlyWithdrawalLimit){
        String accountId = generateAccountId("SAV");
        SavingsAccount account = new SavingsAccount(accountId, client, initialDeposit, LocalDateTime.now(), true, interestRate, overdraftLimit, minimumBalance, monthlyWithdrawalLimit);
        accounts.put(accountId, account);
        System.out.println("Savings account created: " + accountId + " for client: " + client.getFirstName() + " " + client.getLastName());
        return account;
    }

    public BankAccount getAccount(String accountNumber){
        return accounts.get(accountNumber);
    }

    public List<BankAccount> getClientsAccount(String clientId){
        return accounts.values().stream().filter(account -> account.getOwner().getId().equals(clientId)).collect(Collectors.toList());
    }

    public List<BankAccount> getAllAccounts(){
        return new ArrayList<>(accounts.values());
    }

    public void applyInterestToAllAccounts(){
        System.out.println("Applying interest to all accounts...");
        int successCount = 0;
        int failCount = 0;
        
        for (BankAccount account : accounts.values()) {
            try {
                account.applyInterest();
                successCount++;
            } catch (Exception e) {
                System.out.println("Failed to apply interest to account " + 
                    account.getAccountNumber() + ": " + e.getMessage());
                failCount++;
            }
        }
        
        System.out.println("Interest applied successfully to " + successCount + " accounts");
        if (failCount > 0) {
            System.out.println("Failed to apply interest to " + failCount + " accounts");
        }
    }

    public void generateBankReport() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("BANK REPORT: " + name + " (" + code + ")");
        System.out.println("=".repeat(60));
        
        System.out.println("Total Clients: " + clients.size());
        System.out.println("Total Accounts: " + accounts.size());
        
        long activeAccounts = accounts.values().stream()
            .filter(BankAccount::getIsActive)
            .count();
        System.out.println("Active Accounts: " + activeAccounts);
        
        BigDecimal totalBalance = accounts.values().stream()
            .map(BankAccount::getBalance)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total Balance: $" + totalBalance);
        
        long checkingAccounts = accounts.values().stream()
            .filter(account -> account instanceof CheckingAccount)
            .count();
        long savingsAccounts = accounts.values().stream()
            .filter(account -> account instanceof SavingsAccount)
            .count();
            
        System.out.println("Checking Accounts: " + checkingAccounts);
        System.out.println("Savings Accounts: " + savingsAccounts);
        
        Map<TransactionType, Long> transactionStats = transactions.values().stream()
            .collect(Collectors.groupingBy(Transaction::getType, Collectors.counting()));
        
        System.out.println("Transaction Statistics:");
        transactionStats.forEach((type, count) -> 
            System.out.println("   " + type + ": " + count));
            
        System.out.println("=".repeat(60));
    }

    private String generateClientId() {
        return "CLI" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    private String generateAccountId(String prefix) {
        return prefix + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    private String generateTransactionId() {
        return "TXN" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}