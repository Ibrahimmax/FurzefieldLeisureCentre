package com.flc;

import com.flc.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the Booking class
 */
public class BookingTest {
    private Booking booking;
    private Member member;
    private Lesson lesson;

    @Before
    public void setUp() {
        member = new Member(1, "Test Member", "test@example.com");
        lesson = new Lesson(ExerciseType.YOGA, Day.SATURDAY, TimeSlot.MORNING, 1);
        booking = new Booking(member, lesson);
    }

    @Test
    public void testBookingCreation() {
        assertNotNull(booking.getBookingId());
        assertEquals(member, booking.getMember());
        assertEquals(lesson, booking.getLesson());
        assertFalse(booking.isCancelled());
        assertNotNull(booking.getBookingDate());
    }

    @Test
    public void testBookingPrice() {
        double expectedPrice = ExerciseType.YOGA.getPrice();
        assertEquals(expectedPrice, booking.getPrice(), 0.01);
    }

    @Test
    public void testBookingCancellation() {
        assertFalse(booking.isCancelled());
        booking.cancel();
        assertTrue(booking.isCancelled());
    }

    @Test
    public void testChangeLessonToAvailable() {
        Lesson newLesson = new Lesson(ExerciseType.ZUMBA, Day.SUNDAY, TimeSlot.AFTERNOON, 1);
        
        booking.setLesson(newLesson);
        assertEquals(newLesson, booking.getLesson());
    }

    @Test(expected = IllegalStateException.class)
    public void testChangeToFullLesson() {
        Lesson fullLesson = new Lesson(ExerciseType.ZUMBA, Day.SUNDAY, TimeSlot.AFTERNOON, 1);
        
        // Fill the lesson
        Member m1 = new Member(2, "M1", "m1@test.com");
        Member m2 = new Member(3, "M2", "m2@test.com");
        Member m3 = new Member(4, "M3", "m3@test.com");
        Member m4 = new Member(5, "M4", "m4@test.com");
        
        fullLesson.addBooking(new Booking(m1, fullLesson));
        fullLesson.addBooking(new Booking(m2, fullLesson));
        fullLesson.addBooking(new Booking(m3, fullLesson));
        fullLesson.addBooking(new Booking(m4, fullLesson));
        
        booking.setLesson(fullLesson); // Should throw exception
    }

    @Test
    public void testBookingEquality() {
        Booking booking2 = new Booking(member, lesson);
        // Same IDs would be needed for equality - since IDs are auto-generated, they'll be different
        assertNotEquals(booking, booking2);
    }
}
