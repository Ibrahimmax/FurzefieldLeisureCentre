package com.flc.config;

import com.flc.model.*;
import com.flc.service.BookingService;
import com.flc.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

/**
 * Configuration class to initialize sample data on application startup
 */
@Configuration
@RequiredArgsConstructor
public class DataInitializationConfig {
    private final LessonService lessonService;
    private final BookingService bookingService;

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            // Create sample lessons (8 weekends, 2 days, 3 time slots, 8 exercise types)
            // Total: 8 * 2 * 3 * 8 = 384 sample lessons
            
            int lessonCount = 0;
            for (int weekend = 1; weekend <= 8; weekend++) {
                for (Day day : new Day[]{Day.SATURDAY, Day.SUNDAY}) {
                    for (TimeSlot timeSlot : new TimeSlot[]{TimeSlot.MORNING, TimeSlot.AFTERNOON, TimeSlot.EVENING}) {
                        for (ExerciseType exerciseType : ExerciseType.values()) {
                            lessonService.createLesson(exerciseType, day, timeSlot, weekend);
                            lessonCount++;
                        }
                    }
                }
            }
            
            System.out.println("\n✓ Initialized " + lessonCount + " lessons in the database");

            // Create sample members
            List<String> memberNames = List.of(
                "Alice Johnson", "Bob Smith", "Carol Williams", "David Brown",
                "Eve Martinez", "Frank Garcia", "Grace Lee", "Henry Zhang",
                "Iris Patel", "Jack Robinson"
            );

            List<String> memberEmails = List.of(
                "alice@example.com", "bob@example.com", "carol@example.com", "david@example.com",
                "eve@example.com", "frank@example.com", "grace@example.com", "henry@example.com",
                "iris@example.com", "jack@example.com"
            );

            for (int i = 0; i < memberNames.size(); i++) {
                bookingService.registerMember(memberNames.get(i), memberEmails.get(i));
            }
            
            System.out.println("✓ Initialized " + memberNames.size() + " members in the database");
            System.out.println("\n═══════════════════════════════════════════════════════════");
            System.out.println("Application ready! Database populated with sample data.");
            System.out.println("═══════════════════════════════════════════════════════════\n");
        };
    }
}
