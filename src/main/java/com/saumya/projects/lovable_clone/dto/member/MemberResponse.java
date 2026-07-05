package com.saumya.projects.lovable_clone.dto.member;

import com.saumya.projects.lovable_clone.enums.ProjectRole;

import java.time.Instant;

public record MemberResponse(
        Long userId,
        String name,
        String username,
        ProjectRole projectRole,
        Instant invitedAt
) {
}
