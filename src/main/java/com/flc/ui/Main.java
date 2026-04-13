package com.flc.ui;
import com.flc.system.BookingSystem;
import com.flc.system.ReportGenerator;
import java.util.scanner;



public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static BookingSystem bookingSystem = new BookingSystem();
    private static ReportGenerator reportGenerator = new ReportGenerator(bookingSystem);


    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            displayMenu();
            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    bookingSystem.displayTimetable();
                    break;
                case 2:
                    bookingSystem.bookLesson(scanner);
                    break;
                case 3:
                    reportGenerator.generateBookingReport();
                    break;
                case 4:
                    System.out.println("Exiting the system. Goodbye!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    private static void displayMenu() {
        System.out.println("\n--- Fitness Lesson Booking System ---");
        System.out.println("1. View Timetable");
        System.out.println("2. Book a Lesson");
        System.out.println("3. Generate Booking Report");
        System.out.println("4. Exit");
        System.out.print("Please enter your choice: ");
    }


    private static int getUserChoice() {
        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
        return choice;
    }


    
    
}
