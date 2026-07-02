package com.saumya.projects.lovable_clone.dto.projectMember;

import com.saumya.projects.lovable_clone.enums.ProjectRole;

public record InviteMemberRequest(
        String email,
        ProjectRole role
) {
}
