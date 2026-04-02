package com.flc.controller;

import com.flc.model.Review;
import com.flc.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST Controller for Review management
 */
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReviewController {
    private final BookingService bookingService;

    /**
     * Add a review for a lesson
     */
    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody ReviewDTO dto) {
        try {
            Review review = bookingService.addReview(
                dto.getMemberId(),
                dto.getLessonId(),
                dto.getRating(),
                dto.getComment()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(review);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get all reviews for a lesson
     */
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<Review>> getLessonReviews(@PathVariable Long lessonId) {
        try {
            return ResponseEntity.ok(bookingService.getLessonReviews(lessonId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get average rating for a lesson
     */
    @GetMapping("/lesson/{lessonId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long lessonId) {
        try {
            Double averageRating = bookingService.getLessonAverageRating(lessonId);
            return ResponseEntity.ok(averageRating != null ? averageRating : 0.0);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DTO for review creation
     */
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class ReviewDTO {
        private Long memberId;
        private Long lessonId;
        private int rating; // 1-5
        private String comment;
    }
}
