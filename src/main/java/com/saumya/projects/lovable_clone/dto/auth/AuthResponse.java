package com.saumya.projects.lovable_clone.dto.auth;

public record AuthResponse(
        String token,
        UserProfileResponse userProfileResponse
) {

}
