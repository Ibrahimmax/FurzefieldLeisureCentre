package com.flc.repository;

import com.flc.model.Day;
import com.flc.model.ExerciseType;
import com.flc.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for Lesson entity
 */
@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByWeekendNumber(int weekendNumber);
    
    List<Lesson> findByExerciseType(ExerciseType exerciseType);
    
    List<Lesson> findByDay(Day day);
    
    @Query("SELECT l FROM Lesson l WHERE l.weekendNumber = :weekendNumber AND l.day = :day ORDER BY l.timeSlot")
    List<Lesson> findByWeekendAndDay(@Param("weekendNumber") int weekendNumber, @Param("day") Day day);
    
    @Query("SELECT l FROM Lesson l WHERE l.exerciseType = :exerciseType AND l.weekendNumber = :weekendNumber")
    List<Lesson> findByExerciseTypeAndWeekend(@Param("exerciseType") ExerciseType exerciseType, 
                                              @Param("weekendNumber") int weekendNumber);
    
    @Query("SELECT l FROM Lesson l WHERE l.weekendNumber BETWEEN :startWeekend AND :endWeekend ORDER BY l.weekendNumber, l.day, l.timeSlot")
    List<Lesson> findLessonsInWeekendRange(@Param("startWeekend") int startWeekend, @Param("endWeekend") int endWeekend);
}
