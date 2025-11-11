package org.example;

public class Car {
    private String model;
    private double price;

    public Car(String model) {
        this.model = model;
        this.price = 0.0;
    }

    public Car(String model, double price) {
        this.model = model;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("model " + model);
        if (price > 0) {
            System.out.println("price " + price);
        } else {
            System.out.println("price not specified");
        }
    }
}
