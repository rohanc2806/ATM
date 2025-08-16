import java.util.HashMap;
import java.util.Map;

/**
 * Bank class manages all customers and accounts
 * Demonstrates: Aggregation, Collections, Business logic, Singleton pattern
 */
public class Bank {
    private static Bank instance;
    private Map<String, Customer> customers;
    private Map<String, Account> accounts;
    private String bankName;

    // Private constructor for Singleton pattern
    private Bank(String bankName) {
        this.bankName = bankName;
        this.customers = new HashMap<>();
        this.accounts = new HashMap<>();
        initializeSampleData();
    }

    // Singleton getInstance method
    public static Bank getInstance(String bankName) {
        if (instance == null) {
            instance = new Bank(bankName);
        }
        return instance;
    }

    public static Bank getInstance() {
        return instance;
    }

    /**
     * Initializes the bank with sample customer data
     * This makes the ATM immediately usable for demonstration
     */
    private void initializeSampleData() {
        // Create sample customers and accounts

        // Customer 1: John Doe
        Customer john = new Customer("CUST001", "John", "Doe", "9876543210", "john.doe@email.com");
        Account johnSavings = new Account("1234567890", "John Doe", 15000.0, "1234");
        john.addAccount(johnSavings);
        customers.put("CUST001", john);
        accounts.put("1234567890", johnSavings);

        // Customer 2: Jane Smith
        Customer jane = new Customer("CUST002", "Jane", "Smith", "9876543211", "jane.smith@email.com");
        Account janeSavings = new Account("1234567891", "Jane Smith", 25000.0, "5678");
        jane.addAccount(janeSavings);
        customers.put("CUST002", jane);
        accounts.put("1234567891", janeSavings);

        // Customer 3: Mike Johnson
        Customer mike = new Customer("CUST003", "Mike", "Johnson", "9876543212", "mike.johnson@email.com");
        Account mikeSavings = new Account("1234567892", "Mike Johnson", 8500.0, "9999");
        mike.addAccount(mikeSavings);
        customers.put("CUST003", mike);
        accounts.put("1234567892", mikeSavings);

        // Customer 4: Sarah Wilson (for testing edge cases)
        Customer sarah = new Customer("CUST004", "Sarah", "Wilson", "9876543213", "sarah.wilson@email.com");
        Account sarahSavings = new Account("1234567893", "Sarah Wilson", 500.0, "0000");
        sarah.addAccount(sarahSavings);
        customers.put("CUST004", sarah);
        accounts.put("1234567893", sarahSavings);

        System.out.println("🏦 Bank initialized with sample data:");
        System.out.println("   • 4 customers with accounts created");
        System.out.println("   • Ready for ATM operations");
        System.out.println();
    }

    /**
     * Finds customer by customer ID
     */
    public Customer findCustomer(String customerId) {
        return customers.get(customerId);
    }

    /**
     * Finds account by account number
     */
    public Account findAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    /**
     * Adds a new customer to the bank
     */
    public void addCustomer(Customer customer) {
        customers.put(customer.getCustomerId(), customer);

        // Add all customer accounts to the accounts map
        for (Account account : customer.getAccounts()) {
            accounts.put(account.getAccountNumber(), account);
        }
    }

    /**
     * Validates account and PIN combination
     */
    public boolean validateAccountAndPin(String accountNumber, String pin) {
        Account account = findAccount(accountNumber);
        return account != null && account.validatePin(pin);
    }

    /**
     * Gets bank statistics
     */
    public void printBankStats() {
        System.out.println("\n🏦 " + bankName + " - System Statistics");
        System.out.println("=" + "=".repeat(40));
        System.out.println("Total Customers: " + customers.size());
        System.out.println("Total Accounts: " + accounts.size());

        double totalBalance = accounts.values().stream()
            .mapToDouble(Account::getBalance)
            .sum();
        System.out.println("Total Bank Balance: ₹" + String.format("%.2f", totalBalance));
        System.out.println("=" + "=".repeat(40));
    }

    /**
     * Displays available demo accounts for testing
     */
    public void displayDemoAccounts() {
        System.out.println("\n🎯 DEMO ACCOUNTS FOR TESTING:");
        System.out.println("=" + "=".repeat(50));
        System.out.println("Account Number | PIN  | Account Holder    | Balance");
        System.out.println("-" + "-".repeat(49));
        System.out.println("1234567890     | 1234 | John Doe         | ₹15,000.00");
        System.out.println("1234567891     | 5678 | Jane Smith       | ₹25,000.00");
        System.out.println("1234567892     | 9999 | Mike Johnson     | ₹8,500.00");
        System.out.println("1234567893     | 0000 | Sarah Wilson     | ₹500.00");
        System.out.println("=" + "=".repeat(50));
        System.out.println("💡 Use any of these accounts to test the ATM!");
        System.out.println();
    }

    // Getters
    public String getBankName() { return bankName; }
    public Map<String, Customer> getCustomers() { return customers; }
    public Map<String, Account> getAccounts() { return accounts; }
}