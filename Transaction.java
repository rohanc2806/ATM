import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a banking transaction
 * Demonstrates: Encapsulation, Data integrity, Immutable objects
 */
public class Transaction {
    private final String transactionId;
    private final String type;
    private final double amount;
    private final Date timestamp;
    private final String description;
    private static int transactionCounter = 1000;

    public Transaction(String type, double amount, String description) {
        this.transactionId = "TXN" + (++transactionCounter);
        this.type = type;
        this.amount = amount;
        this.timestamp = new Date();
        this.description = description;
    }

    // Getters (No setters to maintain immutability)
    public String getTransactionId() { return transactionId; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public Date getTimestamp() { return timestamp; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String symbol = type.equals("DEPOSIT") ? "+" : type.equals("WITHDRAWAL") ? "-" : " ";
        return String.format("%-12s | %s | %s₹%-8.2f | %s", 
            transactionId, 
            sdf.format(timestamp), 
            symbol,
            amount, 
            description);
    }
}