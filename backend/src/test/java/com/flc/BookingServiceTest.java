package com.flc;

import com.flc.model.*;
import com.flc.service.BookingService;
import com.flc.service.LessonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the BookingService class
 */
public class BookingServiceTest {
    private BookingService bookingService;
    private LessonService lessonService;
    private Member testMember;
    private Lesson testLesson;

    @BeforeEach
    public void setUp() {
        lessonService = new LessonService();
        bookingService = new BookingService(lessonService);
        testMember = bookingService.registerMember(1, "Test Member", "test@example.com");
        testLesson = lessonService.createLesson(ExerciseType.YOGA, Day.SATURDAY, TimeSlot.MORNING, 1);
    }

    @Test
    public void testRegisterMember() {
        Member member = bookingService.registerMember(2, "New Member", "new@example.com");
        
        assertNotNull(member);
        assertEquals(2, member.getMemberId());
        assertEquals("New Member", member.getName());
        assertEquals("new@example.com", member.getEmail());
    }

    @Test
    public void testGetMemberById() {
        Member retrieved = bookingService.getMemberById(1);
        
        assertEquals(testMember, retrieved);
    }

    @Test
    public void testGetAllMembers() {
        assertEquals(1, bookingService.getAllMembers().size());
        
        bookingService.registerMember(2, "Member 2", "member2@example.com");
        bookingService.registerMember(3, "Member 3", "member3@example.com");
        
        assertEquals(3, bookingService.getAllMembers().size());
    }

    @Test
    public void testBookLesson() {
        Booking booking = bookingService.bookLesson(testMember, testLesson);
        
        assertNotNull(booking);
        assertEquals(testMember, booking.getMember());
        assertEquals(testLesson, booking.getLesson());
        assertFalse(booking.isCancelled());
    }

    @Test
    public void testBookingUpdatesLesson() {
        assertEquals(0, testLesson.getCurrentBookings());
        
        bookingService.bookLesson(testMember, testLesson);
        
        assertEquals(1, testLesson.getCurrentBookings());
    }

    @Test
    public void testBookingFullLesson() {
        assertThrows(IllegalStateException.class, () -> {
        Member m1 = bookingService.registerMember(2, "M1", "m1@test.com");
        Member m2 = bookingService.registerMember(3, "M2", "m2@test.com");
        Member m3 = bookingService.registerMember(4, "M3", "m3@test.com");
        Member m4 = bookingService.registerMember(5, "M4", "m4@test.com");
        Member m5 = bookingService.registerMember(6, "M5", "m5@test.com");
        
            bookingService.bookLesson(testMember, testLesson);
            bookingService.bookLesson(m1, testLesson);
            bookingService.bookLesson(m2, testLesson);
            bookingService.bookLesson(m3, testLesson);
            bookingService.bookLesson(m4, testLesson); // This should throw exception
        });
    }

    @Test
    public void testModifyBooking() {
        Booking booking = bookingService.bookLesson(testMember, testLesson);
        
        Lesson newLesson = lessonService.createLesson(ExerciseType.ZUMBA, Day.SUNDAY, TimeSlot.AFTERNOON, 1);
        bookingService.modifyBooking(booking, newLesson);
        
        assertEquals(newLesson, booking.getLesson());
    }

    @Test(expected = IllegalStateException.class)
    public void testConflictingBooking() {
        Lesson lesson1 = lessonService.createLesson(ExerciseType.YOGA, Day.SATURDAY, TimeSlot.MORNING, 1);
        Lesson lesson2 = lessonService.createLesson(ExerciseType.ZUMBA, Day.SATURDAY, TimeSlot.MORNING, 1);
        
        bookingService.bookLesson(testMember, lesson1);
        bookingService.bookLesson(testMember, lesson2); // Same time slot, should fail
    }

    @Test
    public void testCancelBooking() {
        Booking booking = bookingService.bookLesson(testMember, testLesson);
        
        assertFalse(booking.isCancelled());
        assertEquals(1, testLesson.getCurrentBookings());
        
        bookingService.cancelBooking(booking);
        
        assertTrue(booking.isCancelled());
        assertEquals(0, testLesson.getCurrentBookings());
    }

    @Test
    public void testGetActiveBookings() {
        Booking booking1 = bookingService.bookLesson(testMember, testLesson);
        Member member2 = bookingService.registerMember(2, "M2", "m2@test.com");
        Lesson lesson2 = lessonService.createLesson(ExerciseType.ZUMBA, Day.SUNDAY, TimeSlot.AFTERNOON, 1);
        Booking booking2 = bookingService.bookLesson(member2, lesson2);
        
        assertEquals(2, bookingService.getActiveBookings().size());
        
        bookingService.cancelBooking(booking1);
        
        assertEquals(1, bookingService.getActiveBookings().size());
    }

    @Test
    public void testAddReview() {
        Booking booking = bookingService.bookLesson(testMember, testLesson);
        
        Review review = bookingService.addReview(testMember, testLesson, 5, "Excellent!");
        
        assertNotNull(review);
        assertEquals(5, review.getRating());
        assertEquals("Excellent!", review.getComment());
        assertEquals(1, testLesson.getReviews().size());
    }

    @Test
    public void testTotalBookings() {
        assertEquals(0, bookingService.getTotalBookings());
        
        bookingService.bookLesson(testMember, testLesson);
        
        assertEquals(1, bookingService.getTotalBookings());
    }

    @Test
    public void testTotalMembers() {
        assertEquals(1, bookingService.getTotalMembers());
        
        bookingService.registerMember(2, "M2", "m2@test.com");
        
        assertEquals(2, bookingService.getTotalMembers());
    }
}
