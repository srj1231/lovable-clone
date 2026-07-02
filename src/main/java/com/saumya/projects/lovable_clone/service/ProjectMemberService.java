package com.saumya.projects.lovable_clone.service;

import com.saumya.projects.lovable_clone.dto.projectMember.InviteMemberRequest;
import com.saumya.projects.lovable_clone.dto.projectMember.ProjectMemberResponse;
import com.saumya.projects.lovable_clone.dto.projectMember.UpdateMemberRoleRequest;

import java.util.List;

public interface ProjectMemberService {
    List<ProjectMemberResponse> getPerms(Long projectId, Long userId);

    ProjectMemberResponse inviteMember(Long projectId, InviteMemberRequest request, Long userId);

    ProjectMemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request, Long userId);

    ProjectMemberResponse deleteProjectMember(Long projectId, Long memberId, Long userId);
}
