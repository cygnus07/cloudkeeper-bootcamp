package org.example;

public class Student {
    String name;
    int marks1;
    int marks2;
    int marks3;

    public int calculateTotal() {
        return marks1 + marks2 + marks3;
    }

    public double calculateAverage() {
        return calculateTotal() / 3.0;
    }

    public void displayReport() {
        System.out.println("\nstudent report");
        System.out.println("name " + name);
        System.out.println("marks " + marks1 + " " + marks2 + " " + marks3);
        System.out.println("total marks " + calculateTotal());
        System.out.println("average " + calculateAverage());
    }
}
