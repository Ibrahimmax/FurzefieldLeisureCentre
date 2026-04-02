package com.flc.report;

import com.flc.model.*;
import com.flc.service.LessonService;

import java.util.*;

/**
 * Generates summary reports across week ranges (e.g., 1-4 weekends) to match assignment requirements.
 */
public class SummaryReport {
    private LessonService lessonService;
    private int startWeekend;
    private int endWeekend;

    public SummaryReport(LessonService lessonService, int startWeekend, int endWeekend) {
        this.lessonService = lessonService;
        this.startWeekend = startWeekend;
        this.endWeekend = endWeekend;
    }

    public void generateSummary() {
        List<Lesson> lessons = new ArrayList<>();
        for (int weekend = Math.max(1, startWeekend); weekend <= endWeekend; weekend++) {
            lessons.addAll(lessonService.getLessonsByWeekend(weekend));
        }

        Map<Day, Map<ExerciseType, DayExerciseData>> map = new TreeMap<>(Comparator.comparing(Day::getDisplayName));
        Map<ExerciseType, Double> incomeByExercise = new HashMap<>();

        for (Lesson lesson : lessons) {
            Day day = lesson.getDay();
            ExerciseType type = lesson.getExerciseType();

            map.computeIfAbsent(day, d -> new TreeMap<>(Comparator.comparing(ExerciseType::getDisplayName)))
                    .computeIfAbsent(type, t -> new DayExerciseData())
                    .addLessonData(lesson);

            incomeByExercise.merge(type, lesson.getIncome(), Double::sum);
        }

        // Calculate highest income across ALL lessons in ALL weekends
        Map<ExerciseType, Double> incomeAllWeekends = new HashMap<>();
        for (Lesson globalLesson : lessonService.getAllLessons()) {
            double lessonIncome = globalLesson.getCurrentBookings() * globalLesson.getExerciseType().getPrice();
            incomeAllWeekends.merge(globalLesson.getExerciseType(), lessonIncome, Double::sum);
        }

        Optional<Map.Entry<ExerciseType, Double>> highestAllWeekends = incomeAllWeekends.entrySet().stream()
                .max(Map.Entry.comparingByValue());

        System.out.println("\n" + "=".repeat(80));
        System.out.printf("SUMMARY ATTENDANCE REPORT (Weekends %d - %d)\n", startWeekend, endWeekend);
        System.out.println("=".repeat(80));
        System.out.printf("%-12s | %-15s | %-15s | %-15s | %-15s\n", "Day", "Exercise", "Total Members", "Total Reviews", "Avg Rating");
        System.out.println("-".repeat(80));

        for (Map.Entry<Day, Map<ExerciseType, DayExerciseData>> dayEntry : map.entrySet()) {
            Day day = dayEntry.getKey();
            for (Map.Entry<ExerciseType, DayExerciseData> exerciseEntry : dayEntry.getValue().entrySet()) {
                DayExerciseData data = exerciseEntry.getValue();
                System.out.printf("%-12s | %-15s | %-15d | %-15d | %-15.2f\n",
                        day.getDisplayName(),
                        exerciseEntry.getKey().getDisplayName(),
                        data.getTotalMembers(),
                        data.getTotalReviews(),
                        data.getAverageRating());
            }
        }

        System.out.println("=".repeat(80));

        // Highest income exercise type across the selected weekends
        Optional<Map.Entry<ExerciseType, Double>> highest = incomeByExercise.entrySet().stream()
                .max(Map.Entry.comparingByValue());

        System.out.println("\n" + "=".repeat(80));
        System.out.printf("HIGHEST INCOME REPORT (Weekends %d - %d)\n", startWeekend, endWeekend);
        System.out.println("=".repeat(80));
        if (highest.isPresent()) {
            System.out.printf("%s continues to bring the highest income for selected weekends: $%.2f\n",
                    highest.get().getKey().getDisplayName(),
                    highest.get().getValue());
        } else {
            System.out.println("No income data available for selected range.");
        }

        System.out.println("\n" + "=".repeat(80));
        System.out.println("=== HIGHEST INCOME EXERCISE (ALL WEEKENDS COMBINED) ===");
        System.out.println("=".repeat(80));
        if (highestAllWeekends.isPresent()) {
            System.out.printf("Exercise: %s | Total Income: $%.2f\n",
                    highestAllWeekends.get().getKey().getDisplayName(),
                    highestAllWeekends.get().getValue());
        } else {
            System.out.println("No income data available across all weekends.");
        }
        System.out.println("=".repeat(80));
    }

    private static class DayExerciseData {
        private int totalMembers = 0;
        private int totalReviews = 0;
        private int totalRatingPoints = 0;

        void addLessonData(Lesson lesson) {
            totalMembers += lesson.getCurrentBookings();
            List<Review> reviews = lesson.getReviews();
            totalReviews += reviews.size();
            for (Review r : reviews) {
                totalRatingPoints += r.getRating();
            }
        }

        int getTotalMembers() {
            return totalMembers;
        }

        int getTotalReviews() {
            return totalReviews;
        }

        double getAverageRating() {
            if (totalReviews == 0) {
                return 0;
            }
            return (double) totalRatingPoints / totalReviews;
        }
    }
}
