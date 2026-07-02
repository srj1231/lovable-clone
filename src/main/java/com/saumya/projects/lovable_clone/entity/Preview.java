package com.saumya.projects.lovable_clone.entity;

import com.saumya.projects.lovable_clone.enums.PreviewStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level =  AccessLevel.PRIVATE)
public class Preview {
    Long id; // PK
    Long projectId; // UK, FK: project_id

    String namespace;
    String podName;
    String previewUrl;

    PreviewStatus status;

    Instant startedAt;
    Instant terminatedAt;
    Instant createdAt;
}
