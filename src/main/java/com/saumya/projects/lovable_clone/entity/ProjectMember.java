package com.saumya.projects.lovable_clone.entity;

import com.saumya.projects.lovable_clone.enums.ProjectRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level =  AccessLevel.PRIVATE)
public class ProjectMember {
    ProjectMemberId id;
    Project project; // UK, FK: project_id
    User user;    // UK, FK: user_id

    ProjectRole role;

    Long invitedBy; // FK
    Instant invitedAt;
}

// PROJECT_MEMBER table helps join USER and PROJECT tables --> JOIN table / MAPPING table.
