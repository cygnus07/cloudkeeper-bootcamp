package org.example;
import java.util.Scanner;


public class Basic {

    public static void run() {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Basic Problems ===");
        System.out.println("1. Sum of Digits");
        System.out.println("2. Multiplication Table");
        System.out.println("3. Factorial Calculator");
        System.out.println("4. Reverse a Number");
        System.out.print("\nChoose a problem (1-4): ");

        int choice = sc.nextInt();

        switch(choice) {
            case 1:
                sumOfDigits();
                break;
            case 2:
                multiplicationTable();
                break;
            case 3:
                factorialCalculator();
                break;
            case 4:
                reverseNumber();
                break;
            default:
                System.out.println("Invalid choice");
        }

        sc.close();
    }

    private static void sumOfDigits() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter a number: ");
        int num = sc.nextInt();

        int sum = 0;
        int temp = num;

        while(temp > 0) {
            sum += temp % 10;
            temp /= 10;
        }

        System.out.println("Sum of digits of " + num + " = " + sum);
    }

    private static void multiplicationTable() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter a number: ");
        int num = sc.nextInt();

        System.out.println("\nMultiplication Table of " + num + ":");
        for(int i = 1; i <= 10; i++) {
            System.out.println(num + " x " + i + " = " + (num * i));
        }
    }

    private static void factorialCalculator() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter a number: ");
        int num = sc.nextInt();

        long factorial = 1;

        for(int i = 1; i <= num; i++) {
            factorial *= i;
        }

        System.out.println("Factorial of " + num + " = " + factorial);
    }

    private static void reverseNumber() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter a number: ");
        int num = sc.nextInt();

        int reversed = 0;
        int temp = num;

        while(temp != 0) {
            int digit = temp % 10;
            reversed = reversed * 10 + digit;
            temp /= 10;
        }

        System.out.println("Reversed number: " + reversed);
    }
}