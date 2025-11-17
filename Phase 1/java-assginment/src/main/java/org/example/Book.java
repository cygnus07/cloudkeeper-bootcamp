package org.example;

public class Book {
    private String title;
    private String author;
    private double price;

    public Book(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("title " + title);
        System.out.println("author " + author);
        System.out.println("price " + price);
    }
}
