package com.saumya.projects.lovable_clone.entity;

import com.saumya.projects.lovable_clone.enums.ProjectRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level =  AccessLevel.PRIVATE)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "project_members")
public class ProjectMember {

    @EmbeddedId
    ProjectMemberId id;

    @ManyToOne
    @MapsId("projectId")    // Maps the id field of the embedded id with the project_id column
    Project project; // UK, FK: project_id

    @ManyToOne  // fk user_id
    @MapsId("userId")   // Maps the id field of the embedded id with the user_id column
    User user;    // UK, FK: user_id

    // primary key (project_id, user_id) - composite key

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    ProjectRole projectRole;

    Instant invitedAt;
}

// PROJECT_MEMBER table helps join USER and PROJECT tables --> JOIN table / MAPPING table.
