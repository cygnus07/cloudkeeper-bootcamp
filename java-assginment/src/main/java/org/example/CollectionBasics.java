package org.example;
import java.util.ArrayList;
import java.util.List;

public class CollectionBasics {

    public static void customerPOJO() {
        List<Customer> customers = new ArrayList<>();

        Customer c1 = new Customer(1, "jim halpert", "jim@example.com");
        Customer c2 = new Customer(2, "dwight", "dwight@example.com");
        Customer c3 = new Customer(3, "michael Scott", "mike@example.com");

        customers.add(c1);
        customers.add(c2);
        customers.add(c3);

        System.out.println("customer details\n");

        for (Customer customer : customers) {
            customer.displayDetails();
        }
    }
}
