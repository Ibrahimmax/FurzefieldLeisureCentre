package com.flc.service;

import com.flc.model.*;
import com.flc.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * Service for managing members and their bookings
 */
@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {
    private final MemberRepository memberRepository;
    private final BookingRepository bookingRepository;
    private final LessonRepository lessonRepository;
    private final ReviewRepository reviewRepository;

    /**
     * Registers a new member
     */
    public Member registerMember(String name, String email) {
        Member member = new Member(name, email);
        return memberRepository.save(member);
    }

    /**
     * Gets a member by ID
     */
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + memberId));
    }

    /**
     * Gets all members
     */
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    /**
     * Gets a member by email
     */
    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with email: " + email));
    }

    /**
     * Books a lesson for a member
     */
    public Booking bookLesson(Long memberId, Long lessonId) {
        Member member = getMemberById(memberId);
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found with ID: " + lessonId));

        // Check if member already has a booking at the same time
        if (hasConflictingBooking(member, lesson)) {
            throw new IllegalStateException("Member has a conflicting booking at this time");
        }

        if (!lesson.hasAvailableSpace()) {
            throw new IllegalStateException("No available spaces for this lesson");
        }

        Booking booking = new Booking(member, lesson);
        booking = bookingRepository.save(booking);
        lesson.addBooking(booking);
        lessonRepository.save(lesson);

        return booking;
    }

    /**
     * Checks if a member has a conflicting booking (same time slot on same day)
     */
    private boolean hasConflictingBooking(Member member, Lesson newLesson) {
        List<Booking> memberBookings = bookingRepository.findActiveBookingsByMember(member.getMemberId());
        for (Booking booking : memberBookings) {
            Lesson existingLesson = booking.getLesson();
            if (existingLesson.getDay() == newLesson.getDay() &&
                existingLesson.getTimeSlot() == newLesson.getTimeSlot() &&
                existingLesson.getWeekendNumber() == newLesson.getWeekendNumber()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Cancels a booking
     */
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with ID: " + bookingId));
        
        booking.cancel();
        bookingRepository.save(booking);
        
        Lesson lesson = booking.getLesson();
        lesson.removeBooking(booking);
        lessonRepository.save(lesson);
    }

    /**
     * Gets a booking by ID
     */
    public Booking getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with ID: " + bookingId));
    }

    /**
     * Gets all bookings
     */
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    /**
     * Gets active (non-cancelled) bookings for a member
     */
    public List<Booking> getActiveMemberBookings(Long memberId) {
        return bookingRepository.findActiveBookingsByMember(memberId);
    }

    /**
     * Gets bookings for a specific lesson
     */
    public List<Booking> getLessonBookings(Long lessonId) {
        return bookingRepository.findActiveBookingsByLesson(lessonId);
    }

    /**
     * Adds a review for a lesson
     */
    public Review addReview(Long memberId, Long lessonId, int rating, String comment) {
        Member member = getMemberById(memberId);
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found with ID: " + lessonId));

        Review review = new Review(member, lesson, rating, comment);
        review = reviewRepository.save(review);
        lesson.addReview(review);
        lessonRepository.save(lesson);
        
        return review;
    }

    /**
     * Gets reviews for a lesson
     */
    public List<Review> getLessonReviews(Long lessonId) {
        return reviewRepository.findReviewsByLesson(lessonId);
    }

    /**
     * Gets average rating for a lesson
     */
    public Double getLessonAverageRating(Long lessonId) {
        return reviewRepository.getAverageRatingForLesson(lessonId);
    }

    public long getTotalBookings() {
        return bookingRepository.count();
    }

    public long getTotalMembers() {
        return memberRepository.count();
    }
}
