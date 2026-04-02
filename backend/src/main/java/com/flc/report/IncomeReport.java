package com.flc.report;

import com.flc.model.*;
import com.flc.service.LessonService;
import java.util.*;

/**
 * Generates an income report showing which exercise type has generated the highest income
 */
public class IncomeReport {
    private LessonService lessonService;
    private int weekendNumber;

    public IncomeReport(LessonService lessonService, int weekendNumber) {
        this.lessonService = lessonService;
        this.weekendNumber = weekendNumber;
    }

    /**
     * Generates and prints an income report for the specified weekend
     */
    public void generateReport() {
        Map<ExerciseType, IncomeData> incomeData = new HashMap<>();

        // Collect data
        List<Lesson> lessonsInWeekend = lessonService.getLessonsByWeekend(weekendNumber);
        for (Lesson lesson : lessonsInWeekend) {
            ExerciseType type = lesson.getExerciseType();
            IncomeData data = incomeData.computeIfAbsent(type, k -> new IncomeData(type));

            data.addBookingCount(lesson.getCurrentBookings());
            data.addIncome(lesson.getIncome());
        }

        // Sort by income (descending)
        List<IncomeData> sortedData = new ArrayList<>(incomeData.values());
        sortedData.sort((d1, d2) -> Double.compare(d2.getTotalIncome(), d1.getTotalIncome()));

        // Print report
        System.out.println("\n" + "=".repeat(80));
        System.out.println("INCOME REPORT - WEEKEND " + weekendNumber);
        System.out.println("=".repeat(80));
        System.out.printf("%-20s | %-15s | %-15s | %-20s%n", "Exercise Type", "Total Members", "Price per Lesson", "Total Income");
        System.out.println("-".repeat(80));

        double totalIncome = 0;
        int totalMembers = 0;

        for (IncomeData data : sortedData) {
            System.out.printf("%-20s | %-15d | $%-14.2f | $%-19.2f%n",
                    data.exerciseType.getDisplayName(),
                    data.getTotalBookings(),
                    data.exerciseType.getPrice(),
                    data.getTotalIncome());
            totalIncome += data.getTotalIncome();
            totalMembers += data.getTotalBookings();
        }

        System.out.println("-".repeat(80));
        System.out.printf("%-20s | %-15d | %-15s | $%-19.2f%n",
                "TOTAL",
                totalMembers,
                "",
                totalIncome);
        System.out.println("=".repeat(80));

        if (!sortedData.isEmpty()) {
            IncomeData highest = sortedData.get(0);
            System.out.printf("\nHighest Income Exercise: %s ($%.2f)%n",
                    highest.exerciseType.getDisplayName(),
                    highest.getTotalIncome());
        }
    }

    /**
     * Inner class to hold income data for each exercise type
     */
    private static class IncomeData {
        ExerciseType exerciseType;
        int totalBookings;
        double totalIncome;

        IncomeData(ExerciseType exerciseType) {
            this.exerciseType = exerciseType;
            this.totalBookings = 0;
            this.totalIncome = 0;
        }

        void addBookingCount(int count) {
            totalBookings += count;
        }

        void addIncome(double income) {
            totalIncome += income;
        }

        int getTotalBookings() {
            return totalBookings;
        }

        double getTotalIncome() {
            return totalIncome;
        }
    }
}
