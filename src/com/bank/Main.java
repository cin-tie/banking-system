package com.bank;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import com.bank.models.Address;
import com.bank.models.BankAccount;
import com.bank.models.Client;
import com.bank.service.Bank;

public class Main {
    private static Bank bank;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        initializeBank();
        displayWelcomeMessage();
        
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1 -> manageClients();
                case 2 -> manageAccounts();
                case 3 -> performTransactions();
                case 4 -> viewReports();
                case 5 -> bank.applyInterestToAllAccounts();
                case 6 -> bank.generateBankReport();
                case 0 -> {
                    running = false;
                    System.out.println("Thank you for using " + bank.getName() + " Banking System!");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
    
    private static void initializeBank() {
        System.out.println("Initializing Banking System...");
        bank = new Bank("Global Trust Bank", "GTB001");
        createSampleData();
        System.out.println("Banking System initialized successfully!\n");
    }
    
    private static void createSampleData() {
        Address address1 = new Address("USA", "New York", "123 Main St", "10001");
        Address address2 = new Address("USA", "Los Angeles", "456 Oak Ave", "90001");
        Address address3 = new Address("USA", "Chicago", "789 Pine Rd", "60001");
        
        Client client1 = bank.registerClient("John", "Smith", "john.smith@email.com", "+1-555-0101", address1);
        Client client2 = bank.registerClient("Sarah", "Johnson", "sarah.j@email.com", "+1-555-0102", address2);
        Client client3 = bank.registerClient("Michael", "Brown", "m.brown@email.com", "+1-555-0103", address3);
        
        bank.createCheckingAccount(client1, BigDecimal.valueOf(2500.00), BigDecimal.valueOf(0.5), BigDecimal.valueOf(1000.00));
        bank.createSavingsAccount(client1, BigDecimal.valueOf(10000.00), BigDecimal.valueOf(2.5), BigDecimal.ZERO, BigDecimal.valueOf(1000.00), 5);
        
        bank.createCheckingAccount(client2, BigDecimal.valueOf(1500.00), BigDecimal.valueOf(0.5), BigDecimal.valueOf(500.00));
        bank.createSavingsAccount(client2, BigDecimal.valueOf(7500.00), BigDecimal.valueOf(2.5), BigDecimal.ZERO, BigDecimal.valueOf(500.00), 5);
        
        bank.createCheckingAccount(client3, BigDecimal.valueOf(3500.00), BigDecimal.valueOf(0.5), BigDecimal.valueOf(1500.00));
        
        System.out.println("Sample data created with 3 clients and 5 accounts.\n");
    }
    
    private static void displayWelcomeMessage() {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 WELCOME TO " + bank.getName() + "                 ║");
        System.out.println("║                   BANKING MANAGEMENT SYSTEM                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");
    }
    
    private static void displayMainMenu() {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("MAIN MENU");
        System.out.println("═".repeat(60));
        System.out.println("1. Client Management");
        System.out.println("2. Account Management");
        System.out.println("3. Transactions");
        System.out.println("4. View Reports");
        System.out.println("5. Apply Interest to All Accounts");
        System.out.println("6. Generate Bank Report");
        System.out.println("0. Exit");
        System.out.println("═".repeat(60));
    }
    
    private static void manageClients() {
        boolean inClientMenu = true;
        while (inClientMenu) {
            System.out.println("\n" + "─".repeat(40));
            System.out.println("CLIENT MANAGEMENT");
            System.out.println("─".repeat(40));
            System.out.println("1. Register New Client");
            System.out.println("2. View All Clients");
            System.out.println("3. Find Client by ID");
            System.out.println("4. View Client Accounts");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1 -> registerNewClient();
                case 2 -> viewAllClients();
                case 3 -> findClientById();
                case 4 -> viewClientAccounts();
                case 0 -> inClientMenu = false;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void registerNewClient() {
        System.out.println("\n" + "─".repeat(40));
        System.out.println("REGISTER NEW CLIENT");
        System.out.println("─".repeat(40));
        
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        
        System.out.println("\nAddress Information:");
        System.out.print("Country: ");
        String country = scanner.nextLine();
        
        System.out.print("City: ");
        String city = scanner.nextLine();
        
        System.out.print("Street: ");
        String street = scanner.nextLine();
        
        System.out.print("Postal Code: ");
        String postalCode = scanner.nextLine();
        
        Address address = new Address(country, city, street, postalCode);
        Client client = bank.registerClient(firstName, lastName, email, phoneNumber, address);
        
        System.out.println("✓ Client registered successfully!");
        System.out.println("Client ID: " + client.getId());
    }
    
    private static void viewAllClients() {
        List<Client> clients = bank.getAllClients();
        System.out.println("\n" + "─".repeat(80));
        System.out.println("ALL REGISTERED CLIENTS");
        System.out.println("─".repeat(80));
        
        if (clients.isEmpty()) {
            System.out.println("No clients registered.");
        } else {
            System.out.printf("%-12s %-15s %-15s %-25s %-15s%n", 
                "Client ID", "First Name", "Last Name", "Email", "Phone");
            System.out.println("─".repeat(80));
            
            for (Client client : clients) {
                System.out.printf("%-12s %-15s %-15s %-25s %-15s%n",
                    client.getId(), client.getFirstName(), client.getLastName(),
                    client.getEmail(), client.getPhoneNumber());
            }
        }
    }
    
    private static void findClientById() {
        System.out.print("Enter Client ID: ");
        String clientId = scanner.nextLine();
        
        Client client = bank.findClientById(clientId);
        if (client != null) {
            System.out.println("\n" + "─".repeat(60));
            System.out.println("CLIENT DETAILS");
            System.out.println("─".repeat(60));
            System.out.println("ID: " + client.getId());
            System.out.println("Name: " + client.getFirstName() + " " + client.getLastName());
            System.out.println("Email: " + client.getEmail());
            System.out.println("Phone: " + client.getPhoneNumber());
            System.out.println("Address: " + client.getAddress());
            System.out.println("Registration Date: " + client.getRegistrationDate());
        } else {
            System.out.println("✗ Client not found with ID: " + clientId);
        }
    }
    
    private static void viewClientAccounts() {
        System.out.print("Enter Client ID: ");
        String clientId = scanner.nextLine();
        
        List<BankAccount> accounts = bank.getClientsAccount(clientId);
        System.out.println("\n" + "─".repeat(100));
        System.out.println("ACCOUNTS FOR CLIENT: " + clientId);
        System.out.println("─".repeat(100));
        
        if (accounts.isEmpty()) {
            System.out.println("No accounts found for this client.");
        } else {
            System.out.printf("%-15s %-12s %-10s %-12s %-10s %-15s%n",
                "Account Number", "Type", "Balance", "Interest", "Overdraft", "Status");
            System.out.println("─".repeat(100));
            
            for (BankAccount account : accounts) {
                String type = account instanceof com.bank.models.SavingsAccount ? "Savings" : "Checking";
                String status = account.getIsActive() ? "Active" : "Inactive";
                
                System.out.printf("%-15s %-12s $%-9.2f %-11.1f%% $%-9.2f %-15s%n",
                    account.getAccountNumber(), type, account.getBalance(),
                    account.getInterestRate(), account.getOverdraftLimit(), status);
            }
        }
    }
    
    private static void manageAccounts() {
        boolean inAccountMenu = true;
        while (inAccountMenu) {
            System.out.println("\n" + "─".repeat(40));
            System.out.println("ACCOUNT MANAGEMENT");
            System.out.println("─".repeat(40));
            System.out.println("1. Create Checking Account");
            System.out.println("2. Create Savings Account");
            System.out.println("3. View All Accounts");
            System.out.println("4. Find Account by Number");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1 -> createCheckingAccount();
                case 2 -> createSavingsAccount();
                case 3 -> viewAllAccounts();
                case 4 -> findAccountByNumber();
                case 0 -> inAccountMenu = false;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void createCheckingAccount() {
        System.out.print("Enter Client ID: ");
        String clientId = scanner.nextLine();
        
        Client client = bank.findClientById(clientId);
        if (client == null) {
            System.out.println("✗ Client not found with ID: " + clientId);
            return;
        }
        
        double initialDeposit = getDoubleInput("Enter initial deposit: $");
        double interestRate = getDoubleInput("Enter interest rate (%): ");
        double overdraftLimit = getDoubleInput("Enter overdraft limit: $");
        
        BankAccount account = bank.createCheckingAccount(client, 
            BigDecimal.valueOf(initialDeposit), 
            BigDecimal.valueOf(interestRate), 
            BigDecimal.valueOf(overdraftLimit));
        
        System.out.println("✓ Checking account created successfully!");
        System.out.println("Account Number: " + account.getAccountNumber());
    }
    
    private static void createSavingsAccount() {
        System.out.print("Enter Client ID: ");
        String clientId = scanner.nextLine();
        
        Client client = bank.findClientById(clientId);
        if (client == null) {
            System.out.println("✗ Client not found with ID: " + clientId);
            return;
        }
        
        double initialDeposit = getDoubleInput("Enter initial deposit: $");
        double interestRate = getDoubleInput("Enter interest rate (%): ");
        double overdraftLimit = getDoubleInput("Enter overdraft limit: $");
        double minimumBalance = getDoubleInput("Enter minimum balance: $");
        int withdrawalLimit = getIntInput("Enter monthly withdrawal limit: ");
        
        BankAccount account = bank.createSavingsAccount(client, 
            BigDecimal.valueOf(initialDeposit), 
            BigDecimal.valueOf(interestRate), 
            BigDecimal.valueOf(overdraftLimit), 
            BigDecimal.valueOf(minimumBalance), 
            withdrawalLimit);
        
        System.out.println("✓ Savings account created successfully!");
        System.out.println("Account Number: " + account.getAccountNumber());
    }
    
    private static void viewAllAccounts() {
        List<BankAccount> accounts = bank.getAllAccounts();
        System.out.println("\n" + "─".repeat(120));
        System.out.println("ALL BANK ACCOUNTS");
        System.out.println("─".repeat(120));
        
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
        } else {
            System.out.printf("%-15s %-12s %-20s %-10s %-12s %-10s %-15s%n",
                "Account Number", "Type", "Client Name", "Balance", "Interest", "Overdraft", "Status");
            System.out.println("─".repeat(120));
            
            for (BankAccount account : accounts) {
                String type = account instanceof com.bank.models.SavingsAccount ? "Savings" : "Checking";
                String clientName = account.getOwner().getFirstName() + " " + account.getOwner().getLastName();
                String status = account.getIsActive() ? "Active" : "Inactive";
                
                System.out.printf("%-15s %-12s %-20s $%-9.2f %-11.1f%% $%-9.2f %-15s%n",
                    account.getAccountNumber(), type, clientName, account.getBalance(),
                    account.getInterestRate(), account.getOverdraftLimit(), status);
            }
        }
    }
    
    private static void findAccountByNumber() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        
        BankAccount account = bank.getAccount(accountNumber);
        if (account != null) {
            System.out.println("\n" + "─".repeat(60));
            System.out.println("ACCOUNT DETAILS");
            System.out.println("─".repeat(60));
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Type: " + (account instanceof com.bank.models.SavingsAccount ? "Savings" : "Checking"));
            System.out.println("Owner: " + account.getOwner().getFirstName() + " " + account.getOwner().getLastName());
            System.out.println("Balance: $" + account.getBalance());
            System.out.println("Interest Rate: " + account.getInterestRate() + "%");
            System.out.println("Overdraft Limit: $" + account.getOverdraftLimit());
            System.out.println("Status: " + (account.getIsActive() ? "Active" : "Inactive"));
            System.out.println("Open Date: " + account.getOpenAt());
            
            if (account instanceof com.bank.models.SavingsAccount savingsAccount) {
                System.out.println("Monthly Withdrawals: " + savingsAccount.getWithdrawalsThisMonth() + 
                                 "/" + savingsAccount.getMonthlyWithdrawalLimit());
                System.out.println("Minimum Balance: $" + savingsAccount.getMinimumBalance());
            }
        } else {
            System.out.println("✗ Account not found with number: " + accountNumber);
        }
    }
    
    private static void performTransactions() {
        boolean inTransactionMenu = true;
        while (inTransactionMenu) {
            System.out.println("\n" + "─".repeat(40));
            System.out.println("TRANSACTIONS");
            System.out.println("─".repeat(40));
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. Apply Interest to Account");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1 -> performDeposit();
                case 2 -> performWithdrawal();
                case 3 -> performTransfer();
                case 4 -> applyInterestToAccount();
                case 0 -> inTransactionMenu = false;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void performDeposit() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        
        BankAccount account = bank.getAccount(accountNumber);
        if (account == null) {
            System.out.println("✗ Account not found with number: " + accountNumber);
            return;
        }
        
        double amount = getDoubleInput("Enter deposit amount: $");
        
        try {
            account.deposit(BigDecimal.valueOf(amount));
            System.out.println("✓ Deposit successful!");
            System.out.println("New balance: $" + account.getBalance());
        } catch (Exception e) {
            System.out.println("✗ Deposit failed: " + e.getMessage());
        }
    }
    
    private static void performWithdrawal() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        
        BankAccount account = bank.getAccount(accountNumber);
        if (account == null) {
            System.out.println("✗ Account not found with number: " + accountNumber);
            return;
        }
        
        double amount = getDoubleInput("Enter withdrawal amount: $");
        
        try {
            account.withdraw(BigDecimal.valueOf(amount));
            System.out.println("✓ Withdrawal successful!");
            System.out.println("New balance: $" + account.getBalance());
        } catch (Exception e) {
            System.out.println("✗ Withdrawal failed: " + e.getMessage());
        }
    }
    
    private static void performTransfer() {
        System.out.print("Enter Source Account Number: ");
        String fromAccountNumber = scanner.nextLine();
        
        System.out.print("Enter Destination Account Number: ");
        String toAccountNumber = scanner.nextLine();
        
        BankAccount fromAccount = bank.getAccount(fromAccountNumber);
        BankAccount toAccount = bank.getAccount(toAccountNumber);
        
        if (fromAccount == null) {
            System.out.println("✗ Source account not found: " + fromAccountNumber);
            return;
        }
        if (toAccount == null) {
            System.out.println("✗ Destination account not found: " + toAccountNumber);
            return;
        }
        
        double amount = getDoubleInput("Enter transfer amount: $");
        
        try {
            fromAccount.transfer(BigDecimal.valueOf(amount), toAccount);
            System.out.println("✓ Transfer successful!");
            System.out.println("Source account balance: $" + fromAccount.getBalance());
            System.out.println("Destination account balance: $" + toAccount.getBalance());
        } catch (Exception e) {
            System.out.println("✗ Transfer failed: " + e.getMessage());
        }
    }
    
    private static void applyInterestToAccount() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        
        BankAccount account = bank.getAccount(accountNumber);
        if (account == null) {
            System.out.println("✗ Account not found with number: " + accountNumber);
            return;
        }
        
        try {
            account.applyInterest();
            System.out.println("✓ Interest applied successfully!");
            System.out.println("New balance: $" + account.getBalance());
        } catch (Exception e) {
            System.out.println("✗ Failed to apply interest: " + e.getMessage());
        }
    }
    
    private static void viewReports() {
        boolean inReportMenu = true;
        while (inReportMenu) {
            System.out.println("\n" + "─".repeat(40));
            System.out.println("REPORTS");
            System.out.println("─".repeat(40));
            System.out.println("1. Bank Summary Report");
            System.out.println("2. Client List");
            System.out.println("3. Account List");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1 -> bank.generateBankReport();
                case 2 -> viewAllClients();
                case 3 -> viewAllAccounts();
                case 0 -> inReportMenu = false;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    // Utility methods
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
    
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid amount.");
            }
        }
    }
}