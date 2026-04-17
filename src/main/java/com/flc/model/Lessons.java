package com.flc.model;

public class Lessons {

    private String lessonId;
    private String day;
    private String time;
    private String exercise_type;
    private double price;
    private int capacity;
    private int BookedCount;
    private int weekend;
    private int month;


    public Lessons(String lessonId, String day ,String time,String exercise_type ,double price, int weekend, int month) {
    
    this.lessonId=lessonId;
    this.time=time;
    this.capacity=capacity;
    this.exercise_type=exercise_type;
    this.price=price;
    this.weekend=weekend;
    this.month=month;
    this.BookedCount=0;
    
    
    }

    public boolean isFull() {
        return BookedCount >= capacity;
    }
    public boolean isAvailable(){
        return BookedCount < capacity;
    }

    public void incrementBooked() {
        if (!isFull()) {
            BookedCount++;
        }
    }

    public void decrementBooked() {
        if (BookedCount > 0) {
            BookedCount--;
        }
    }

    public int getAvailableSpots() {
        return capacity - BookedCount;
    }
//Functional calls for the getters
    public String getLessonId() {return lessonId;}
    public String getDay() {return day;}
    public String getTime() {return time;}
    public String getExercise_type() {return exercise_type;}
    public double getPrice() {return price;}
    public int getCapacity() {return capacity;}
    public int getBookedCount() {return BookedCount;}
    public int getWeekend() {return weekend;}
    public int getMonth() {return month;}
    public String getType() {return exercise_type;}
    public String getName() {return exercise_type + " on " + day + " at " + time;}
    public String getInstructor() {return "Instructor for " + exercise_type;}


}