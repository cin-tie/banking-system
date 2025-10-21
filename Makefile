JAVAC = javac
JAVA = java
JAVAC_FLAGS = -d bin -cp src
JAVA_FLAGS = -cp bin

SRC_DIR = src
BIN_DIR = bin
MAIN_CLASS = com.bank.Main

SOURCES = \
	$(SRC_DIR)/com/bank/exceptions/AccountDeactivationException.java \
	$(SRC_DIR)/com/bank/exceptions/AccountNotActiveException.java \
	$(SRC_DIR)/com/bank/exceptions/InsufficientFundsException.java \
	$(SRC_DIR)/com/bank/exceptions/InvalidAmountException.java \
	$(SRC_DIR)/com/bank/models/enums/TransactionStatus.java \
	$(SRC_DIR)/com/bank/models/enums/TransactionType.java \
	$(SRC_DIR)/com/bank/models/Address.java \
	$(SRC_DIR)/com/bank/models/BankAccount.java \
	$(SRC_DIR)/com/bank/models/CheckingAccount.java \
	$(SRC_DIR)/com/bank/models/Client.java \
	$(SRC_DIR)/com/bank/models/SavingsAccount.java \
	$(SRC_DIR)/com/bank/models/Transaction.java \
	$(SRC_DIR)/com/bank/service/interfaces/NotificationService.java \
	$(SRC_DIR)/com/bank/service/implementations/EmailNotificationService.java \
	$(SRC_DIR)/com/bank/service/Bank.java \
	$(SRC_DIR)/com/bank/Main.java

all: compile

$(BIN_DIR):
	@mkdir -p $(BIN_DIR)

compile: $(BIN_DIR) $(SOURCES)
	@echo "Compiling Banking System..."
	$(JAVAC) $(JAVAC_FLAGS) $(SOURCES)
	@echo "Compilation completed successfully!"

run: compile
	@echo "Starting Banking System..."
	$(JAVA) $(JAVA_FLAGS) $(MAIN_CLASS)

clean:
	@echo "Cleaning compiled files..."
	@rm -rf $(BIN_DIR)
	@rm -rf BankingSystem.jar
	@echo "Clean completed!"

jar: compile
	@echo "Creating JAR file..."
	@jar cfe BankingSystem.jar $(MAIN_CLASS) -C $(BIN_DIR) .
	@echo "JAR file created: BankingSystem.jar"

run-jar: jar
	@echo "Running from JAR..."
	@java -jar BankingSystem.jar

help:
	@echo "Available targets:"
	@echo "  all      - Compile all source files (default)"
	@echo "  compile  - Compile the project"
	@echo "  run      - Compile and run the application"
	@echo "  clean    - Remove all compiled files"
	@echo "  jar      - Create executable JAR file"
	@echo "  run-jar  - Run from JAR file"
	@echo "  help     - Show this help message"

.PHONY: all compile run clean jar run-jar help