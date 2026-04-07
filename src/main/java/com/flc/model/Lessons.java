package com.flc.model;

public class Lessons {

    private String lessonId;
    private string day;
    private string time;
    private string exercise_type;
    private sting price;
    private int capacity;
    private int bookedCount;
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
    this.bookedCount=0;
    
    
    }

    public boolean isFull() {
        return bookedCount >= capacity;
    }
    public void isAvailable(){
        return bookedCount < capacity;
    }

    public void increamentBookedCount() {
        if (!isFull()) {
            bookedCount++;
        }
    }

    public void decrementBookedCount() {
        if (bookedCount > 0) {
            bookedCount--;
        }
    }

    public int getAvailableSpots() {
        return capacity - bookedCount;
    }
//Functional calls for the getters
    public String getLessonId() {return lessonId;}
    public String getDay() {return day;}
    public String getTime() {return time;}
    public String getExercise_type() {return exercise_type;}
    public double getPrice() {return price;}
    public int getCapacity() {return capacity;}
    public int getBookedCount() {return bookedCount;}
    public int getWeekend() {return weekend;}
    public int getMonth() {return month;}


}