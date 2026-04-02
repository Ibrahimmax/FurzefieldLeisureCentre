package com.flc.service;

import com.flc.model.*;
import com.flc.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for managing lessons and timetable
 */
@Service
@RequiredArgsConstructor
@Transactional
public class LessonService {
    private final LessonRepository lessonRepository;

    /**
     * Creates a new lesson and adds it to the timetable
     */
    public Lesson createLesson(ExerciseType exerciseType, Day day, TimeSlot timeSlot, int weekendNumber) {
        Lesson lesson = new Lesson(exerciseType, day, timeSlot, weekendNumber);
        return lessonRepository.save(lesson);
    }

    /**
     * Gets all lessons
     */
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    /**
     * Gets lessons for a specific day
     */
    public List<Lesson> getLessonsByDay(Day day) {
        return lessonRepository.findByDay(day);
    }

    /**
     * Gets lessons by day name (case-insensitive)
     */
    public List<Lesson> getLessonsByDayName(String dayName) {
        if (dayName == null || dayName.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String normalized = dayName.trim().toUpperCase();
        try {
            Day day = Day.valueOf(normalized);
            return getLessonsByDay(day);
        } catch (IllegalArgumentException ex) {
            return new ArrayList<>();
        }
    }

    /**
     * Gets lessons for a specific exercise type
     */
    public List<Lesson> getLessonsByExerciseType(ExerciseType exerciseType) {
        return lessonRepository.findByExerciseType(exerciseType);
    }

    /**
     * Gets lessons by exercise display name (case-insensitive)
     */
    public List<Lesson> getLessonsByExerciseName(String exerciseName) {
        if (exerciseName == null || exerciseName.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String normalized = exerciseName.trim().toLowerCase();
        return getAllLessons().stream()
                .filter(l -> l.getExerciseType().getDisplayName().toLowerCase().equals(normalized))
                .collect(Collectors.toList());
    }

    /**
     * Gets lessons for a specific weekend
     */
    public List<Lesson> getLessonsByWeekend(int weekendNumber) {
        return lessonRepository.findByWeekendNumber(weekendNumber);
    }

    /**
     * Gets lessons for a specific day and weekend
     */
    public List<Lesson> getLessonsByDayAndWeekend(Day day, int weekendNumber) {
        return lessonRepository.findByWeekendAndDay(weekendNumber, day);
    }

    /**
     * Gets a specific lesson by ID
     */
    public Lesson getLessonById(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found with ID: " + lessonId));
    }

    /**
     * Gets lessons of a specific type on a specific day
     */
    public List<Lesson> getLessonsByExerciseTypeAndDay(ExerciseType exerciseType, Day day) {
        return getAllLessons().stream()
                .filter(l -> l.getExerciseType() == exerciseType && l.getDay() == day)
                .collect(Collectors.toList());
    }

    /**
     * Gets a range of lessons for multiple weekends
     */
    public List<Lesson> getLessonsForWeekendRange(int startWeekend, int endWeekend) {
        return lessonRepository.findLessonsInWeekendRange(startWeekend, endWeekend);
    }

    /**
     * Gets total number of lessons
     */
    public long getTotalLessons() {
        return lessonRepository.count();
    }

    /**
     * Gets available lessons (with space)
     */
    public List<Lesson> getAvailableLessons() {
        return getAllLessons().stream()
                .filter(Lesson::hasAvailableSpace)
                .collect(Collectors.toList());
    }
}
