package com.flc.controller;

import com.flc.model.Member;
import com.flc.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST Controller for Member management
 */
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MemberController {
    private final BookingService bookingService;

    /**
     * Get all members
     */
    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(bookingService.getAllMembers());
    }

    /**
     * Get member by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(bookingService.getMemberById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Register a new member
     */
    @PostMapping
    public ResponseEntity<Member> registerMember(@RequestBody MemberDTO dto) {
        try {
            Member member = bookingService.registerMember(dto.getName(), dto.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(member);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get member by email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Member> getMemberByEmail(@PathVariable String email) {
        try {
            return ResponseEntity.ok(bookingService.getMemberByEmail(email));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a member
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        try {
            bookingService.getMemberById(id);
            // Delete logic would go here
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DTO for Member registration
     */
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class MemberDTO {
        private String name;
        private String email;
    }
}
