package com.flc.system;
import com.flc.model.Lessons;
import java.util.ArrayList;

public class Timetable {
    private ArrayList<Lessons> Lessons;

    public Timetable(){
        Lessons=new ArrayList<>();
        initializeLessons();
    
    }

    private void initializeLessons(){

// Weekend 1 to 4 ,Month of March   and weekend 5 to 8 , Month of April
// format of the sample reord is  (LessonsID,Dat,Time,Type,price,weeknd,month)



            // record for the week#1

        Lessons.add(new Lessons("L001","Saturday" ,"Morning","Yoga" , 10,1,3));
        Lessons.add(new Lessons("L002","Saturday" ,"Morning","Zumba" , 12,1,3));
        Lessons.add(new Lessons("L003","Saturday" ,"Morning","Box fit" , 13,1,3));
        Lessons.add(new Lessons("L004","Saturday" ,"Morning","Aquacise" , 11,1,3));
        Lessons.add(new Lessons("L005","Saturday" ,"Morning","Pilates" , 11.50,1,3));
        Lessons.add(new Lessons("L006","Saturday" ,"Morning","Body Blitz" , 14,1,3));

        //reord for the week#2
        // ===== WEEKEND 2 (March) =====
        Lessons.add(new Lessons("L007", "Saturday", "Morning",   "Zumba",      12.00, 2, 3));
        Lessons.add(new Lessons("L008", "Saturday", "Afternoon", "Yoga",       10.00, 2, 3));
        Lessons.add(new Lessons("L009", "Saturday", "Evening",   "Pilates",    11.50, 2, 3));
        Lessons.add(new Lessons("L010", "Sunday",   "Morning",   "Box Fit",    13.00, 2, 3));
        Lessons.add(new Lessons("L011", "Sunday",   "Afternoon", "Aquacise",   11.00, 2, 3));
        Lessons.add(new Lessons("L012", "Sunday",   "Evening",   "Body Blitz", 14.00, 2, 3));

        // ===== WEEKEND 3 (March) =====
        Lessons.add(new Lessons("L013", "Saturday", "Morning",   "Body Blitz", 14.00, 3, 3));
        Lessons.add(new Lessons("L014", "Saturday", "Afternoon", "Aquacise",   11.00, 3, 3));
        Lessons.add(new Lessons("L015", "Saturday", "Evening",   "Yoga",       10.00, 3, 3));
        Lessons.add(new Lessons("L016", "Sunday",   "Morning",   "Zumba",      12.00, 3, 3));
        Lessons.add(new Lessons("L017", "Sunday",   "Afternoon", "Pilates",    11.50, 3, 3));
        Lessons.add(new Lessons("L018", "Sunday",   "Evening",   "Box Fit",    13.00, 3, 3));

        // ===== WEEKEND 4 (March) =====
        Lessons.add(new Lessons("L019", "Saturday", "Morning",   "Aquacise",   11.00, 4, 3));
        Lessons.add(new Lessons("L020", "Saturday", "Afternoon", "Body Blitz", 14.00, 4, 3));
        Lessons.add(new Lessons("L021", "Saturday", "Evening",   "Zumba",      12.00, 4, 3));
        Lessons.add(new Lessons("L022", "Sunday",   "Morning",   "Pilates",    11.50, 4, 3));
        Lessons.add(new Lessons("L023", "Sunday",   "Afternoon", "Box Fit",    13.00, 4, 3));
        Lessons.add(new Lessons("L024", "Sunday",   "Evening",   "Yoga",       10.00, 4, 3));

        // ===== WEEKEND 5 (April) =====
        Lessons.add(new Lessons("L025", "Saturday", "Morning",   "Pilates",    11.50, 5, 4));
        Lessons.add(new Lessons("L026", "Saturday", "Afternoon", "Box Fit",    13.00, 5, 4));
        Lessons.add(new Lessons("L027", "Saturday", "Evening",   "Aquacise",   11.00, 5, 4));
        Lessons.add(new Lessons("L028", "Sunday",   "Morning",   "Yoga",       10.00, 5, 4));
        Lessons.add(new Lessons("L029", "Sunday",   "Afternoon", "Zumba",      12.00, 5, 4));
        Lessons.add(new Lessons("L030", "Sunday",   "Evening",   "Body Blitz", 14.00, 5, 4));

        // ===== WEEKEND 6 (April) =====
        Lessons.add(new Lessons("L031", "Saturday", "Morning",   "Box Fit",    13.00, 6, 4));
        Lessons.add(new Lessons("L032", "Saturday", "Afternoon", "Pilates",    11.50, 6, 4));
        Lessons.add(new Lessons("L033", "Saturday", "Evening",   "Body Blitz", 14.00, 6, 4));
        Lessons.add(new Lessons("L034", "Sunday",   "Morning",   "Zumba",      12.00, 6, 4));
        Lessons.add(new Lessons("L035", "Sunday",   "Afternoon", "Yoga",       10.00, 6, 4));
        Lessons.add(new Lessons("L036", "Sunday",   "Evening",   "Aquacise",   11.00, 6, 4));

        // ===== WEEKEND 7 (April) =====
        Lessons.add(new Lessons("L037", "Saturday", "Morning",   "Aquacise",   11.00, 7, 4));
        Lessons.add(new Lessons("L038", "Saturday", "Afternoon", "Body Blitz", 14.00, 7, 4));
        Lessons.add(new Lessons("L039", "Saturday", "Evening",   "Zumba",      12.00, 7, 4));
        Lessons.add(new Lessons("L040", "Sunday",   "Morning",   "Box Fit",    13.00, 7, 4));
        Lessons.add(new Lessons("L041", "Sunday",   "Afternoon", "Aquacise",   11.00, 7, 4));
        Lessons.add(new Lessons("L042", "Sunday",   "Evening",   "Yoga",       10.00, 7, 4));

        // ===== WEEKEND 8 (April) =====
        Lessons.add(new Lessons("L043", "Saturday", "Morning",   "Zumba",      12.00, 8, 4));
        Lessons.add(new Lessons("L044", "Saturday", "Afternoon", "Aquacise",   11.00, 8, 4));
        Lessons.add(new Lessons("L045", "Saturday", "Evening",   "Yoga",       10.00, 8, 4));
        Lessons.add(new Lessons("L046", "Sunday",   "Morning",   "Body Blitz", 14.00, 8, 4));
        Lessons.add(new Lessons("L047", "Sunday",   "Afternoon", "Box Fit",    13.00, 8, 4));
        Lessons.add(new Lessons("L048", "Sunday",   "Evening",   "Pilates",    11.50, 8, 4));
    


    }



    // return Lessons by overall day 
    public ArrayList<Lessons> getLessonsByDay(String day)
    {
        
        ArrayList<Lessons> overallLessons=new ArrayList<>();
        {
            for (Lessons ln: Lessons)
                if(ln.getDay().equalsIgnoreCase(day))
                    overallLessons.add(ln);
        }

        return overallLessons;
    }


    public ArrayList<Lessons> getLessonsByExerciseType(String exerciseType)
    {
        ArrayList<Lessons> overallExerciseType=new ArrayList<>();
        for(Lessons ln: Lessons){
            if(ln.getExercise_type().equalsIgnoreCase(exerciseType))
                overallExerciseType.add(ln);
        }
        return overallExerciseType;
    }

//get lesson by overall type

    public ArrayList<Lessons> getLessonsByType(String type)
    {
    ArrayList<Lessons> overalltype=new ArrayList<>();
    for(Lessons ln: Lessons){
        if(ln.getExercise_type().equalsIgnoreCase(type))
            overalltype.add(ln);
    }
        return overalltype;
    }

//get lesson by overall month
    public ArrayList<Lessons> getLessonsByMonth(int month)
    {
        ArrayList<Lessons> overallmonth=new ArrayList<>();
        for(Lessons ln: Lessons){
            if(ln.getMonth()==month)
                overallmonth.add(ln);
        }
        return overallmonth;
    }

    //get lesson by overall weekend
    public ArrayList<Lessons> getLessonsByWeekend(int weekend)
    {
        ArrayList<Lessons> overallweekend=new ArrayList<>();
        for(Lessons ln: Lessons){
            if(ln.getWeekend()==weekend)
                overallweekend.add(ln);
        }
        return overallweekend;
    }
    
    public ArrayList<Lessons> getAllLessons(){return Lessons;} 
    
    public Lessons getLessonById(String id){return Lessons.get(Integer.parseInt(id));}


    public void displayLessons(ArrayList<Lessons> list)
    {
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println(String.format("%-10s %-10s %-10s %-15s %-10s %-10s %-10s", "LessonID", "Day", "Time", "Exercise Type", "Price", "Weekend", "Month"));
        System.out.println("---------------------------------------------------------------------------------");
        for(Lessons ln: list){
            System.out.println(String.format("%-10s %-10s %-10s %-15s %-10.2f %-10d %-10d",
                    ln.getLessonId(), ln.getDay(), ln.getTime(), ln.getExercise_type(),
                    ln.getPrice(), ln.getWeekend(), ln.getMonth()));
        }
        System.out.println("---------------------------------------------------------------------------------");
    }
































    }
