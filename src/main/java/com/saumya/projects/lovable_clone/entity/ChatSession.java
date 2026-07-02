package com.saumya.projects.lovable_clone.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level =  AccessLevel.PRIVATE)
public class ChatSession {
    Long projectId; // FK, PK: project_id
    User user; // FK, PK: user_id
    String title;

    Instant createdAt;
    Instant updatedAt;

    Instant deletedAt;
}
