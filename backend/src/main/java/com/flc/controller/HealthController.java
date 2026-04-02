package com.flc.controller;

import com.flc.service.BookingService;
import com.flc.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * REST Controller for system health and statistics
 */
@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class HealthController {
    private final BookingService bookingService;
    private final LessonService lessonService;

    /**
     * Get system health status
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getHealth() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("application", "FLC Booking System");
        health.put("version", "1.0.0");
        
        try {
            health.put("database", "CONNECTED");
            health.put("statistics", Map.of(
                "totalMembers", bookingService.getTotalMembers(),
                "totalBookings", bookingService.getTotalBookings(),
                "totalLessons", lessonService.getTotalLessons()
            ));
        } catch (Exception e) {
            health.put("database", "ERROR");
            health.put("error", e.getMessage());
        }
        
        return ResponseEntity.ok(health);
    }

    /**
     * Get statistics
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("timestamp", LocalDateTime.now());
        stats.put("totalMembers", bookingService.getTotalMembers());
        stats.put("totalBookings", bookingService.getTotalBookings());
        stats.put("totalLessons", lessonService.getTotalLessons());
        stats.put("availableLessons", lessonService.getAvailableLessons().size());
        
        return ResponseEntity.ok(stats);
    }
}
