package com.flc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.persistence.*;
import java.util.*;

/**
 * JPA Entity representing a group exercise lesson offered by the leisure centre
 */
@Entity
@Table(name = "lessons", indexes = {
    @Index(name = "idx_weekend_number", columnList = "weekend_number"),
    @Index(name = "idx_day_exercise", columnList = "day,exercise_type")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lessonId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExerciseType exerciseType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Day day;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TimeSlot timeSlot;

    @Column(nullable = false)
    private int weekendNumber;

    @Column(nullable = false)
    private int maxCapacity = 4;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Review> reviews = new ArrayList<>();

    public Lesson(ExerciseType exerciseType, Day day, TimeSlot timeSlot, int weekendNumber) {
        this.exerciseType = exerciseType;
        this.day = day;
        this.timeSlot = timeSlot;
        this.weekendNumber = weekendNumber;
        this.maxCapacity = 4;
        this.bookings = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public int getCurrentBookings() {
        return (int) bookings.stream().filter(b -> !b.isCancelled()).count();
    }

    public int getAvailableSpaces() {
        return maxCapacity - getCurrentBookings();
    }

    public boolean hasAvailableSpace() {
        return getCurrentBookings() < maxCapacity;
    }

    public void addBooking(Booking booking) {
        if (hasAvailableSpace()) {
            bookings.add(booking);
            booking.setLesson(this);
        } else {
            throw new IllegalStateException("No available spaces for this lesson");
        }
    }

    public void removeBooking(Booking booking) {
        if (bookings.remove(booking)) {
            booking.setLesson(null);
        }
    }

    public void addReview(Review review) {
        reviews.add(review);
        review.setLesson(this);
    }

    public double getAverageRating() {
        if (reviews.isEmpty()) {
            return 0;
        }
        return reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
    }

    public double getIncome() {
        return getCurrentBookings() * exerciseType.getPrice();
    }
}
