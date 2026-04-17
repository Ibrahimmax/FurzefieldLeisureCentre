package com.flc.ui;

import com.flc.model.Member;
import com.flc.system.BookingSystem;
import com.flc.model.Booking;
import com.flc.system.ReportGenerator;

import java.util.Scanner;


public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static BookingSystem bookingSystem = new BookingSystem();
    private static ReportGenerator reportGenerator = new ReportGenerator(bookingSystem);
    public static void main(String[] args) {

        System.out.println("-------------------------------------------------------");
        System.out.println("Welcome to the Furzefield Leisure Booking System!");
        System.out.println("-------------------------------------------------------");
        boolean exit = true;
        while (!exit) {
            displayMenu();
            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    handleBookLessons();
                    break;
                case 2:
                    handleChangeCancel();
                    break;
                case 3:
                    handleAttendLessons();
                    break;

                case 4:
                    handleMonthlyReport();
                    break;
                case 5:
                    handleChampionReport();
                    break;

                case 6:
                    System.out.println("Exiting the system. Goodbye!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n--- Furzefield Leisure Booking System ---");
        System.out.println("1. Book a Lessons");
        System.out.println("2. Change or Cancel Booking");
        System.out.println("3. Attend a Lessons");
        System.out.println("4. Generate Monthly Report");
        System.out.println("5. Generate Champion Report");
        System.out.println("6. Exit");
        System.out.print("Please enter your choice: ");
    }

    // Functionality for booking a lesson
    private static void handleBookLessons() {
        System.out.println("\n--- Book a Lessons ---");

        //show register Member

        System.out.println("Available Member:");

        for (Member m : bookingSystem.getMember())
        {
            System.out.println(" " +m.getId() + ": " + m.getName());
        }
        System.out.print("Enter Member ID: ");
        Member memberId = bookingSystem.getMemberById(scanner.nextLine().trim());

        System.out.println("\nView timetable by:");
        System.out.println("1. View by Day(Saturday or Sunday)");
        System.out.println("2. Exercise Type");
        System.out.print("Enter your choice: ");
        String viewChoice = scanner.nextLine().trim();


        if(viewChoice.equals("1"))
        {
            System.out.println("Enter the day (Saturday or Sunday): ");
            String day = scanner.nextLine().trim();
            bookingSystem.getTimetable().displayLessons(bookingSystem.getTimetable().getLessonsByDay(day));
        }
        else if(viewChoice.equals("2"))
        {
            System.out.println("Available Exercise Types: Yoga, Zumba, Aquacise, Box Fit, Body Blitz, Pilates");
            System.out.println("Enter the exercise type: ");
            String exerciseType = scanner.nextLine().trim();
            bookingSystem.getTimetable().displayLessons(bookingSystem.getTimetable().getLessonsByExerciseType(exerciseType));
        }
        else
        {
            System.out.println("Invalid choice. Returning to main menu.");
            return;
        }

        System.out.println("Enter Lessons ID to Book: ");
        Member memberId = bookingSystem.getMemberById(memberId.getId());
        String lessonId = scanner.nextLine().trim();
        bookingSystem.bookLessons(memberId, lessonId);


    }

    // Functionality for changing or canceling a booking
    private static void handleChangeCancel() {
        System.out.println("\n--- Change or Cancel Booking ---");
        System.out.println("Enter Member ID: ");
        Member member = bookingSystem.getMemberById(scanner.nextLine().trim());

        bookingSystem.displayMemberBookings(member);

        System.out.println("Enter Booking ID: ");
        String bookingId = scanner.nextLine().trim();
        System.out.println("Enter Lessons ID to Change/Cancel: ");
        String lessonId = scanner.nextLine().trim();


        

         Booking booking = bookingSystem.findBookingById(bookingId);

        System.out.println("1. Change Booking");
        System.out.println("2. Cancel Booking");
        System.out.println("Enter your choice: ");
        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            System.out.println("Enter new Lessons ID: ");
            System.out.println("1: Day:");
            System.out.println("2: Exercise Type:");

            System.out.println("Enter your choice: ");
            String viewChoice = scanner.nextLine().trim();

            if(viewChoice.equals("1"))
            {
                System.out.println("Enter the day (Saturday or Sunday): ");
                String day = scanner.nextLine().trim();
                bookingSystem.getTimetable().displayLessons(bookingSystem.getTimetable().getLessonsByDay(day));
            }
            else if(viewChoice.equals("2"))
            {
                System.out.println("Available Exercise Types: Yoga, Zumba, Aquacise, Box Fit, Body Blitz, Pilates");
                System.out.println("Enter the exercise type: ");
                String exerciseType = scanner.nextLine().trim();
                bookingSystem.getTimetable().displayLessons(bookingSystem.getTimetable().getLessonsByExerciseType(exerciseType));
            }
            else
            {
                System.out.println("Invalid choice. Returning to main menu.");
                return;
            }

            System.out.println("Enter new Lessons ID: ");
            String newLessonsId = scanner.nextLine().trim();
            bookingSystem.changeBooking(bookingId, newLessonsId);
         
        }
        else if (choice.equals("2")) {
            
            System.out.println("Are you sure you want to cancel this booking? (yes/no): ");
            String confirmation = scanner.nextLine().trim();
            if (confirmation.equalsIgnoreCase("yes")) {
                bookingSystem.cancelBooking(bookingId);
                System.out.println("Booking cancelled successfully.");
            } else {
                System.out.println("Cancellation aborted.");
            }
        } else {
            System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    public void gedisplayMemberBookings(Member memberId)
    {
        System.out.println("\n--- Bookings for Member: " + memberId.getName() + " ---");
        bookingSystem.displayMemberBookings(memberId);

            for (Booking booking : memberId.getBookings()) {
                System.out.println("Booking ID: " + booking.getBookingId() +
                        ", Lesson ID: " + booking.getLessons().getLessonId() +
                        ", Status: " + booking.getStatus());
            }

    }


    // Functionality for attending a lesson
    private static void handleAttendLessons() {

        System.out.println("\n--- Attend a Lessons ---");
        System.out.print("Enter Member ID to view bookings: ");
        Member memberId = bookingSystem.getMemberById(scanner.nextLine().trim());
        bookingSystem.displayMemberBookings(memberId);

        System.out.print("Enter booking ID: ");
        String bookingId = scanner.nextLine().trim();

        System.out.println("Write your review");
        String review = scanner.nextLine().trim();

        int rating = 0;
        while (rating < 1 || rating > 5) {
            System.out.println("Rate the lesson (1-5): ");
            try {
                rating = Integer.parseInt(scanner.nextLine().trim());
                if (rating < 1 || rating > 5) {
                    System.out.println("Invalid rating. Please enter a number between 1 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
            }
        }
        bookingSystem.attendLessons(bookingId, review, rating);

    }

    // Functionality for generating monthly report
    private static void handleMonthlyReport() {
        System.out.println("\n--- Champion Exercise Report ---");
        System.out.println("Enter a month number (3 for March, 4 for April, etc.): ");

        try {
            int month = Integer.parseInt(scanner.nextLine().trim());
           if (month!=3 && month!=4 ) {
                System.out.println("Invalid month. Please enter a number between 3 and 4.");
                return;
            }
            reportGenerator.generateMonthlyLessonsReport(month);
            
        }
         catch (NumberFormatException e)
        {
            System.out.println("Invalid input. Please enter a number between 3 and 4.");
        }
    }

    private static int getUserChoice(){
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1; // Invalid choice
        }
    }

    private static void handleChampionReport(){
        System.out.println("\n--- Champion Exercise Report ---");
    }
    
    
}
