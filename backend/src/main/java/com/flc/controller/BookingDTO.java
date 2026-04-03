package com.flc.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for booking requests
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private Long memberId;
    private Long lessonId;
}
