package product;

public class Product {
    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price) throws NegativePriceException {
        if (price < 0) {
            throw new NegativePriceException("price cannot be negative");
        }
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) throws NegativePriceException {
        if (price < 0) {
            throw new NegativePriceException("price cannot be negative");
        }
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("\nproduct id " + id);
        System.out.println("name " + name);
        System.out.println("price " + price);
    }
}
