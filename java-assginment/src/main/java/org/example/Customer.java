package org.example;

public class Customer {
    private int id;
    private String name;
    private String email;

    public Customer(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public void displayDetails() {
        System.out.println("id " + id);
        System.out.println("name " + name);
        System.out.println("email " + email);
        System.out.println();
    }
}
