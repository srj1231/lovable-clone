package com.saumya.projects.lovable_clone.security;

import com.saumya.projects.lovable_clone.enums.ProjectRole;
import com.saumya.projects.lovable_clone.repository.ProjectMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("security")
@RequiredArgsConstructor
public class SecurityExpressions {
    private final ProjectMemberRepository projectMemberRepository;
    private final AuthUtil authUtil;

    public boolean canViewProject(Long projectId) {
        Long userId = authUtil.getCurrentUserId();
        return projectMemberRepository.findRoleByProjectIdAndUserId(projectId, userId)
                .map(role -> role.equals(ProjectRole.OWNER)
                        || role.equals(ProjectRole.EDITOR)
                        || role.equals(ProjectRole.VIEWER)
                )
                .orElse(false);
    }

    public boolean canEditProject(Long projectId) {
        Long userId = authUtil.getCurrentUserId();
        return projectMemberRepository.findRoleByProjectIdAndUserId(projectId, userId)
                .map(role -> role.equals(ProjectRole.OWNER)
                        || role.equals(ProjectRole.EDITOR)
                )
                .orElse(false);
    }
}
