package com.flc.system;
import com.flc.model.*;
import java.util.ArrayList;
public class ReportGenerator {
    private BookingSystem bookingSystem;

    public ReportGenerator(BookingSystem bookingSystem) {
    
        this.bookingSystem = bookingSystem;
    }

    public void generateMothlyLessonReport(int month)
    {
        System.out.println("\n--------------------------------------------------");
        
        system.out.println("Monthly Lesson Report for Month: " + month);
        System.out.print("===============================================================");

        System.out.printf("%-6s %-10s %-10s %-12s %-9s %-10s%n","ID","Day","Type","Attended","Avg rating");
        System.out.println("------------------------------------------------------------------------------");

        ArrayList<Lesson>monthLessons=bookingSystem.getTimetable().getLessonsByMonth(month);

        if(monthLessons.isEmpty())
        {
            System.out.println("No Lesson found for this Month");
            return;
        }

        for(Lesson ln: monthLessons)
        {
            int attended=calculatorAttendedCount(ln);
            double avgrating = calculateAverageRating(ln);
            String ratingDisplay = attended == 0 ? "N/A" : String.format("%.1f", avgrating);
            System.out.printf("%-6s %-10s %-10s %-12s %-9s %-10s%n",ln.getLessonId(), ln.getDay(), ln.getType(), attended, ratingDisplay);
        }
        System.out.println("--------------------------------------------------");

    }



    public void generateChampionReport(int month)
    {
        System.out.println("\n--------------------------------------------------");
        System.out.println("Champion Report for Month: " + month);
        System.out.println("===============================================================");
        String[] types = {"Yoga", "Zumba", "Aquacise", "Pilates","Box Fit","Body Blitz","Pilates"};

        String champion=null;
        double highestAvgRating=0.0;


        System.out.println("%-14s %-10s%n","Exercise Type","Income");
        System.out.println("--------------------------------------------------");


        for(String type: types)
        {
            double income=calculateIncomeByType(type, month);
            System.out.printf("%-14s %-10.2f%n", type, income);

            if(income>highestAvgRating)
            {
                highestAvgRating=income;
                champion=type;
            }
        }

        System.out.println("--------------------------------------------------");
        if(champion !=null && highestAvgRating>0)
        {
            System.out.println("Champion Exercise Type: " + champion + " with Income: " + String.format("%.2f", highestAvgRating));
        }
        else
        {
            System.out.println("No champion exercise type found for this month.");
        }
        System.out.println("--------------------------------------------------");

    }



    public int calculatorAttendedCount(Lessons lessons)
    {
        int count=0;
        for(Booking b: bookingSystem.getBookings())
        {
            if(b.getLesson().getLessonId().equals(lessons.getLessonId()) && b.getStatus()==BookingStatus.ATTENDED)
            {
                count++;
            }
        }
        return count;
    }


    public double calculateAverageRating(Lessons lessons)
    {
        int totalRating=0;
        int ratingCount=0;

        for(Booking b: bookingSystem.getBookings())
        {
            if(b.getLesson().getLessonId().equals(lessons.getLessonId()) && b.getStatus()==BookingStatus.ATTENDED)
            {
                totalRating+=b.getRating();
                ratingCount++;
            }
        }

        return ratingCount > 0 ? (double) totalRating / ratingCount : 0.0;
    }


    public double calculateIncomeByType(String type, int month)
    {
        double income=0.0;
        for(Booking b: bookingSystem.getBookings())
        {
            if(b.getLesson().getType().equalsIgnoreCase(type) && b.getStatus()==BookingStatus.ATTENDED && b.getLesson().getMonth()==month)
            {
                income+=b.getLesson().getPrice();
            }
        }
        return income;

    }
    



















}
