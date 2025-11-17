package product;

import java.util.ArrayList;
import java.util.List;

public class ProductApp {

    public static void main(String[] args) {
        System.out.println("product catalog\n");

        List<Product> products = new ArrayList<>();

        try {
            Product p1 = new Product(1, "laptop", 999.99);
            Product p2 = new Product(2, "mouse", 25.50);
            Product p3 = new Product(3, "keyboard", 75.00);
            Product p4 = new Product(4, "monitor", 299.99);
            Product p5 = new Product(5, "headphones", 49.99);

            products.add(p1);
            products.add(p2);
            products.add(p3);
            products.add(p4);
            products.add(p5);

            System.out.println("all products");
            for (Product product : products) {
                product.displayDetails();
            }

            System.out.println("\n\ntrying to set negative price");
            p1.setPrice(-100);

        } catch (NegativePriceException e) {
            System.out.println("error " + e.getMessage());
        }

        System.out.println("\n\ntrying to create product with negative price");
        try {
            Product invalidProduct = new Product(6, "invalid item", -50);
        } catch (NegativePriceException e) {
            System.out.println("error " + e.getMessage());
        }
    }
}
