import com.flc.system.*;
import com.flc.model.*;
import java.util.ArrayList;
public class ReportGenerator {
    private BookingSystem bookingSystem;


    public void generateMothlyLessonsReport(int month)
    {
        System.out.println("\n--------------------------------------------------");
        
        System.out.println("Monthly Lessons Report for Month: " + month);
        System.out.print("===============================================================");

        System.out.printf("%-6s %-10s %-10s %-12s %-9s %-10s%n","ID","Day","Type","Attended","Avg rating");
        System.out.println("------------------------------------------------------------------------------");

        ArrayList<Lessons>monthLessons=bookingSystem.getTimetable().getLessonsByMonth(month);

        if(monthLessons.isEmpty())
        {
            System.out.println("No Lessons found for this Month");
            return;
        }

        for(Lessons ln: monthLessons)
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


        System.out.printf("%-14s %-10s%n","Exercise Type","Income");
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



    public int calculatorAttendedCount(Lessons lesson)
    {
        int count=0;
        for(Booking b: bookingSystem.getBookings())
        {
            if(b.getLessons().getLessonId().equals(lesson.getLessonId()) && b.getStatus()==BookingStatus.Attended)
            {
                count++;
            }
        }
        return count;
    }


    public double calculateAverageRating(Lessons lesson)
    {
        int totalRating=0;
        int ratingCount=0;

        for(Booking b: bookingSystem.getBookings())
        {
            if(b.getLessons().getLessonId().equals(lesson.getLessonId()) && b.getStatus()==BookingStatus.Attended)
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
            if(b.getLessons().getType().equalsIgnoreCase(type) && b.getStatus()==BookingStatus.Attended && b.getLessons().getMonth()==month)
            {
                income+=b.getLessons().getPrice();
            }
        }
        return income;

    }
    



















}
