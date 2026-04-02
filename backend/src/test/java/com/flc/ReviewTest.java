package com.flc;

import com.flc.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the Review class
 */
public class ReviewTest {
    private Review review;
    private Member member;
    private Lesson lesson;

    @Before
    public void setUp() {
        member = new Member(1, "Test Member", "test@example.com");
        lesson = new Lesson(ExerciseType.YOGA, Day.SATURDAY, TimeSlot.MORNING, 1);
        review = new Review(member, lesson, 5, "Excellent class!");
    }

    @Test
    public void testReviewCreation() {
        assertNotNull(review.getReviewId());
        assertEquals(member, review.getMember());
        assertEquals(lesson, review.getLesson());
        assertEquals(5, review.getRating());
        assertEquals("Excellent class!", review.getComment());
        assertNotNull(review.getReviewDate());
    }

    @Test
    public void testValidRatings() {
        Review review1 = new Review(member, lesson, 1, "Very dissatisfied");
        Review review2 = new Review(member, lesson, 3, "Ok");
        Review review5 = new Review(member, lesson, 5, "Very satisfied");

        assertEquals(1, review1.getRating());
        assertEquals(3, review2.getRating());
        assertEquals(5, review5.getRating());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidRatingTooLow() {
        new Review(member, lesson, 0, "Invalid");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidRatingTooHigh() {
        new Review(member, lesson, 6, "Invalid");
    }

    @Test
    public void testReviewEquality() {
        Review review2 = new Review(member, lesson, 5, "Excellent class!");
        // Different IDs due to auto-increment
        assertNotEquals(review, review2);
    }

    @Test
    public void testAllRatingLevels() {
        for (int i = 1; i <= 5; i++) {
            Review r = new Review(member, lesson, i, "Rating " + i);
            assertEquals(i, r.getRating());
        }
    }
}
