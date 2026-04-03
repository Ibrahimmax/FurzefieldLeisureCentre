package com.flc.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for member registration requests
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private String name;
    private String email;
}
