package com.flc.system;
import com.flc.model.*;

import com.flc.model.Members;
import java.util.ArrayList;

public class BookingSystem {
    private ArrayList<Booking> bookings;
    private ArrayList<Members> members;
    private Timetable timetable;
    private int bookiingCount;
    
    public BookingSystem(){
        this.bookings=new ArrayList<>();
        this.members=new ArrayList<>();
        this.timetable=new Timetable();
        this.bookiingCount=1;
        intializeMembers();
    }
    private void intializeMembers(){
        members.add(new Members("M001","Hassan"));
        members.add(new Members("M002","Ali"));
        members.add(new Members("M003","Hamza"));
        members.add(new Members("M004","Sara"));
        members.add(new Members("M005","Ayesha"));
        members.add(new Members("M006","Zainab"));
        members.add(new Members("M007","Omar"));
        members.add(new Members("M008","Fatima"));
        members.add(new Members("M009","Ahmed"));
        members.add(new Members("M010","Maryam"));
    }

    public boolean bookLesson(String memberId , String lessonId)
    {
        Member member= findMemberById(memberId);
        if(member == null){
            System.out.println("Member not found");
            return false;

        }

        Lessons Lesson = timetable.getLessonById(lessonId);
        if (Lesson == null){
            System.out.println("lesson not found");
            return false;
        }

        if(!Lesson.isAvailable()){
            System.out.println("lesson not available");
            return false;
        }

        if (isDuplicateBooking(memberId, lessonId)){
            System.out.println("you have already booking for this Lesson, kiindly make sure to cancel the previous booking before making a new one");
            return false;
        }

        // Release old spot if the member has a previous booking


        System.out.println("Booking changed successfully");


    }
    
    public Booking findBookingById(String bookingId){
        for (Booking b: bookings){
            if (b.getBookingId().equalsIgnoreCase(bookingId)){
                return b;
            }
        }
        return null;
    }


    public boolean changeBooking(String bookingId, String newLessonId){
        Booking booking = findBookingById(bookingId);
        if (booking == null){
            System.out.println("Booking not found");
            return false;
        }

        Lessons newLesson = timetable.getLessonById(newLessonId);
        if (newLesson == null){
            System.out.println("New lesson not found");
            return false;
        }

        if(!newLesson.isAvailable()){
            System.out.println("New lesson is not available");
            return false;
        }

        if (isDuplicateBooking(booking.getMember(), newLesson)){
            System.out.println("You have already booked this lesson. Please cancel the existing booking before making a new one.");
            return false;
        }

        // Release old spot
        booking.getLesson().decrementBooked();

        // Change to new lesson
        booking.changeLesson(newLesson);
        newLesson.incrementBooked();

        System.out.println("Booking changed successfully");
        return true;

    }


    public boolean cancelBooking(String bookingId){
        Booking booking = findBookingById(bookingId);
        if (booking == null){
            System.out.println("Booking not found");
            return false;
        }

        if(booking.getStatus() == BookingStatus.Cancelled){
            System.out.println("Booking is already cancelled");
            return false;
        }

        if(booking.getStatus() == BookingStatus.ATTENDED){
            System.out.println("Cannot cancel an attended booking");
            return false;
        }

        booking.getLesson().decrementBooked();
        booking.cancel();
        booking.getMember().removeBooking(booking);
        System.out.println("Booking cancelled successfully");
        return true;

    }



    public boolean attendLesson(String bookingId, String review, int rating){
        Booking booking = findBookingById(bookingId);
        if (booking == null){
            System.out.println("Booking not found");
            return false;
        }

        if (booking.getStatus() != BookingStatus.BOOKED&&
            booking.getStatus() !=BookingStatus.CHANGED)
        {
            System.out.println("Booking is already attended");
            return false;
        }

        if (rating < 1 || rating > 5){
            System.out.println("Rating must be between 1 and 5");
            return false;
        }

        booking.attend(review, rating);
        System.out.println("Lesson attended successfully");
        return true;

    }

    public Members findMemberById(String Id){
        for (Members m : members){
            if(m.getId().equalsIgnoreCase(Id)){
                return m;
            }
        }
        return null;
    }



    public boolean isDuplicateBooking(Members member, Lesson lesson){
        for (Booking b : member.getBookings()){
            if (b.getMember().getId().equals(member.getId())
            && (b.getStatus() == BookingStatus.CANCELLED)){
                return true;
            }
        }
        return false;
    }

    public String generateBookId(){
        return "B" + String.format("%03d", bookiingCount++);
    }

    public void displayMemberBookings(String memberId){
        Member member = findMemberById(memberId);
        if(member == null){
            System.out.println("Member not found");
            return;
        }


        System.out.println("Bookings for member: " + member.getName());


        if(member.getBookings().isEmpty()){
            System.out.println("No bookings found for this member");
            return;
        }

        for(Booking b: member.getBookings()){
            System.out.println("ID: %-6s | %-10s  %-10s %-12s | stsus:%s%n" + b.getBookingId(), b.getLesson().getName(), b.getLesson().getInstructor(), b.getLesson().getTime(), b.getStatus());
        }

        
    }

    public Timetable getTimetable() {
        return timetable;
    }
    public ArrayList<Members> getMembers() {
        return members;
    }
    public ArrayList<Booking> getBookings() {
        return bookings;
    }


}

