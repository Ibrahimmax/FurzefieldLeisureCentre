package com.flc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * JPA Entity representing a member's booking for a group exercise lesson
 */
@Entity
@Table(name = "bookings", indexes = {
    @Index(name = "idx_member_id", columnList = "member_id"),
    @Index(name = "idx_lesson_id", columnList = "lesson_id"),
    @Index(name = "idx_cancelled", columnList = "cancelled")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(nullable = false)
    private LocalDateTime bookingDate;

    @Column(nullable = false)
    private boolean cancelled = false;

    public Booking(Member member, Lesson lesson) {
        this.member = member;
        this.lesson = lesson;
        this.bookingDate = LocalDateTime.now();
        this.cancelled = false;
    }

    public void cancel() {
        this.cancelled = true;
    }

    public double getPrice() {
        return lesson.getExerciseType().getPrice();
    }
}
