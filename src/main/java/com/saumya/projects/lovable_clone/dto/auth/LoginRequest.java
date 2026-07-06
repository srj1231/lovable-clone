package com.saumya.projects.lovable_clone.dto.auth;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank String username,
        @Min(4) String password
) {
}
