package com.flc.model;

public class Booking {
    private String bookingId;
    private Members members;
    private Lessons lessons;
    private String userId;
    private String Lessons;
    private BookingStatus status;
    private String review;
    private int rating ;

    public Booking (String bookingId,Members member , Lessons lessons){
        this.bookingId=bookingId;
        this.members=member;
        this.lessons=lessons;
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

    public Members getMember(){
        return members;

    }

    public void changeLesson()    {
        this.status=BookingStatus.Changed;
    }

    public String getBookingId() {return bookingId;}
    public Members getMember() {return members;}
    public Lessons getLesson() {return lessons;}
    public BookingStatus getStatus() {return status;}
    public String getReview() {return review;}
    public int getRating(){return rating;}
}
