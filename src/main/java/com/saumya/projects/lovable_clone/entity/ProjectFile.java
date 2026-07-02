package com.saumya.projects.lovable_clone.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level =  AccessLevel.PRIVATE)
public class ProjectFile {
    Long id;    // PK
    Long projectId; // FK: project_id

    String path; // UK: path
    String minioObjectKey;

    Long createdBy; // FK: created_by
    Long updatedBy; // FK: updated_by

    Instant createdAt;
    Instant updatedAt;

    Instant deletedAt; // soft delete
}
