package com.flc;

import com.flc.model.*;
import com.flc.report.SummaryReport;
import com.flc.service.BookingService;
import com.flc.service.LessonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class SummaryReportTest {
    private LessonService lessonService;
    private BookingService bookingService;

    @BeforeEach
    public void setUp() {
        lessonService = new LessonService();
        bookingService = new BookingService(lessonService);
    }

    @Test
    public void testSummaryReportGeneratesWithoutException() {
        // Setup sample data
        Member m1 = bookingService.registerMember(1, "Alice", "alice@test.com");
        Lesson lesson = lessonService.createLesson(ExerciseType.YOGA, Day.SATURDAY, TimeSlot.MORNING, 1);
        bookingService.bookLesson(m1, lesson);
        bookingService.addReview(m1, lesson, 4, "Great");

        SummaryReport summaryReport = new SummaryReport(lessonService, 1, 1);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        try {
            System.setOut(new PrintStream(baos));
            summaryReport.generateSummary();
        } finally {
            System.setOut(originalOut);
        }

        String output = baos.toString();
        assertTrue(output.contains("SUMMARY ATTENDANCE REPORT"));
        assertTrue(output.contains("Highest Income Exercise") || output.contains("HIGHEST INCOME REPORT"));
    }
}
