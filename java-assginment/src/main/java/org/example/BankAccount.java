package org.example;

public class BankAccount {
    int accountNumber;
    String holderName;
    double balance;

    public BankAccount(int accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    public void displayDetails() {
        System.out.println("\naccount details");
        System.out.println("account number " + accountNumber);
        System.out.println("holder name " + holderName);
        System.out.println("balance " + balance);
    }
}
