import java.util.Scanner;

/**
 * Main ATM Simulator application
 * Demonstrates: CLI interface, User interaction, Input validation, Security
 */
public class ATMSimulator {

    private static final Scanner scanner = new Scanner(System.in);
    private static Bank bank;
    private static Account currentAccount;
    private static final int MAX_PIN_ATTEMPTS = 3;

    public static void main(String[] args) {
        // Initialize the bank
        bank = Bank.getInstance("State Bank of Java");

        // Display welcome screen
        displayWelcomeScreen();

        // Start ATM operations
        startATMOperations();

        // Close scanner
        scanner.close();
        System.out.println("\n👋 Thank you for using our ATM service!");
        System.out.println("💙 Have a great day!");
    }

    /**
     * Displays the welcome screen with bank information
     */
    private static void displayWelcomeScreen() {
        clearScreen();
        System.out.println("╔" + "=".repeat(60) + "╗");
        System.out.println("║" + " ".repeat(18) + "WELCOME TO ATM SIMULATOR" + " ".repeat(18) + "║");
        System.out.println("║" + " ".repeat(60) + "║");
        System.out.println("║" + " ".repeat(15) + "🏦 STATE BANK OF JAVA 🏦" + " ".repeat(16) + "║");
        System.out.println("║" + " ".repeat(60) + "║");
        System.out.println("║" + " ".repeat(12) + "Your Money, Your Security, Our Priority" + " ".repeat(10) + "║");
        System.out.println("╚" + "=".repeat(60) + "╝");
        System.out.println();

        // Display demo accounts for easy testing
        bank.displayDemoAccounts();

        // Simulate ATM initialization
        System.out.print("🔧 Initializing ATM services");
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(500);
                System.out.print(".");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(" ✅ Ready!\n");
    }

    /**
     * Main ATM operations loop
     */
    private static void startATMOperations() {
        boolean continueOperations = true;

        while (continueOperations) {
            try {
                // Authentication phase
                if (authenticateUser()) {
                    // Main ATM menu
                    handleATMOperations();
                } else {
                    System.out.println("\n❌ Authentication failed. Please try again later.");
                    waitForUser();
                }

                // Ask if user wants to perform another transaction
                continueOperations = askForAnotherTransaction();

            } catch (Exception e) {
                System.out.println("\n❌ An error occurred: " + e.getMessage());
                System.out.println("Please try again.");
                waitForUser();
            }
        }
    }

    /**
     * Handles user authentication with PIN validation
     */
    private static boolean authenticateUser() {
        clearScreen();
        System.out.println("🔐 ATM Authentication");
        System.out.println("=" + "=".repeat(30));

        int attempts = 0;
        while (attempts < MAX_PIN_ATTEMPTS) {
            try {
                // Get account number
                System.out.print("\n💳 Enter your Account Number (10 digits): ");
                String accountNumber = scanner.nextLine().trim();

                // Validate account number format
                if (!accountNumber.matches("\\d{10}")) {
                    System.out.println("❌ Invalid account number format! Please enter 10 digits.");
                    continue;
                }

                // Find account
                Account account = bank.findAccount(accountNumber);
                if (account == null) {
                    System.out.println("❌ Account not found! Please check your account number.");
                    attempts++;
                    continue;
                }

                // Get PIN
                System.out.print("🔑 Enter your 4-digit PIN: ");
                String pin = scanner.nextLine().trim();

                // Validate PIN format
                if (!pin.matches("\\d{4}")) {
                    System.out.println("❌ Invalid PIN format! Please enter 4 digits.");
                    attempts++;
                    continue;
                }

                // Validate PIN
                if (account.validatePin(pin)) {
                    currentAccount = account;
                    System.out.println("\n✅ Authentication successful!");
                    System.out.println("👋 Welcome, " + account.getAccountHolderName() + "!");

                    // Brief loading animation
                    System.out.print("🔄 Loading your account");
                    for (int i = 0; i < 3; i++) {
                        try {
                            Thread.sleep(300);
                            System.out.print(".");
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.println(" ✅");

                    return true;
                } else {
                    attempts++;
                    int remainingAttempts = MAX_PIN_ATTEMPTS - attempts;
                    if (remainingAttempts > 0) {
                        System.out.println("❌ Incorrect PIN! " + remainingAttempts + " attempts remaining.");
                    }
                }

            } catch (Exception e) {
                System.out.println("❌ Invalid input! Please try again.");
                attempts++;
            }
        }

        System.out.println("\n🚫 Maximum PIN attempts exceeded!");
        System.out.println("🔒 Your account has been temporarily locked for security.");
        System.out.println("📞 Please contact customer service: 1800-XXX-XXXX");

        return false;
    }

    /**
     * Main ATM operations menu and handling
     */
    private static void handleATMOperations() {
        boolean sessionActive = true;

        while (sessionActive) {
            displayMainMenu();

            try {
                int choice = getIntInput("👆 Enter your choice (1-6): ");

                switch (choice) {
                    case 1:
                        handleBalanceInquiry();
                        break;
                    case 2:
                        handleCashWithdrawal();
                        break;
                    case 3:
                        handleCashDeposit();
                        break;
                    case 4:
                        handlePinChange();
                        break;
                    case 5:
                        handleMiniStatement();
                        break;
                    case 6:
                        sessionActive = false;
                        handleSessionEnd();
                        break;
                    default:
                        System.out.println("❌ Invalid choice! Please select 1-6.");
                }

                if (sessionActive && choice != 6) {
                    waitForUser();
                }

            } catch (Exception e) {
                System.out.println("❌ Invalid input! Please enter a number between 1-6.");
                waitForUser();
            }
        }
    }

    /**
     * Displays the main ATM menu
     */
    private static void displayMainMenu() {
        clearScreen();
        System.out.println("╔" + "=".repeat(50) + "╗");
        System.out.println("║" + " ".repeat(15) + "ATM MAIN MENU" + " ".repeat(22) + "║");
        System.out.println("║" + " ".repeat(50) + "║");
        System.out.println("║  1. 💰 Balance Inquiry" + " ".repeat(25) + "║");
        System.out.println("║  2. 💸 Cash Withdrawal" + " ".repeat(25) + "║");
        System.out.println("║  3. 💵 Cash Deposit" + " ".repeat(28) + "║");
        System.out.println("║  4. 🔑 Change PIN" + " ".repeat(31) + "║");
        System.out.println("║  5. 📄 Mini Statement" + " ".repeat(26) + "║");
        System.out.println("║  6. 🚪 Exit" + " ".repeat(36) + "║");
        System.out.println("║" + " ".repeat(50) + "║");
        System.out.println("╚" + "=".repeat(50) + "╝");
        System.out.println("\n👤 Account: " + maskAccountNumber(currentAccount.getAccountNumber()));
        System.out.println("👋 Welcome, " + currentAccount.getAccountHolderName());
    }

    /**
     * Handles balance inquiry
     */
    private static void handleBalanceInquiry() {
        clearScreen();
        System.out.println("💰 BALANCE INQUIRY");
        System.out.println("=" + "=".repeat(30));
        System.out.println();
        System.out.println("💳 Account: " + maskAccountNumber(currentAccount.getAccountNumber()));
        System.out.println("👤 Name: " + currentAccount.getAccountHolderName());
        System.out.println();
        System.out.println("💵 Available Balance: ₹" + String.format("%.2f", currentAccount.getBalance()));
        System.out.println();
        System.out.println("✅ Balance inquiry completed successfully!");
    }

    /**
     * Handles cash withdrawal
     */
    private static void handleCashWithdrawal() {
        clearScreen();
        System.out.println("💸 CASH WITHDRAWAL");
        System.out.println("=" + "=".repeat(30));
        System.out.println("\n💰 Current Balance: ₹" + String.format("%.2f", currentAccount.getBalance()));
        System.out.println("\n💡 Quick withdrawal amounts:");
        System.out.println("  1. ₹500     2. ₹1,000   3. ₹2,000");
        System.out.println("  4. ₹5,000   5. ₹10,000  6. Other amount");
        System.out.println();

        try {
            int choice = getIntInput("Select option (1-6): ");
            double amount = 0;

            switch (choice) {
                case 1: amount = 500; break;
                case 2: amount = 1000; break;
                case 3: amount = 2000; break;
                case 4: amount = 5000; break;
                case 5: amount = 10000; break;
                case 6:
                    amount = getDoubleInput("💰 Enter withdrawal amount: ₹");
                    break;
                default:
                    System.out.println("❌ Invalid choice!");
                    return;
            }

            // Confirmation
            System.out.println("\n🔄 Processing withdrawal of ₹" + String.format("%.2f", amount));
            System.out.print("⏳ Please wait");

            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(500);
                    System.out.print(".");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println();

            if (currentAccount.withdraw(amount)) {
                System.out.println("\n💰 Please collect your cash from the dispenser");
                System.out.println("🧾 Transaction receipt printed");
                System.out.println("💳 Please take your card");
            }

        } catch (Exception e) {
            System.out.println("❌ Invalid amount entered!");
        }
    }

    /**
     * Handles cash deposit
     */
    private static void handleCashDeposit() {
        clearScreen();
        System.out.println("💵 CASH DEPOSIT");
        System.out.println("=" + "=".repeat(30));
        System.out.println("\n💰 Current Balance: ₹" + String.format("%.2f", currentAccount.getBalance()));
        System.out.println("\n📝 Instructions:");
        System.out.println("  • Insert cash notes into the deposit slot");
        System.out.println("  • Maximum deposit: ₹50,000 per transaction");
        System.out.println("  • Only ₹100, ₹200, ₹500, ₹2000 notes accepted");
        System.out.println();

        try {
            double amount = getDoubleInput("💰 Enter deposit amount: ₹");

            // Simulate cash counting
            System.out.println("\n🔄 Counting and validating cash...");
            System.out.print("⏳ Please wait");

            for (int i = 0; i < 4; i++) {
                try {
                    Thread.sleep(400);
                    System.out.print(".");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println();

            if (currentAccount.deposit(amount)) {
                System.out.println("\n✅ Cash deposit successful!");
                System.out.println("💰 New Balance: ₹" + String.format("%.2f", currentAccount.getBalance()));
                System.out.println("🧾 Transaction receipt printed");
            }

        } catch (Exception e) {
            System.out.println("❌ Invalid amount entered!");
        }
    }

    /**
     * Handles PIN change
     */
    private static void handlePinChange() {
        clearScreen();
        System.out.println("🔑 CHANGE PIN");
        System.out.println("=" + "=".repeat(30));
        System.out.println("\n🔒 For security, please enter your current PIN and new PIN");
        System.out.println();

        try {
            String currentPin = getPasswordInput("🔑 Enter current PIN (4 digits): ");

            if (currentPin.length() != 4 || !currentPin.matches("\\d{4}")) {
                System.out.println("❌ Invalid PIN format!");
                return;
            }

            String newPin = getPasswordInput("🆕 Enter new PIN (4 digits): ");

            if (newPin.length() != 4 || !newPin.matches("\\d{4}")) {
                System.out.println("❌ Invalid PIN format!");
                return;
            }

            String confirmPin = getPasswordInput("✅ Confirm new PIN (4 digits): ");

            if (!newPin.equals(confirmPin)) {
                System.out.println("❌ New PIN and confirmation don't match!");
                return;
            }

            // Processing animation
            System.out.println("\n🔄 Processing PIN change...");
            System.out.print("⏳ Please wait");

            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(500);
                    System.out.print(".");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println();

            currentAccount.changePin(currentPin, newPin);

        } catch (Exception e) {
            System.out.println("❌ Error processing PIN change!");
        }
    }

    /**
     * Handles mini statement display
     */
    private static void handleMiniStatement() {
        clearScreen();
        currentAccount.printMiniStatement();
    }

    /**
     * Handles session end
     */
    private static void handleSessionEnd() {
        clearScreen();
        System.out.println("🚪 SESSION END");
        System.out.println("=" + "=".repeat(20));
        System.out.println();
        System.out.println("✅ Transaction completed successfully!");
        System.out.println("🙏 Thank you for banking with us!");
        System.out.println();
        System.out.println("🔒 For your security:");
        System.out.println("  • Please take your card");
        System.out.println("  • Keep your PIN confidential");
        System.out.println("  • Check your account regularly");
        System.out.println();
        System.out.println("📞 Customer Service: 1800-XXX-XXXX");
        System.out.println("🌐 Online Banking: www.statebankofjava.com");

        currentAccount = null; // Clear current session

        // Countdown before next session
        System.out.println("\n⏳ ATM will be ready for next customer in...");
        for (int i = 3; i > 0; i--) {
            System.out.print(i + "... ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Ready! 🎯\n");
    }

    /**
     * Asks user if they want to perform another transaction
     */
    private static boolean askForAnotherTransaction() {
        if (currentAccount != null) return false; // Already logged out

        System.out.println("\n" + "=".repeat(50));
        System.out.print("🔄 Would you like to perform another transaction? (y/n): ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.startsWith("y");
    }

    // Utility methods

    private static void clearScreen() {
        // Print multiple lines to simulate screen clear
        for (int i = 0; i < 3; i++) {
            System.out.println();
        }
    }

    private static void waitForUser() {
        System.out.println("\n📱 Press Enter to continue...");
        scanner.nextLine();
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        return Integer.parseInt(scanner.nextLine().trim());
    }

    private static double getDoubleInput(String prompt) {
        System.out.print(prompt);
        return Double.parseDouble(scanner.nextLine().trim());
    }

    private static String getPasswordInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static String maskAccountNumber(String accountNumber) {
        if (accountNumber.length() <= 4) return accountNumber;
        return "****" + accountNumber.substring(accountNumber.length() - 4);
    }
}