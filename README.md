# Banking Management System

A comprehensive Java-based banking system that simulates real-world banking operations including client management, account handling, transactions, and reporting.

## Table of Contents

- [Features](#features)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Usage](#usage)
- [Class Documentation](#class-documentation)
- [Build Instructions](#build-instructions)
- [Exception Handling](#exception-handling)
- [Contributing](#contributing)

## Features

### Client Management
- Register new clients with complete personal information
- View all registered clients
- Find clients by unique ID
- Comprehensive client profiles with address details

### Account Management
- **Checking Accounts**: Basic accounts with overdraft protection
- **Savings Accounts**: Interest-bearing accounts with withdrawal limits
- Create multiple accounts per client
- Account status management (active/inactive)
- View account details and transaction history

### Transaction System
- Deposit funds to any account
- Withdraw with sufficient funds validation
- Transfer between accounts with rollback capability
- Interest application for savings accounts
- Transaction status tracking (Success, Failed, In Process, Canceled)

### Reporting & Analytics
- Bank summary reports
- Client and account listings
- Transaction statistics
- Balance and interest calculations

### Notification System
- Email notifications for transactions
- Low balance alerts
- Deposit/withdrawal confirmations
- Transfer notifications

## Project Structure

```
banking-system/
├── src/
│   ├── com/bank/
│   │   ├── exceptions/          # Custom exceptions
│   │   ├── models/             # Core domain models
│   │   ├── models/enums/       # Enumerations
│   │   └── service/            # Business logic
│   │       ├── implementations/# Service implementations
│   │       └── interfaces/     # Service interfaces
├── bin/                        # Compiled classes
├── Makefile                   # Build automation
└── BankingSystem.jar          # Executable JAR
```

## Installation

### Prerequisites
- Java JDK 8 or higher
- Make utility (optional, for build automation)

### Quick Start

1. **Clone or download the project**
   ```bash
   git clone <repository-url>
   cd banking-system
   ```

2. **Compile the project**
   ```bash
   make compile
   ```
   *or manually:*
   ```bash
   javac -d bin -cp src src/com/bank/Main.java
   ```

3. **Run the application**
   ```bash
   make run
   ```
   *or manually:*
   ```bash
   java -cp bin com.bank.Main
   ```

## Usage

### Main Menu Options

1. **Client Management**
   - Register new clients
   - View all clients
   - Find client by ID
   - View client accounts

2. **Account Management**
   - Create checking accounts
   - Create savings accounts
   - View all accounts
   - Find account by number

3. **Transactions**
   - Deposit funds
   - Withdraw funds
   - Transfer between accounts
   - Apply interest

4. **Reports**
   - Bank summary
   - Client list
   - Account list

### Sample Data
The system automatically creates sample data:
- 3 demo clients with addresses
- 5 sample accounts (checking and savings)
- Pre-configured with realistic balances and limits

## Class Documentation

### Core Models

#### `BankAccount` (Abstract)
Base class for all account types with common banking operations.

**Key Methods:**
- `deposit(BigDecimal amount)` - Add funds to account
- `withdraw(BigDecimal amount)` - Remove funds with validation
- `transfer(BigDecimal amount, BankAccount recipient)` - Transfer between accounts
- `applyInterest()` - Calculate and add interest

#### `CheckingAccount`
Extends `BankAccount` with overdraft protection.

#### `SavingsAccount` 
Extends `BankAccount` with interest rates and withdrawal limits.

#### `Client`
Represents banking customers with personal information and addresses.

#### `Transaction`
Tracks all financial operations with status and type information.

### Services

#### `Bank`
Main service class managing all banking operations.

**Key Features:**
- Client registration and management
- Account creation and retrieval
- Transaction processing
- Report generation

#### `EmailNotificationService`
Implements notification interface for customer communications.

### Exceptions

- `AccountNotActiveException` - Operations on inactive accounts
- `InsufficientFundsException` - Withdrawal/transfer exceeds balance
- `InvalidAmountException` - Negative or zero amount operations
- `AccountDeactivationException` - Cannot deactivate accounts with balances

## Build Instructions

### Using Makefile
```bash
# Compile project
make compile

# Run application
make run

# Create executable JAR
make jar

# Run from JAR
make run-jar

# Clean build files
make clean
```

### Manual Build
```bash
# Compile all source files
find src -name "*.java" > sources.txt
javac -d bin @sources.txt

# Create JAR
jar cfe BankingSystem.jar com.bank.Main -C bin .

# Run JAR
java -jar BankingSystem.jar
```

## Exception Handling

The system implements comprehensive exception handling:

- **Validation**: All inputs are validated for correctness
- **Account Status**: Operations blocked on inactive accounts
- **Funds Checking**: Prevents overdrafts beyond limits
- **Transaction Safety**: Transfers include rollback mechanisms

### Common Error Scenarios

```java
try {
    account.withdraw(amount);
} catch (InsufficientFundsException e) {
    System.out.println("Error: " + e.getMessage());
} catch (AccountNotActiveException e) {
    System.out.println("Account is not active");
}
```

## Contributing

### Development Setup

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Ensure all tests pass
5. Submit a pull request

### Code Standards

- Follow Java naming conventions
- Include JavaDoc comments for public methods
- Maintain exception handling consistency
- Update documentation for new features

### Building from Source

```bash
# Set up development environment
make clean
make compile
make run
```

## License

This project is for educational purposes. Feel free to use and modify as needed.

---

## Quick Commands Reference

```bash
# Development workflow
make compile      # Build the project
make run         # Run the application
make jar         # Create executable JAR
make clean       # Clean build artifacts

# Direct execution
java -cp bin com.bank.Main
java -jar BankingSystem.jar
```

For questions or issues, please check the code documentation or review the exception messages displayed during operation.