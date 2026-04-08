package com.flc.model;
import java.util.ArrayList;

public class Members {
    private String id;
    private String name;
    private String email;
    private ArrayList<Booking> bookings;

    public Members(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.bookings = new ArrayList<>();
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public void removeBooking(Booking booking) {
        this.bookings.remove(booking);
    }
    
    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }
    
}
