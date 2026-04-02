package com.flc.repository;

import com.flc.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for Review entity
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.lesson.lessonId = :lessonId ORDER BY r.reviewDate DESC")
    List<Review> findReviewsByLesson(@Param("lessonId") Long lessonId);
    
    @Query("SELECT r FROM Review r WHERE r.member.memberId = :memberId ORDER BY r.reviewDate DESC")
    List<Review> findReviewsByMember(@Param("memberId") Long memberId);
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.lesson.lessonId = :lessonId")
    Double getAverageRatingForLesson(@Param("lessonId") Long lessonId);
    
    @Query("SELECT COUNT(r) FROM Review r WHERE r.lesson.lessonId = :lessonId")
    long countReviewsForLesson(@Param("lessonId") Long lessonId);
    
    @Query("SELECT r FROM Review r WHERE r.rating >= :minRating ORDER BY r.rating DESC, r.reviewDate DESC")
    List<Review> findTopReviews(@Param("minRating") int minRating);
}
