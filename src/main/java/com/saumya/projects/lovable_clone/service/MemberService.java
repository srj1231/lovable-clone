package com.saumya.projects.lovable_clone.service;

import com.saumya.projects.lovable_clone.dto.member.InviteMemberRequest;
import com.saumya.projects.lovable_clone.dto.member.MemberResponse;
import com.saumya.projects.lovable_clone.dto.member.UpdateMemberRoleRequest;

import java.util.List;

public interface MemberService {
    List<MemberResponse> getPerms(Long projectId, Long userId);

    MemberResponse inviteMember(Long projectId, InviteMemberRequest request, Long userId);

    MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request, Long userId);

    MemberResponse deleteProjectMember(Long projectId, Long memberId, Long userId);
}
