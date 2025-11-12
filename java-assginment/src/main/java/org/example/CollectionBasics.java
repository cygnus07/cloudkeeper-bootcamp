package org.example;
import java.util.*;

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

    public static void nonRepeat() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a string");
        String s = sc.next();

        Map<Character, Integer> map = new HashMap<>();
        for(char c: s.toCharArray()){
            map.put(c, map.getOrDefault(c,0) +1);
        }
        for(char c: s.toCharArray()){
            if(map.get(c) == 1){
                System.out.println(c);
                return;
            }
        }
        System.out.println("No repeating character found");


    }
}
