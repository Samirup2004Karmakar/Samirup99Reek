import java.util.*;

class Course {
    String code;
    String title;
    String description;
    int capacity;
    String schedule;
    List<Student> enrolledStudents = new ArrayList<>();

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
    }

    public boolean isAvailable() {
        return enrolledStudents.size() < capacity;
    }

    public int availableSlots() {
        return capacity - enrolledStudents.size();
    }

    public void enroll(Student student) {
        if (isAvailable()) {
            enrolledStudents.add(student);
        }
    }

    public void drop(Student student) {
        enrolledStudents.remove(student);
    }

    public void printCourseDetails() {
        System.out.println(code + " - " + title);
        System.out.println("Description: " + description);
        System.out.println("Schedule: " + schedule);
        System.out.println("Available slots: " + availableSlots());
        System.out.println();
    }
}

class Student {
    String id;
    String name;
    List<Course> registeredCourses = new ArrayList<>();

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void registerCourse(Course course) {
        if (course.isAvailable() && !registeredCourses.contains(course)) {
            registeredCourses.add(course);
            course.enroll(this);
            System.out.println("Registered for course: " + course.title);
        } else {
            System.out.println("Unable to register for " + course.title);
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.remove(course)) {
            course.drop(this);
            System.out.println("Dropped course: " + course.title);
        } else {
            System.out.println("You are not registered in this course.");
        }
    }

    public void printRegisteredCourses() {
        System.out.println("Registered Courses for " + name + ":");
        for (Course c : registeredCourses) {
            System.out.println("- " + c.code + " " + c.title);
        }
        System.out.println();
    }
}

public class CourseSystem {
    static Map<String, Course> courses = new HashMap<>();
    static Map<String, Student> students = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Sample data
        addSampleCourses();
        addSampleStudents();

        while (true) {
            System.out.println("\n--- Course Registration System ---");
            System.out.println("1. List Courses");
            System.out.println("2. Register for Course");
            System.out.println("3. Drop Course");
            System.out.println("4. View My Courses");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1 -> listCourses();
                case 2 -> registerCourse();
                case 3 -> dropCourse();
                case 4 -> viewMyCourses();
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void addSampleCourses() {
        courses.put("CS101", new Course("CS101", "Intro to Programming", "Basics of Java", 3, "Mon 9-11AM"));
        courses.put("MA201", new Course("MA201", "Calculus I", "Differentiation and Integration", 2, "Tue 10-12AM"));
        courses.put("PH301", new Course("PH301", "Physics", "Mechanics and Waves", 2, "Wed 1-3PM"));
    }

    private static void addSampleStudents() {
        students.put("S1001", new Student("S1001", "Alice"));
        students.put("S1002", new Student("S1002", "Bob"));
    }

    private static Student getStudent() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        Student student = students.get(id);
        if (student == null) {
            System.out.print("Enter Student Name: ");
            String name = scanner.nextLine();
            student = new Student(id, name);
            students.put(id, student);
        }
        return student;
    }

    private static void listCourses() {
        System.out.println("\nAvailable Courses:");
        for (Course c : courses.values()) {
            c.printCourseDetails();
        }
    }

    private static void registerCourse() {
        Student student = getStudent();
        listCourses();
        System.out.print("Enter Course Code to Register: ");
        String code = scanner.nextLine();
        Course course = courses.get(code);
        if (course != null) {
            student.registerCourse(course);
        } else {
            System.out.println("Invalid course code.");
        }
    }

    private static void dropCourse() {
        Student student = getStudent();
        student.printRegisteredCourses();
        System.out.print("Enter Course Code to Drop: ");
        String code = scanner.nextLine();
        Course course = courses.get(code);
        if (course != null) {
            student.dropCourse(course);
        } else {
            System.out.println("Invalid course code.");
        }
    }

    private static void viewMyCourses() {
        Student student = getStudent();
        student.printRegisteredCourses();
    }
}
