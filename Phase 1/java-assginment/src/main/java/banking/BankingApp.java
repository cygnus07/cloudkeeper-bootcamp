package banking;

import java.util.Scanner;

public class BankingApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("mini banking application\n");

        System.out.print("enter account number ");
        int accNum = sc.nextInt();
        sc.nextLine();

        System.out.print("enter account holder name ");
        String name = sc.nextLine();

        System.out.print("enter initial balance ");
        double initialBalance = sc.nextDouble();

        BankAccount account = new BankAccount(accNum, name, initialBalance);
        account.displayDetails();

        while (true) {
            System.out.println("\n1 deposit");
            System.out.println("2 withdraw");
            System.out.println("3 check balance");
            System.out.println("4 exit");
            System.out.print("choose option ");

            int choice = sc.nextInt();

            try {
                if (choice == 1) {
                    System.out.print("enter deposit amount ");
                    double amount = sc.nextDouble();
                    account.deposit(amount);

                } else if (choice == 2) {
                    System.out.print("enter withdrawal amount ");
                    double amount = sc.nextDouble();
                    account.withdraw(amount);

                } else if (choice == 3) {
                    System.out.println("current balance " + account.getBalance());

                } else if (choice == 4) {
                    System.out.println("thank you for using our banking app");
                    break;

                } else {
                    System.out.println("invalid option");
                }

            } catch (InvalidAmountException | InsufficientBalanceException e) {
                System.out.println("error " + e.getMessage());
            }
        }

        BankAccount.displayTotalAccounts();
        sc.close();
    }
}
