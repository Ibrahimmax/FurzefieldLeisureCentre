package com.flc.ui;

import com.flc.model.*;
import com.flc.report.*;
import com.flc.service.*;
import java.util.*;

/**
 * Main UI class demonstrating the Furzefield Leisure Centre Booking System
 */
public class BookingSystemUI {
    private LessonService lessonService;
    private BookingService bookingService;

    public BookingSystemUI() {
        this.lessonService = new LessonService();
        this.bookingService = new BookingService(lessonService);
    }

    /**
     * Initializes the system with sample data
     */
    public void initializeSystem() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("FURZEFIELD LEISURE CENTRE - GROUP EXERCISE BOOKING SYSTEM");
        System.out.println("=".repeat(80));

        // Register 10 members
        registerMembers();

        // Create 8+ weekends of timetable (48+ lessons)
        createTimetable();

        // Make bookings
        makeBookings();

        // Add reviews
        addReviews();

        // All data is kept in-memory as per assignment requirement.
        System.out.println("\nIn-memory mode: data is not persisted to external database.");
    }

    /**
     * Registers 10 members
     */
    private void registerMembers() {
        System.out.println("\n>>> Registering Members...");
        String[] names = {
                "Alice Johnson", "Bob Smith", "Carol Williams", "David Brown",
                "Emma Davis", "Frank Miller", "Grace Wilson", "Henry Moore",
                "Iris Taylor", "Jack Anderson"
        };
        String[] emails = {
                "alice@mail.com", "bob@mail.com", "carol@mail.com", "david@mail.com",
                "emma@mail.com", "frank@mail.com", "grace@mail.com", "henry@mail.com",
                "iris@mail.com", "jack@mail.com"
        };

        for (int i = 0; i < 10; i++) {
            bookingService.registerMember(i + 1, names[i], emails[i]);
            System.out.printf("  ✓ Member %d: %s (%s)%n", i + 1, names[i], emails[i]);
        }
    }

    /**
     * Creates 8 weekends of timetable with 6 lessons per weekend (3 days × 2 time slots)
     * Actually creates all 3 time slots × 2 days = 6 lessons per weekend for 8 weekends = 48 lessons minimum
     */
    private void createTimetable() {
        System.out.println("\n>>> Creating Timetable (8 weekends)...");
        ExerciseType[] exercises = {
                ExerciseType.YOGA, ExerciseType.ZUMBA, ExerciseType.AQUACISE,
                ExerciseType.BOX_FIT, ExerciseType.BODY_BLITZ, ExerciseType.PILATES
        };

        int lessonCount = 0;
        for (int weekend = 1; weekend <= 8; weekend++) {
            for (Day day : Day.values()) {
                for (TimeSlot timeSlot : TimeSlot.values()) {
                    ExerciseType exercise = exercises[(lessonCount) % exercises.length];
                    lessonService.createLesson(exercise, day, timeSlot, weekend);
                    lessonCount++;
                }
            }
        }

        System.out.printf("  ✓ Created %d lessons (8 weekends × 2 days × 3 time slots)%n", lessonCount);
    }

    /**
     * Makes various bookings demonstrating the system functionality
     */
    private void makeBookings() {
        System.out.println("\n>>> Making Bookings...");

        List<Member> members = bookingService.getAllMembers();
        List<Lesson> lessons = lessonService.getAllLessons();

        int bookingCount = 0;

        // Simple booking strategy: book members to various lessons
        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);

            // Each member books 2-3 lessons on different weekends
            for (int j = 0; j < 3; j++) {
                int lessonIndex = (i * 3 + j) % lessons.size();
                Lesson lesson = lessons.get(lessonIndex);

                try {
                    if (lesson.hasAvailableSpace()) {
                        bookingService.bookLesson(member, lesson);
                        bookingCount++;
                    }
                } catch (Exception e) {
                    // Skip if booking fails
                }
            }
        }

        System.out.printf("  ✓ Created %d bookings%n", bookingCount);
    }

    /**
     * Adds 20+ reviews with ratings
     */
    private void addReviews() {
        System.out.println("\n>>> Adding Reviews and Ratings...");

        String[] comments = {
                "Excellent class! Very energetic instructor.",
                "Great workout, felt very satisfied.",
                "Good exercise, enjoyed it.",
                "Okay class, nothing special.",
                "Not satisfied with this session.",
                "Amazing instructor, highly recommend!",
                "Good pace, well organized.",
                "Perfect for my fitness level.",
                "Instructor was very helpful and supportive.",
                "Fun and engaging class!",
                "Could be better, but acceptable.",
                "Outstanding experience!",
                "Very professional and well-managed.",
                "Enjoyed the music and atmosphere.",
                "Challenging but rewarding.",
                "Best class I've attended!",
                "Good variety of exercises.",
                "Felt great after the session.",
                "Helpful and encouraging coach.",
                "Excellent value for money!"
        };

        List<Booking> bookings = bookingService.getActiveBookings();
        int reviewCount = 0;

        for (int i = 0; i < Math.min(20, bookings.size()); i++) {
            Booking booking = bookings.get(i);
            Member member = booking.getMember();
            Lesson lesson = booking.getLesson();

            int rating = (i % 5) + 1; // Ratings 1-5
            String comment = comments[i % comments.length];

            try {
                bookingService.addReview(member, lesson, rating, comment);
                reviewCount++;
            } catch (Exception e) {
                // Skip if review fails
            }
        }

        System.out.printf("  ✓ Added %d reviews with ratings%n", reviewCount);
    }

    /**
     * Displays the complete timetable
     */
    public void displayTimetable() {
        System.out.println("\n>>> Complete Timetable");
        lessonService.printTimetable();
    }

    /**
     * Displays all member information
     */
    public void displayMembers() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("REGISTERED MEMBERS");
        System.out.println("=".repeat(80));

        List<Member> members = bookingService.getAllMembers();
        for (Member member : members) {
            System.out.printf("ID: %2d | Name: %-20s | Email: %-25s | Bookings: %d%n",
                    member.getMemberId(),
                    member.getName(),
                    member.getEmail(),
                    member.getBookings().size());
        }
    }

    /**
     * Displays all bookings
     */
    public void displayBookings() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ALL BOOKINGS");
        System.out.println("=".repeat(80));

        List<Booking> bookings = bookingService.getActiveBookings();
        System.out.printf("Total Active Bookings: %d%n%n", bookings.size());

        for (Booking booking : bookings) {
            System.out.printf("Booking #%d: %s booked for %s (%s, %s at %s, Weekend %d) - $%.2f%n",
                    booking.getBookingId(),
                    booking.getMember().getName(),
                    booking.getLesson().getExerciseType().getDisplayName(),
                    booking.getLesson().getDay().getDisplayName(),
                    booking.getLesson().getTimeSlot().getTimeRange(),
                    booking.getLesson().getTimeSlot().name(),
                    booking.getLesson().getWeekendNumber(),
                    booking.getPrice());
        }
    }

    /**
     * Generates reports for a specific weekend
     */
    public void generateReports(int weekendNumber) {
        AttendanceReport attendanceReport = new AttendanceReport(lessonService, weekendNumber);
        IncomeReport incomeReport = new IncomeReport(lessonService, weekendNumber);

        attendanceReport.generateReport();
        incomeReport.generateReport();
    }

    public void generateFourWeekSummary() {
        SummaryReport summaryReport = new SummaryReport(lessonService, 1, 4);
        summaryReport.generateSummary();
    }

    private void printMenu() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("FLC BOOKING SYSTEM - MENU");
        System.out.println("=".repeat(80));
        System.out.println("1. View full timetable");
        System.out.println("2. View timetable by day");
        System.out.println("3. View lessons by exercise");
        System.out.println("4. View members");
        System.out.println("5. View bookings");
        System.out.println("6. Book a lesson");
        System.out.println("7. Modify an existing booking");
        System.out.println("8. Cancel a booking");
        System.out.println("9. Add a review for a lesson");
        System.out.println("10. Generate weekend reports");
        System.out.println("11. Generate 4-week summary report");
        System.out.println("0. Exit");
        System.out.print("Enter option: ");
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            String input = scanner.nextLine().trim();
            int option;
            try {
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (option) {
                case 1:
                    displayTimetable();
                    break;
                case 2:
                    System.out.print("Enter day (Saturday or Sunday): ");
                    String dayInput = scanner.nextLine().trim();
                    List<Lesson> dayLessons = lessonService.getLessonsByDayName(dayInput);
                    if (dayLessons.isEmpty()) {
                        System.out.println("No lessons found for that day.");
                    } else {
                        displayLessons(dayLessons);
                    }
                    break;
                case 3:
                    System.out.print("Enter exercise name: ");
                    String exerciseInput = scanner.nextLine().trim();
                    List<Lesson> exerciseLessons = lessonService.getLessonsByExerciseName(exerciseInput);
                    if (exerciseLessons.isEmpty()) {
                        System.out.println("No lessons found for that exercise.");
                    } else {
                        displayLessons(exerciseLessons);
                    }
                    break;
                case 4:
                    displayMembers();
                    break;
                case 5:
                    displayBookings();
                    break;
                case 6:
                    bookLessonInteractive(scanner);
                    break;
                case 7:
                    modifyBookingInteractive(scanner);
                    break;
                case 8:
                    cancelBookingInteractive(scanner);
                    break;
                case 9:
                    addReviewInteractive(scanner);
                    break;
                case 10:
                    System.out.print("Enter weekend number to report: ");
                    String weekendInput = scanner.nextLine().trim();
                    try {
                        int weekendNum = Integer.parseInt(weekendInput);
                        generateReports(weekendNum);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid weekend number.");
                    }
                    break;
                case 11:
                    generateFourWeekSummary();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Unknown option.");
                    break;
            }
        }

        System.out.println("Exiting FLC Booking System. Goodbye!");
        scanner.close();
    }

    private void displayLessons(List<Lesson> lessons) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("LESSONS");
        System.out.println("=".repeat(80));
        for (Lesson lesson : lessons) {
            System.out.printf("Lesson ID: %d | %s | %s | %s | Weekend %d | %d/%d seats booked | price=%.2f\n",
                    lesson.getLessonId(),
                    lesson.getExerciseType().getDisplayName(),
                    lesson.getDay().getDisplayName(),
                    lesson.getTimeSlot().getTimeRange(),
                    lesson.getWeekendNumber(),
                    lesson.getCurrentBookings(),
                    lesson.getMaxCapacity(),
                    lesson.getExerciseType().getPrice());
        }
    }

    private void bookLessonInteractive(Scanner scanner) {
        displayMembers();
        System.out.print("Enter member ID: ");
        int memberId = readInt(scanner);
        Member member = bookingService.getMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        displayTimedLessons();
        System.out.print("Enter lesson ID to book: ");
        int lessonId = readInt(scanner);
        Lesson lesson = lessonService.getLessonById(lessonId);
        if (lesson == null) {
            System.out.println("Lesson not found.");
            return;
        }

        try {
            bookingService.bookLesson(member, lesson);
            System.out.println("Booking successful.");
        } catch (IllegalStateException e) {
            System.out.println("Cannot book lesson: " + e.getMessage());
        }
    }

    private void modifyBookingInteractive(Scanner scanner) {
        displayBookings();
        System.out.print("Enter booking ID to modify: ");
        int bookingId = readInt(scanner);
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null) {
            System.out.println("Booking not found.");
            return;
        }

        displayTimedLessons();
        System.out.print("Enter new lesson ID: ");
        int newLessonId = readInt(scanner);
        Lesson newLesson = lessonService.getLessonById(newLessonId);
        if (newLesson == null) {
            System.out.println("Lesson not found.");
            return;
        }

        try {
            bookingService.modifyBooking(booking, newLesson);
            System.out.println("Booking modified successfully.");
        } catch (IllegalStateException e) {
            System.out.println("Cannot modify booking: " + e.getMessage());
        }
    }

    private void cancelBookingInteractive(Scanner scanner) {
        displayBookings();
        System.out.print("Enter booking ID to cancel: ");
        int bookingId = readInt(scanner);
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null) {
            System.out.println("Booking not found.");
            return;
        }

        bookingService.cancelBooking(booking);
        System.out.println("Booking cancelled successfully.");
    }

    private void addReviewInteractive(Scanner scanner) {
        displayBookings();
        System.out.print("Enter booking ID for review: ");
        int bookingId = readInt(scanner);
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null) {
            System.out.println("Booking not found.");
            return;
        }

        Member member = booking.getMember();
        Lesson lesson = booking.getLesson();
        System.out.print("Enter rating (1-5): ");
        int rating = readInt(scanner);
        if (rating < 1 || rating > 5) {
            System.out.println("Rating must be 1-5.");
            return;
        }

        System.out.print("Enter comment: ");
        String comment = scanner.nextLine().trim();

        bookingService.addReview(member, lesson, rating, comment);
        System.out.println("Review added successfully.");
    }

    private int readInt(Scanner scanner) {
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void displayTimedLessons() {
        displayLessons(lessonService.getAllLessons());
    }

    /**
     * Main method to run the CLI system
     */
    public static void main(String[] args) {
        BookingSystemUI system = new BookingSystemUI();

        // Initialize system with sample data (members, timetable, bookings, reviews)
        system.initializeSystem();

        // Start interactive console UI
        system.run();
    }
}
