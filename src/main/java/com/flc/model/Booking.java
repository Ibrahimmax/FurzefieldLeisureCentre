package com.flc.model;

public class Booking {
    private String bookingId;
    private Member Member;
    private Lessons lesson;
    private String userId;
    private BookingStatus status;
    private String review;
    private int rating ;

    public Booking (String bookingId,Member member , Lessons lesson){
        this.bookingId=bookingId;
        this.Member=member;
        this.lesson=lesson;
        this.status=BookingStatus.Booked;
        this.review="null";
        this.rating=0;
    }

    public void attend (String review, int rating){
        this.status=BookingStatus.Attended;
        this.review=review;
        this.rating=rating;
    }

    public void cancel(){
        this.status=BookingStatus.Cancelled;
    }

    public Member getMember(){
        return Member;

    }

    public void changeLessons(Lessons newLessons)
    {
        this.lesson=newLessons;
        this.status=BookingStatus.Changed;
    }

    public String getBookingId() {return bookingId;}
    public Lessons getLessons() {return lesson;}
    public BookingStatus getStatus() {return status;}
    public String getReview() {return review;}
    public int getRating(){return rating;}
}
