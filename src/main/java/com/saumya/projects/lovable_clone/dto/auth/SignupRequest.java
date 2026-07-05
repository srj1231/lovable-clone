package com.saumya.projects.lovable_clone.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @Size(min=1, max=30) String name,
        @Email @NotBlank String email,
        @Size(min=4) String password
) {
}
