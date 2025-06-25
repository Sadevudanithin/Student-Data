import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.Scanner;

// Student class with encapsulation
class Student {
    private int id;
    private String name;
    private double marks;

    // Constructors
    public Student(int id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    public Student() { /* Default constructor */ }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getMarks() { return marks; }
    public void setMarks(double marks) { this.marks = marks; }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Marks: %.2f", id, name, marks);
    }
}

public class Data {
    private static ArrayList<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            menu();
            int choice = getInt("Enter your choice: ");
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> updateStudent();
                case 4 -> deleteStudent();
                case 5 -> {
                    System.out.println("Exiting. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void menu() {
        System.out.println("""
            === Student Record Management ===
            1. Add Student
            2. View All Students
            3. Update Student by ID
            4. Delete Student by ID
            5. Exit
            """);
    }

    private static void addStudent() {
        int id = getInt("Enter student ID: ");
        String name = getString("Enter student name: ");
        double marks = getDouble("Enter student marks: ");
        students.add(new Student(id, name, marks));
        System.out.println("Student added successfully!");
    }

    private static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
        } else {
            System.out.println("---- Student List ----");
            students.sort(Comparator.comparingInt(Student::getId));
            students.forEach(System.out::println);
        }
    }

    private static void updateStudent() {
        int id = getInt("Enter the ID of the student to update: ");
        Optional<Student> opt = students.stream()
                .filter(s -> s.getId() == id)
                .findFirst();
        if (opt.isPresent()) {
            Student s = opt.get();
            System.out.println("Current details: " + s);
            s.setName(getString("Enter new name (leave blank to keep current): ").trim().isEmpty() ? s.getName() : getString("Enter new name: "));
            s.setMarks(getDouble("Enter new marks: "));
            System.out.println("Student updated!");
        } else {
            System.out.println("Student with ID " + id + " not found.");
        }
    }

    private static void deleteStudent() {
        int id = getInt("Enter the ID of the student to delete: ");
        boolean removed = students.removeIf(s -> s.getId() == id);
        System.out.println(removed ? "Student deleted!" : "Student with ID " + id + " not found.");
    }

    // Utility methods to handle input with validation
    private static int getInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer. Please try again.");
            }
        }
    }

    private static double getDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    private static String getString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
