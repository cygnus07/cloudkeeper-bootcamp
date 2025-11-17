package org.example;
import java.util.Scanner;

public class OOPBasics {

    public static void simpleCalculator() {
        Scanner sc = new Scanner(System.in);
        Calculator calc = new Calculator();

        System.out.print("enter first number ");
        int num1 = sc.nextInt();
        System.out.print("enter second number ");
        int num2 = sc.nextInt();

        System.out.println("\ncalculator results");
        System.out.println("addition " + calc.add(num1, num2));
        System.out.println("subtraction " + calc.subtract(num1, num2));
        System.out.println("multiplication " + calc.multiply(num1, num2));
        System.out.println("division " + calc.divide(num1, num2));
    }

    public static void studentReport() {
        Scanner sc = new Scanner(System.in);
        Student student = new Student();

        System.out.print("enter student name ");
        student.name = sc.nextLine();

        System.out.print("enter marks for subject 1 ");
        student.marks1 = sc.nextInt();
        System.out.print("enter marks for subject 2 ");
        student.marks2 = sc.nextInt();
        System.out.print("enter marks for subject 3 ");
        student.marks3 = sc.nextInt();

        student.displayReport();
    }

    public static void bankAccount() {
        Scanner sc = new Scanner(System.in);

        System.out.print("enter account number ");
        int accNum = sc.nextInt();
        sc.nextLine();

        System.out.print("enter holder name ");
        String name = sc.nextLine();

        System.out.print("enter initial balance ");
        double bal = sc.nextDouble();

        BankAccount account = new BankAccount(accNum, name, bal);
        account.displayDetails();
    }

    public static void objectCounter() {
        System.out.println("\ncreating objects");

        ObjectCounter obj1 = new ObjectCounter();
        System.out.println("created object 1");
        ObjectCounter.displayCount();

        ObjectCounter obj2 = new ObjectCounter();
        System.out.println("created object 2");
        ObjectCounter.displayCount();

        ObjectCounter obj3 = new ObjectCounter();
        System.out.println("created object 3");
        ObjectCounter.displayCount();
    }

    public static void mathUtils() {
        System.out.println("\nmath utils with static methods");

        int a = 15;
        int b = 25;
        int c = 20;

        System.out.println("numbers " + a + " " + b + " " + c);
        System.out.println("max of " + a + " and " + b + " is " + MathUtils.max(a, b));
        System.out.println("min of " + a + " and " + b + " is " + MathUtils.min(a, b));
        System.out.println("average of " + a + " and " + b + " is " + MathUtils.average(a, b));
        System.out.println("average of all three is " + MathUtils.average(a, b, c));
    }

    public static void bookDetails() {
        System.out.println("\nbook details");

        Book book1 = new Book("The Kite Runner", "Khalid Hosseine", 12.99);
        book1.displayDetails();

        System.out.println();

        Book book2 = new Book("1984", "George Orwell", 14.99);
        book2.displayDetails();
    }

    public static void carDetails() {
        System.out.println("\ncar details");

        Car car1 = new Car("Toyota Camry");
        car1.displayDetails();

        System.out.println();

        Car car2 = new Car("Honda Accord", 25000.00);
        car2.displayDetails();
    }
}
