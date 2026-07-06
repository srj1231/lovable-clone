package com.saumya.projects.lovable_clone.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank String username,
        @Size(min = 4) String password
) {
}
