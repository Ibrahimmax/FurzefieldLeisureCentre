package com.flc.controller;

import com.flc.model.Booking;
import com.flc.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST Controller for Booking management
 */
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BookingController {
    private final BookingService bookingService;

    /**
     * Get all bookings
     */
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    /**
     * Get booking by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(bookingService.getBookingById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Book a lesson for a member
     */
    @PostMapping
    public ResponseEntity<Booking> bookLesson(@RequestBody BookingDTO dto) {
        try {
            Booking booking = bookingService.bookLesson(dto.getMemberId(), dto.getLessonId());
            return ResponseEntity.status(HttpStatus.CREATED).body(booking);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Cancel a booking
     */
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        try {
            bookingService.cancelBooking(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get active bookings for a member
     */
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Booking>> getActiveMemberBookings(@PathVariable Long memberId) {
        return ResponseEntity.ok(bookingService.getActiveMemberBookings(memberId));
    }

    /**
     * Get bookings for a lesson
     */
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<Booking>> getLessonBookings(@PathVariable Long lessonId) {
        return ResponseEntity.ok(bookingService.getLessonBookings(lessonId));
    }

    /**
     * DTO for booking creation
     */
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class BookingDTO {
        private Long memberId;
        private Long lessonId;
    }
}
