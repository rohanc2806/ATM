import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bank customer with multiple accounts
 * Demonstrates: Composition, Collections usage, Business logic
 */
public class Customer {
    private String customerId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private List<Account> accounts;

    public Customer(String customerId, String firstName, String lastName, String phoneNumber, String email) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.accounts = new ArrayList<>();
    }

    /**
     * Adds an account to the customer
     */
    public void addAccount(Account account) {
        accounts.add(account);
    }

    /**
     * Finds account by account number
     */
    public Account findAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Gets customer's full name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // Getters and Setters
    public String getCustomerId() { return customerId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public List<Account> getAccounts() { return accounts; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", name='" + getFullName() + '\'' +
                ", accounts=" + accounts.size() +
                '}';
    }
}