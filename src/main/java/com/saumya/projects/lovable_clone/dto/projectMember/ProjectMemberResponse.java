package com.saumya.projects.lovable_clone.dto.projectMember;

import com.saumya.projects.lovable_clone.enums.ProjectRole;

import java.time.Instant;

public record ProjectMemberResponse(
        Long userId,
        String name,
        String email,
        String avatarUrl,
        ProjectRole role,
        Instant invitedAt
) {
}
