package com.flc.controller;

import com.flc.model.Day;
import com.flc.model.ExerciseType;
import com.flc.model.Lesson;
import com.flc.model.TimeSlot;
import com.flc.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST Controller for Lesson management
 */
@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LessonController {
    private final LessonService lessonService;

    /**
     * Get all lessons
     */
    @GetMapping
    public ResponseEntity<List<Lesson>> getAllLessons() {
        return ResponseEntity.ok(lessonService.getAllLessons());
    }

    /**
     * Get lesson by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(lessonService.getLessonById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create a new lesson
     */
    @PostMapping
    public ResponseEntity<Lesson> createLesson(@RequestBody LessonDTO dto) {
        try {
            Lesson lesson = lessonService.createLesson(
                dto.getExerciseType(),
                dto.getDay(),
                dto.getTimeSlot(),
                dto.getWeekendNumber()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(lesson);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get lessons by weekend
     */
    @GetMapping("/weekend/{weekendNumber}")
    public ResponseEntity<List<Lesson>> getLessonsByWeekend(@PathVariable int weekendNumber) {
        return ResponseEntity.ok(lessonService.getLessonsByWeekend(weekendNumber));
    }

    /**
     * Get lessons by day
     */
    @GetMapping("/day/{day}")
    public ResponseEntity<List<Lesson>> getLessonsByDay(@PathVariable String day) {
        return ResponseEntity.ok(lessonService.getLessonsByDayName(day));
    }

    /**
     * Get lessons by exercise type
     */
    @GetMapping("/exercise/{exerciseName}")
    public ResponseEntity<List<Lesson>> getLessonsByExercise(@PathVariable String exerciseName) {
        return ResponseEntity.ok(lessonService.getLessonsByExerciseName(exerciseName));
    }

    /**
     * Get available lessons (with space)
     */
    @GetMapping("/available")
    public ResponseEntity<List<Lesson>> getAvailableLessons() {
        return ResponseEntity.ok(lessonService.getAvailableLessons());
    }

    /**
     * Get lessons for weekend range
     */
    @GetMapping("/range")
    public ResponseEntity<List<Lesson>> getLessonsForRange(
        @RequestParam int startWeekend,
        @RequestParam int endWeekend
    ) {
        return ResponseEntity.ok(lessonService.getLessonsForWeekendRange(startWeekend, endWeekend));
    }

    /**
     * DTO for creating lessons
     */
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class LessonDTO {
        private ExerciseType exerciseType;
        private Day day;
        private TimeSlot timeSlot;
        private int weekendNumber;
    }
}
