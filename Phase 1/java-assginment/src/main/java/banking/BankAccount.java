package banking;

public class BankAccount {
    private static int totalAccounts = 0;

    private int accountNumber;
    private String holderName;
    private double balance;

    public BankAccount(int accountNumber, String holderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = initialBalance;
        totalAccounts++;
    }

    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("deposit amount must be positive");
        }
        balance += amount;
        System.out.println("deposited " + amount);
        System.out.println("new balance " + balance);
    }

    public void withdraw(double amount) throws InvalidAmountException, InsufficientBalanceException {
        if (amount <= 0) {
            throw new InvalidAmountException("withdrawal amount must be positive");
        }
        if (amount > balance) {
            throw new InsufficientBalanceException("insufficient balance current balance is " + balance);
        }
        balance -= amount;
        System.out.println("withdrew " + amount);
        System.out.println("new balance " + balance);
    }

    public double getBalance() {
        return balance;
    }

    public void displayDetails() {
        System.out.println("\naccount details");
        System.out.println("account number " + accountNumber);
        System.out.println("holder name " + holderName);
        System.out.println("balance " + balance);
    }

    public static int getTotalAccounts() {
        return totalAccounts;
    }

    public static void displayTotalAccounts() {
        System.out.println("\ntotal accounts created " + totalAccounts);
    }
}
