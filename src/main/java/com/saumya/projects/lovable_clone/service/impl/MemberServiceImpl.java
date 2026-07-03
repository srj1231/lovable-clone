package com.saumya.projects.lovable_clone.service.impl;

import com.saumya.projects.lovable_clone.dto.member.InviteMemberRequest;
import com.saumya.projects.lovable_clone.dto.member.MemberResponse;
import com.saumya.projects.lovable_clone.dto.member.UpdateMemberRoleRequest;
import com.saumya.projects.lovable_clone.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    @Override
    public List<MemberResponse> getPerms(Long projectId, Long userId) {
        return List.of();
    }

    @Override
    public MemberResponse inviteMember(Long projectId, InviteMemberRequest request, Long userId) {
        return null;
    }

    @Override
    public MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request, Long userId) {
        return null;
    }

    @Override
    public MemberResponse deleteProjectMember(Long projectId, Long memberId, Long userId) {
        return null;
    }
}
