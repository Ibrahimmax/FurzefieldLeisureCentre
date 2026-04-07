package com.flc.model;

public class Booking {
    private String bookingId;
    private String userId;
    private String Lesson;
    private BookingStatus status;
    private String review;
    private int rating ;

    public Booking (String bookingId,Memeber member , Lesson lesson){
        this.bookingId=bookingId;
        this.userId=member;
        this.Lesson=lesson;
        this.status=BookingStatus.Booked;
        this.review="null";
        this.rating=0;
    }

    public void attend (String review, int rating){
        this.status=BookingStatus.Attended;
        this.review=review;
        this.rating=rating;
    }

    public void cacel(){
        this.status=BookingStatus.Cancelled;
    }


    public String getBookingId() {return bookingId;}
    public String getMemeber() {return member;}
    public Lesson getLesson() {return lesson;}
    public BookingStatus getStatus() {return status;}
    public String getReview() {return review;}
    public int getRating(){return rating;}
}
