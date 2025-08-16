/**
 * Represents a bank account with basic operations
 * Demonstrates: Encapsulation, Data validation, Method overloading
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private String pin;
    private List<Transaction> transactionHistory;
    private Date accountCreationDate;

    // Constructor with initial balance
    public Account(String accountNumber, String accountHolderName, double initialBalance, String pin) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.pin = pin;
        this.transactionHistory = new ArrayList<>();
        this.accountCreationDate = new Date();

        // Add initial deposit transaction
        if (initialBalance > 0) {
            transactionHistory.add(new Transaction("DEPOSIT", initialBalance, 
                "Account opened with initial deposit"));
        }
    }

    // Method overloading - constructor without initial balance
    public Account(String accountNumber, String accountHolderName, String pin) {
        this(accountNumber, accountHolderName, 0.0, pin);
    }

    /**
     * Validates PIN for security
     * @param enteredPin PIN entered by user
     * @return true if PIN matches
     */
    public boolean validatePin(String enteredPin) {
        return this.pin.equals(enteredPin);
    }

    /**
     * Deposits money into the account
     * @param amount Amount to deposit
     * @return true if successful
     */
    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("❌ Invalid amount! Please enter a positive value.");
            return false;
        }

        if (amount > 50000) {
            System.out.println("❌ Daily deposit limit exceeded! Maximum deposit: ₹50,000");
            return false;
        }

        this.balance += amount;
        transactionHistory.add(new Transaction("DEPOSIT", amount, "Cash deposit via ATM"));
        System.out.println("✅ ₹" + String.format("%.2f", amount) + " deposited successfully!");
        return true;
    }

    /**
     * Withdraws money from the account
     * @param amount Amount to withdraw
     * @return true if successful
     */
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("❌ Invalid amount! Please enter a positive value.");
            return false;
        }

        if (amount > this.balance) {
            System.out.println("❌ Insufficient funds! Available balance: ₹" + 
                String.format("%.2f", this.balance));
            return false;
        }

        if (amount > 25000) {
            System.out.println("❌ Daily withdrawal limit exceeded! Maximum withdrawal: ₹25,000");
            return false;
        }

        // Check for minimum denomination
        if (amount % 100 != 0) {
            System.out.println("❌ Please enter amount in multiples of ₹100");
            return false;
        }

        this.balance -= amount;
        transactionHistory.add(new Transaction("WITHDRAWAL", amount, "Cash withdrawal via ATM"));
        System.out.println("✅ ₹" + String.format("%.2f", amount) + " withdrawn successfully!");
        System.out.println("💰 Remaining balance: ₹" + String.format("%.2f", this.balance));
        return true;
    }

    /**
     * Changes the PIN
     * @param oldPin Current PIN
     * @param newPin New PIN to set
     * @return true if successful
     */
    public boolean changePin(String oldPin, String newPin) {
        if (!validatePin(oldPin)) {
            System.out.println("❌ Invalid current PIN!");
            return false;
        }

        if (newPin.length() != 4 || !newPin.matches("\\d{4}")) {
            System.out.println("❌ PIN must be exactly 4 digits!");
            return false;
        }

        if (newPin.equals(oldPin)) {
            System.out.println("❌ New PIN cannot be the same as current PIN!");
            return false;
        }

        this.pin = newPin;
        transactionHistory.add(new Transaction("PIN_CHANGE", 0, "PIN changed successfully"));
        System.out.println("✅ PIN changed successfully!");
        return true;
    }

    /**
     * Gets mini statement (last 5 transactions)
     */
    public void printMiniStatement() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           MINI STATEMENT");
        System.out.println("=".repeat(50));
        System.out.println("Account: " + maskAccountNumber());
        System.out.println("Holder: " + accountHolderName);
        System.out.println("Current Balance: ₹" + String.format("%.2f", balance));
        System.out.println("\nRecent Transactions:");
        System.out.println("-".repeat(50));

        int transactionsToShow = Math.min(5, transactionHistory.size());
        if (transactionsToShow == 0) {
            System.out.println("No transactions found.");
        } else {
            for (int i = transactionHistory.size() - transactionsToShow; i < transactionHistory.size(); i++) {
                System.out.println(transactionHistory.get(i).toString());
            }
        }
        System.out.println("=".repeat(50));
    }

    /**
     * Masks account number for security
     */
    private String maskAccountNumber() {
        if (accountNumber.length() <= 4) return accountNumber;
        return "****" + accountNumber.substring(accountNumber.length() - 4);
    }

    // Getters and Setters (Encapsulation)
    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolderName() { return accountHolderName; }
    public double getBalance() { return balance; }
    public Date getAccountCreationDate() { return accountCreationDate; }
    public List<Transaction> getTransactionHistory() { return transactionHistory; }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + maskAccountNumber() + '\'' +
                ", accountHolderName='" + accountHolderName + '\'' +
                ", balance=" + String.format("%.2f", balance) +
                '}';
    }
}