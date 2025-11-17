package employee;

public class Employee {
    protected int id;
    protected String name;
    protected String department;

    public Employee(int id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    public double calculateSalary() {
        return 0.0;
    }

    public void displayDetails() {
        System.out.println("\nemployee id " + id);
        System.out.println("name " + name);
        System.out.println("department " + department);
        System.out.println("salary " + calculateSalary());
    }
}
