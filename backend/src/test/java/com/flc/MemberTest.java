package com.flc;

import com.flc.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the Member class
 */
public class MemberTest {
    private Member member;

    @Before
    public void setUp() {
        member = new Member(1, "John Doe", "john@example.com");
    }

    @Test
    public void testMemberCreation() {
        assertEquals(1, member.getMemberId());
        assertEquals("John Doe", member.getName());
        assertEquals("john@example.com", member.getEmail());
    }

    @Test
    public void testMemberBookings() {
        assertEquals(0, member.getBookings().size());

        Lesson lesson = new Lesson(ExerciseType.YOGA, Day.SATURDAY, TimeSlot.MORNING, 1);
        Booking booking = new Booking(member, lesson);
        
        member.addBooking(booking);
        assertEquals(1, member.getBookings().size());

        member.removeBooking(booking);
        assertEquals(0, member.getBookings().size());
    }

    @Test
    public void testMemberEquality() {
        Member member2 = new Member(1, "Jane Doe", "jane@example.com");
        Member member3 = new Member(2, "John Doe", "john@example.com");

        assertEquals(member, member2); // Same ID
        assertNotEquals(member, member3); // Different ID
    }

    @Test
    public void testGetBookingsForWeekend() {
        Lesson lesson1 = new Lesson(ExerciseType.YOGA, Day.SATURDAY, TimeSlot.MORNING, 1);
        Lesson lesson2 = new Lesson(ExerciseType.ZUMBA, Day.SUNDAY, TimeSlot.AFTERNOON, 2);
        
        Booking booking1 = new Booking(member, lesson1);
        Booking booking2 = new Booking(member, lesson2);
        
        member.addBooking(booking1);
        member.addBooking(booking2);

        assertEquals(1, member.getBookingsForWeekend(1).size());
        assertEquals(1, member.getBookingsForWeekend(2).size());
        assertEquals(0, member.getBookingsForWeekend(3).size());
    }
}
