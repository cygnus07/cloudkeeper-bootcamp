package org.example;
import java.util.Scanner;

public class ExceptionBasics {

    public static void divisionWithException() {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("enter first number ");
            int num1 = sc.nextInt();

            System.out.print("enter second number ");
            int num2 = sc.nextInt();

            int result = num1 / num2;
            System.out.println("result is " + result);

        } catch (ArithmeticException e) {
            System.out.println("error cannot divide by zero");
        } finally {
            System.out.println("division completed");
        }
    }

    public static void nestedTryCatch() {
        try {
            int[] arr = {1, 2, 3};

            try {
                System.out.println("trying to divide");
                int result = 10 / 0;
                System.out.println("result " + result);

            } catch (ArithmeticException e) {
                System.out.println("caught arithmetic exception " + e.getMessage());
            }

            System.out.println("trying to access array");
            System.out.println("array element " + arr[10]);

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("caught array index exception " + e.getMessage());
        }
    }

    public static void exceptionPropagation() {
        try {
            m1();
        } catch (ArithmeticException e) {
            System.out.println("caught exception in exceptionPropagation " + e.getMessage());
        }
    }

    private static void m1() {
        System.out.println("m1 called");
        m2();
    }

    private static void m2() {
        System.out.println("m2 called");
        m3();
    }

    private static void m3() {
        System.out.println("m3 called trying to divide by zero");
        int result = 10 / 0;
        System.out.println("result " + result);
    }

    public static void rethrowException() {
        try {
            try {
                System.out.println("dividing 10 by 0");
                int result = 10 / 0;

            } catch (ArithmeticException e) {
                System.out.println("caught in inner block rethrowing");
                throw e;
            }

        } catch (ArithmeticException e) {
            System.out.println("caught in outer block " + e.getMessage());
        }
    }

    public static void customEmailException() {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("enter your email ");
            String email = sc.nextLine();

            validateEmail(email);
            System.out.println("email is valid");

        } catch (InvalidEmailException e) {
            System.out.println("error " + e.getMessage());
        }
    }

    private static void validateEmail(String email) throws InvalidEmailException {
        if (!email.contains("@")) {
            throw new InvalidEmailException("email must contain @ symbol");
        }
    }
}
