package org.example;

public class ObjectCounter {
    private static int count = 0;

    public ObjectCounter() {
        count++;
    }

    public static int getCount() {
        return count;
    }

    public static void displayCount() {
        System.out.println("total objects created " + count);
    }
}
