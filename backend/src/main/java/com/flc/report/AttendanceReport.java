package com.flc.report;

import com.flc.model.*;
import com.flc.service.LessonService;
import java.util.*;

/**
 * Generates attendance and rating reports for group exercise lessons
 */
public class AttendanceReport {
    private LessonService lessonService;
    private int weekendNumber;

    public AttendanceReport(LessonService lessonService, int weekendNumber) {
        this.lessonService = lessonService;
        this.weekendNumber = weekendNumber;
    }

    /**
     * Generates and prints an attendance report for the specified weekend
     */
    public void generateReport() {
        Map<ExerciseType, ReportData> reportData = new TreeMap<>(
                (e1, e2) -> e1.getDisplayName().compareTo(e2.getDisplayName())
        );

        // Collect data
        List<Lesson> lessonsInWeekend = lessonService.getLessonsByWeekend(weekendNumber);
        for (Lesson lesson : lessonsInWeekend) {
            ExerciseType type = lesson.getExerciseType();
            ReportData data = reportData.computeIfAbsent(type, k -> new ReportData(type));

            data.addMemberCount(lesson.getCurrentBookings());
            if (!lesson.getReviews().isEmpty()) {
                data.addAverageRating(lesson.getAverageRating());
                data.addReviewCount(lesson.getReviews().size());
            }
        }

        // Print report
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ATTENDANCE AND RATING REPORT - WEEKEND " + weekendNumber);
        System.out.println("=".repeat(80));
        System.out.printf("%-20s | %-15s | %-20s | %-15s%n", "Exercise Type", "Total Members", "Avg Rating (across lessons)", "Total Reviews");
        System.out.println("-".repeat(80));

        double totalMembers = 0;
        double totalReviews = 0;
        double totalRatings = 0;

        for (ReportData data : reportData.values()) {
            System.out.printf("%-20s | %-15d | %-20.2f | %-15d%n",
                    data.exerciseType.getDisplayName(),
                    data.getTotalMembers(),
                    data.getAverageRating(),
                    data.getTotalReviews());
            totalMembers += data.getTotalMembers();
            totalReviews += data.getTotalReviews();
            totalRatings += data.getTotalRatings();
        }

        System.out.println("-".repeat(80));
        System.out.printf("%-20s | %-15.0f | %-20.2f | %-15.0f%n",
                "TOTAL",
                totalMembers,
                reportData.isEmpty() ? 0 : totalRatings / reportData.size(),
                totalReviews);
        System.out.println("=".repeat(80));
    }

    /**
     * Inner class to hold report data for each exercise type
     */
    private static class ReportData {
        ExerciseType exerciseType;
        List<Integer> memberCounts;
        List<Double> averageRatings;
        int totalReviews;

        ReportData(ExerciseType exerciseType) {
            this.exerciseType = exerciseType;
            this.memberCounts = new ArrayList<>();
            this.averageRatings = new ArrayList<>();
            this.totalReviews = 0;
        }

        void addMemberCount(int count) {
            memberCounts.add(count);
        }

        void addAverageRating(double rating) {
            averageRatings.add(rating);
        }

        void addReviewCount(int count) {
            totalReviews += count;
        }

        int getTotalMembers() {
            return memberCounts.stream().mapToInt(Integer::intValue).sum();
        }

        double getAverageRating() {
            if (averageRatings.isEmpty()) {
                return 0;
            }
            return averageRatings.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        }

        double getTotalRatings() {
            return averageRatings.stream().mapToDouble(Double::doubleValue).sum();
        }

        int getTotalReviews() {
            return totalReviews;
        }
    }
}
