package com.flc.repository;

import com.flc.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for Booking entity
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b WHERE b.member.memberId = :memberId AND b.cancelled = false")
    List<Booking> findActiveBookingsByMember(@Param("memberId") Long memberId);
    
    @Query("SELECT b FROM Booking b WHERE b.lesson.lessonId = :lessonId AND b.cancelled = false")
    List<Booking> findActiveBookingsByLesson(@Param("lessonId") Long lessonId);
    
    @Query("SELECT b FROM Booking b WHERE b.member.memberId = :memberId ORDER BY b.bookingDate DESC")
    List<Booking> findBookingsByMember(@Param("memberId") Long memberId);
    
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.lesson.lessonId = :lessonId AND b.cancelled = false")
    long countActiveBookingsByLesson(@Param("lessonId") Long lessonId);
    
    @Query("SELECT b FROM Booking b WHERE b.lesson.weekendNumber = :weekendNumber AND b.member.memberId = :memberId")
    List<Booking> findMemberBookingsForWeekend(@Param("memberId") Long memberId, @Param("weekendNumber") int weekendNumber);
}
