package org.example;
import java.util.Scanner;

public class Basic {

    public static void sumOfDigits() {
        Scanner sc = new Scanner(System.in);
        System.out.println("enter a number ");
        int num = sc.nextInt();

        int sum = 0;
        int temp = num;

        while(temp > 0) {
            sum += temp % 10;
            temp /= 10;
        }

        System.out.println("sum of digits is " + sum);
    }

    public static void multiplicationTable() {
        Scanner sc = new Scanner(System.in);
        System.out.print("enter a number ");
        int num = sc.nextInt();

        System.out.println("\nmultiplication table of " + num);
        for(int i = 1; i <= 10; i++) {
            System.out.println(num + " x " + i + " = " + (num * i));
        }
    }

    public static void factorialCalculator() {
        Scanner sc = new Scanner(System.in);
        System.out.print("enter a number ");
        int num = sc.nextInt();

        long factorial = 1;

        for(int i = 1; i <= num; i++) {
            factorial *= i;
        }

        System.out.println("factorial of " + num + " is " + factorial);
    }

    public static void reverseNumber() {
        Scanner sc = new Scanner(System.in);
        System.out.print("enter a number ");
        int num = sc.nextInt();

        int reversed = 0;
        int temp = num;

        while(temp != 0) {
            int digit = temp % 10;
            reversed = reversed * 10 + digit;
            temp /= 10;
        }

        System.out.println("reversed number is " + reversed);
    }
}
