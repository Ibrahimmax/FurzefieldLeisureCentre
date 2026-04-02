package com.flc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;

/**
 * JPA Entity representing a review and rating given by a member for a lesson they attended
 */
@Entity
@Table(name = "reviews", indexes = {
    @Index(name = "idx_lesson_id", columnList = "lesson_id"),
    @Index(name = "idx_member_id", columnList = "member_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(nullable = false)
    @Min(1)
    @Max(5)
    private int rating; // 1-5 scale

    @Column(length = 500)
    private String comment;

    @Column(nullable = false)
    private LocalDateTime reviewDate;

    /**
     * Creates a review with a rating (1-5 scale)
     */
    public Review(Member member, Lesson lesson, int rating, String comment) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        this.member = member;
        this.lesson = lesson;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = LocalDateTime.now();
    }
}
}
