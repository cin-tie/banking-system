package com.bank.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bank.models.Address;
import com.bank.models.BankAccount;
import com.bank.models.Client;
import com.bank.models.Transaction;
import com.bank.service.interfaces.NotificationService;
import com.bank.service.interfaces.TransactionService;
import com.bank.util.IdGenerator;

public class Bank {
    private String bankName;
    private String bankCode;
    private LocalDate establishedDate;

    private TransactionService transactionService;
    private NotificationService notificationService;
    private IdGenerator idGenerator;

    private Map<String, Client> clients;
    private Map<String, BankAccount> accounts;
    private Map<String, List<Transaction>> transactions;

    public Bank(String bankName, String bankCode, TransactionService transactionService, NotificationService notificationService){
        this.bankName = bankName;
        this.bankCode = bankCode;
        this.transactionService = transactionService;
        this.notificationService = notificationService;

        this.clients = new HashMap<>();
        this.accounts = new HashMap<>();
        this.transactions = new HashMap<>();
        this.idGenerator = new IdGenerator();
    }

    public String getBankCode() { return bankCode; }
    public String getBankName() { return bankName; }
    public Map<String, Client> getClients() { return new HashMap<>(clients); }
    public Map<String, BankAccount> getAccounts() { return new HashMap<>(accounts); }

    public Client registerClient(String firstName, String lastName, String email, String phoneNumber, Address address){

        String clientId = idGenerator.generateClientId();
        Client client = new Client(firstName, lastName, email, phoneNumber, address);
        clients.put(clientId, client);

        return client;
    }

    public Client findClientById(String clientId){
        return clients.get(clientId);
    }

}
