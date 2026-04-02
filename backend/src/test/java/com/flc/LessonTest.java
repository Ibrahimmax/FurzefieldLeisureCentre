package com.flc;

import com.flc.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the Lesson class
 */
public class LessonTest {
    private Lesson lesson;

    @Before
    public void setUp() {
        lesson = new Lesson(ExerciseType.YOGA, Day.SATURDAY, TimeSlot.MORNING, 1);
    }

    @Test
    public void testLessonCreation() {
        assertEquals(ExerciseType.YOGA, lesson.getExerciseType());
        assertEquals(Day.SATURDAY, lesson.getDay());
        assertEquals(TimeSlot.MORNING, lesson.getTimeSlot());
        assertEquals(1, lesson.getWeekendNumber());
        assertEquals(4, lesson.getMaxCapacity());
    }

    @Test
    public void testLessonCapacity() {
        assertEquals(4, lesson.getAvailableSpaces());
        assertEquals(0, lesson.getCurrentBookings());
        assertTrue(lesson.hasAvailableSpace());

        Member member = new Member(1, "Test", "test@example.com");
        Booking booking = new Booking(member, lesson);
        lesson.addBooking(booking);

        assertEquals(3, lesson.getAvailableSpaces());
        assertEquals(1, lesson.getCurrentBookings());
    }

    @Test
    public void testLessonFullCapacity() {
        Member member1 = new Member(1, "Test1", "test1@example.com");
        Member member2 = new Member(2, "Test2", "test2@example.com");
        Member member3 = new Member(3, "Test3", "test3@example.com");
        Member member4 = new Member(4, "Test4", "test4@example.com");

        lesson.addBooking(new Booking(member1, lesson));
        lesson.addBooking(new Booking(member2, lesson));
        lesson.addBooking(new Booking(member3, lesson));
        lesson.addBooking(new Booking(member4, lesson));

        assertFalse(lesson.hasAvailableSpace());
        assertEquals(0, lesson.getAvailableSpaces());
        assertEquals(4, lesson.getCurrentBookings());
    }

    @Test(expected = IllegalStateException.class)
    public void testLessonOverCapacity() {
        Member member1 = new Member(1, "Test1", "test1@example.com");
        Member member2 = new Member(2, "Test2", "test2@example.com");
        Member member3 = new Member(3, "Test3", "test3@example.com");
        Member member4 = new Member(4, "Test4", "test4@example.com");
        Member member5 = new Member(5, "Test5", "test5@example.com");

        lesson.addBooking(new Booking(member1, lesson));
        lesson.addBooking(new Booking(member2, lesson));
        lesson.addBooking(new Booking(member3, lesson));
        lesson.addBooking(new Booking(member4, lesson));
        lesson.addBooking(new Booking(member5, lesson)); // Should throw exception
    }

    @Test
    public void testLessonReviews() {
        Member member = new Member(1, "Test", "test@example.com");
        
        assertEquals(0, lesson.getReviews().size());
        assertEquals(0, lesson.getAverageRating(), 0);

        Review review1 = new Review(member, lesson, 5, "Great!");
        Review review2 = new Review(member, lesson, 4, "Good!");
        
        lesson.addReview(review1);
        lesson.addReview(review2);

        assertEquals(2, lesson.getReviews().size());
        assertEquals(4.5, lesson.getAverageRating(), 0.01);
    }

    @Test
    public void testLessonIncome() {
        Member member1 = new Member(1, "Test1", "test1@example.com");
        Member member2 = new Member(2, "Test2", "test2@example.com");
        
        lesson.addBooking(new Booking(member1, lesson));
        lesson.addBooking(new Booking(member2, lesson));

        double expectedIncome = ExerciseType.YOGA.getPrice() * 2;
        assertEquals(expectedIncome, lesson.getIncome(), 0.01);
    }
}
