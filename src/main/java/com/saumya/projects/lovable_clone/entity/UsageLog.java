package com.saumya.projects.lovable_clone.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level =  AccessLevel.PRIVATE)
public class UsageLog {
    Long id; // PK
    Project project; // FK: project_id
    User user; // FK: user_id
    String action;

    Integer tokensUsed;
    Integer durationMs;

    Instant createdAt;

    String metadata;
}
