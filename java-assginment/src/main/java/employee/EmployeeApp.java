package employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeApp {

    public static void main(String[] args) {
        System.out.println("employee management system\n");

        List<Employee> employees = new ArrayList<>();

        FullTimeEmployee ft1 = new FullTimeEmployee(1, "john doe", "engineering", 5000.0);
        FullTimeEmployee ft2 = new FullTimeEmployee(2, "jane smith", "marketing", 4500.0);

        PartTimeEmployee pt1 = new PartTimeEmployee(3, "bob wilson", "support", 20.0, 80);
        PartTimeEmployee pt2 = new PartTimeEmployee(4, "alice brown", "sales", 25.0, 60);

        employees.add(ft1);
        employees.add(ft2);
        employees.add(pt1);
        employees.add(pt2);

        System.out.println("all employees");

        for (Employee emp : employees) {
            emp.displayDetails();
        }

        System.out.println("\n\ntotal salary expense");
        double totalSalary = 0;
        for (Employee emp : employees) {
            totalSalary += emp.calculateSalary();
        }
        System.out.println("total " + totalSalary);
    }
}
