package com.saumya.projects.lovable_clone.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import static com.saumya.projects.lovable_clone.enums.ProjectPermission.*;

@Getter
@RequiredArgsConstructor
public enum ProjectRole {
//    EDITOR(EDIT, VIEW, DELETE), // uses varargs constructor
    EDITOR(Set.of(EDIT, VIEW, DELETE, VIEW_MEMBERS)),
    VIEWER(Set.of(VIEW, VIEW_MEMBERS)), // uses default constructor
    OWNER(Set.of(VIEW, EDIT, DELETE, MANAGE_MEMBERS, VIEW_MEMBERS));

//    varargs constructor
//    ProjectRole(ProjectPermission... permissions) {
//        this.permissions = Set.of(permissions);
//    }

    private final Set<ProjectPermission> permissions;
}