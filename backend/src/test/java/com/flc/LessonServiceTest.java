package com.flc;

import com.flc.model.*;
import com.flc.service.LessonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the LessonService class
 */
public class LessonServiceTest {
    private LessonService lessonService;

    @BeforeEach
    public void setUp() {
        lessonService = new LessonService();
    }

    @Test
    public void testCreateLesson() {
        Lesson lesson = lessonService.createLesson(ExerciseType.YOGA, Day.SATURDAY, TimeSlot.MORNING, 1);
        
        assertNotNull(lesson);
        assertEquals(ExerciseType.YOGA, lesson.getExerciseType());
        assertEquals(Day.SATURDAY, lesson.getDay());
        assertEquals(TimeSlot.MORNING, lesson.getTimeSlot());
        assertEquals(1, lesson.getWeekendNumber());
    }

    @Test
    public void testGetAllLessons() {
        assertEquals(0, lessonService.getAllLessons().size());
        
        lessonService.createLesson(ExerciseType.YOGA, Day.SATURDAY, TimeSlot.MORNING, 1);
        lessonService.createLesson(ExerciseType.ZUMBA, Day.SUNDAY, TimeSlot.AFTERNOON, 1);
        
        assertEquals(2, lessonService.getAllLessons().size());
    }

    @Test
    public void testGetLessonsByDay() {
        lessonService.createLesson(ExerciseType.YOGA, Day.SATURDAY, TimeSlot.MORNING, 1);
        lessonService.createLesson(ExerciseType.ZUMBA, Day.SATURDAY, TimeSlot.AFTERNOON, 1);
        lessonService.createLesson(ExerciseType.AQUACISE, Day.SUNDAY, TimeSlot.MORNING, 1);
        
        List<Lesson> saturdayLessons = lessonService.getLessonsByDay(Day.SATURDAY);
        List<Lesson> sundayLessons = lessonService.getLessonsByDay(Day.SUNDAY);
        
        assertEquals(2, saturdayLessons.size());
        assertEquals(1, sundayLessons.size());
    }

    @Test
    public void testGetLessonsByExerciseType() {
        lessonService.createLesson(ExerciseType.YOGA, Day.SATURDAY, TimeSlot.MORNING, 1);
        lessonService.createLesson(ExerciseType.YOGA, Day.SUNDAY, TimeSlot.AFTERNOON, 1);
        lessonService.createLesson(ExerciseType.ZUMBA, Day.SUNDAY, TimeSlot.MORNING, 1);
        
        List<Lesson> yogaLessons = lessonService.getLessonsByExerciseType(ExerciseType.YOGA);
        List<Lesson> zumbaLessons = lessonService.getLessonsByExerciseType(ExerciseType.ZUMBA);
        
        assertEquals(2, yogaLessons.size());
        assertEquals(1, zumbaLessons.size());
    }

    @Test
    public void testGetLessonsByWeekend() {
        lessonService.createLesson(ExerciseType.YOGA, Day.SATURDAY, TimeSlot.MORNING, 1);
        lessonService.createLesson(ExerciseType.ZUMBA, Day.SUNDAY, TimeSlot.AFTERNOON, 1);
        lessonService.createLesson(ExerciseType.AQUACISE, Day.SATURDAY, TimeSlot.MORNING, 2);
        
        List<Lesson> weekend1 = lessonService.getLessonsByWeekend(1);
        List<Lesson> weekend2 = lessonService.getLessonsByWeekend(2);
        
        assertEquals(2, weekend1.size());
        assertEquals(1, weekend2.size());
    }

    @Test
    public void testGetLessonById() {
        Lesson lesson = lessonService.createLesson(ExerciseType.YOGA, Day.SATURDAY, TimeSlot.MORNING, 1);
        
        Lesson retrieved = lessonService.getLessonById(lesson.getLessonId());
        
        assertEquals(lesson, retrieved);
    }

    @Test
    public void testGetNonexistentLesson() {
        Lesson lesson = lessonService.getLessonById(999);
        assertNull(lesson);
    }

    @Test
    public void testGetLessonsByExerciseTypeAndDay() {
        lessonService.createLesson(ExerciseType.YOGA, Day.SATURDAY, TimeSlot.MORNING, 1);
        lessonService.createLesson(ExerciseType.YOGA, Day.SATURDAY, TimeSlot.AFTERNOON, 1);
        lessonService.createLesson(ExerciseType.YOGA, Day.SUNDAY, TimeSlot.MORNING, 1);
        lessonService.createLesson(ExerciseType.ZUMBA, Day.SATURDAY, TimeSlot.MORNING, 1);
        
        List<Lesson> yogaSaturday = lessonService.getLessonsByExerciseTypeAndDay(ExerciseType.YOGA, Day.SATURDAY);
        List<Lesson> yogaSunday = lessonService.getLessonsByExerciseTypeAndDay(ExerciseType.YOGA, Day.SUNDAY);
        List<Lesson> zumbaSaturday = lessonService.getLessonsByExerciseTypeAndDay(ExerciseType.ZUMBA, Day.SATURDAY);
        
        assertEquals(2, yogaSaturday.size());
        assertEquals(1, yogaSunday.size());
        assertEquals(1, zumbaSaturday.size());
    }

    @Test
    public void testGetLessonsByExerciseName() {
        lessonService.createLesson(ExerciseType.YOGA, Day.SATURDAY, TimeSlot.MORNING, 1);
        lessonService.createLesson(ExerciseType.ZUMBA, Day.SUNDAY, TimeSlot.AFTERNOON, 1);

        List<Lesson> yogaLessons = lessonService.getLessonsByExerciseName("Yoga");
        List<Lesson> zumbaLessons = lessonService.getLessonsByExerciseName("zumba");
        List<Lesson> invalid = lessonService.getLessonsByExerciseName("notfound");

        assertEquals(1, yogaLessons.size());
        assertEquals(1, zumbaLessons.size());
        assertTrue(invalid.isEmpty());
    }

    @Test
    public void testGetLessonsByDayName() {
        lessonService.createLesson(ExerciseType.YOGA, Day.SATURDAY, TimeSlot.MORNING, 1);
        lessonService.createLesson(ExerciseType.ZUMBA, Day.SUNDAY, TimeSlot.AFTERNOON, 1);

        List<Lesson> saturdayLessons = lessonService.getLessonsByDayName("saturday");
        List<Lesson> sundayLessons = lessonService.getLessonsByDayName("Sunday");
        List<Lesson> invalid = lessonService.getLessonsByDayName("friday");

        assertEquals(1, saturdayLessons.size());
        assertEquals(1, sundayLessons.size());
        assertTrue(invalid.isEmpty());
    }
}

