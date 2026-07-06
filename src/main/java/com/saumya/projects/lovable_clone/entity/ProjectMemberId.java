package com.saumya.projects.lovable_clone.entity;

import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProjectMemberId {
    Long projectId;
    Long userId;
}

/*
 * @EqualsAndHashCode:
 * Hibernate requires equals() and hashCode() for composite primary keys to:
 * - Properly identify entity instances in the persistence context
 * - Manage entity caching and dirty checking
 * - Handle collections and relationships correctly
 */
