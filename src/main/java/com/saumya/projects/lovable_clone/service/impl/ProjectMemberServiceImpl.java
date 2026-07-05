package com.saumya.projects.lovable_clone.service.impl;

import com.saumya.projects.lovable_clone.dto.member.InviteMemberRequest;
import com.saumya.projects.lovable_clone.dto.member.MemberResponse;
import com.saumya.projects.lovable_clone.dto.member.UpdateMemberRoleRequest;
import com.saumya.projects.lovable_clone.entity.Project;
import com.saumya.projects.lovable_clone.entity.ProjectMember;
import com.saumya.projects.lovable_clone.entity.ProjectMemberId;
import com.saumya.projects.lovable_clone.entity.User;
import com.saumya.projects.lovable_clone.mapper.ProjectMemberMapper;
import com.saumya.projects.lovable_clone.repository.ProjectMemberRepository;
import com.saumya.projects.lovable_clone.repository.ProjectRepository;
import com.saumya.projects.lovable_clone.repository.UserRepository;
import com.saumya.projects.lovable_clone.service.ProjectMemberService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Transactional
public class ProjectMemberServiceImpl implements ProjectMemberService {

    ProjectMemberRepository projectMemberRepository;
    ProjectRepository projectRepository;
    UserRepository userRepository;
    ProjectMemberMapper projectMemberMapper;

    @Override
    public List<MemberResponse> getProjectMembers(Long projectId, Long userId) {
        Project project = getAccessibleProjectById(projectId, userId);

        // List<MemberResponse> membersResponseList = new ArrayList<>();

        // don't need to get owner from project, can be fetched from projectMemberRepository with projectRole = "OWNER"
        // membersResponseList.add(projectMemberMapper.toMemberResponseFromOwner(project.getOwner()));

        return projectMemberRepository.findByProjectId(projectId)
                .stream()
                .map(projectMemberMapper::toMemberResponseFromMember)
                .toList();
    }

    @Override
    public MemberResponse inviteMember(Long projectId, InviteMemberRequest request, Long userId) {
        Project project = getAccessibleProjectById(projectId, userId);

        User invitee = userRepository.findByUsername(request.email()).orElseThrow();

        // this can be handled by Spring Security later
//        if (invitee.getId().equals(project.getOwner().getId())) {
//            throw new RuntimeException("Not allowed.");
//        }

        if(invitee.getId().equals(userId)){
            throw new RuntimeException("You can't invite yourself");
        }

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, invitee.getId());

        if(projectMemberRepository.existsById(projectMemberId)){
            throw new RuntimeException("User is already a member");
        }

        ProjectMember member = ProjectMember.builder()
                                .id(projectMemberId)
                                .project(project)
                                .user(invitee)
                                .projectRole(request.role())
                                .invitedAt(Instant.now())
                                .build();

        projectMemberRepository.save(member);

        return projectMemberMapper.toMemberResponseFromMember(member);
    }

    @Override
    public MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request, Long userId) {
        Project project = getAccessibleProjectById(projectId, userId);

        // this can be handled by Spring Security later
//        if(!project.getOwner().getId().equals(userId)){
//            throw new RuntimeException("You can't make changes to the project.");
//        }

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, memberId);

        ProjectMember member = projectMemberRepository.findById(projectMemberId).orElseThrow();
        member.setProjectRole(request.role());

        projectMemberRepository.save(member);
        return projectMemberMapper.toMemberResponseFromMember(member);
    }

    @Override
    public void removeProjectMember(Long projectId, Long memberId, Long userId) {
        Project project = getAccessibleProjectById(projectId, userId);

        // this can be handled by Spring Security later
//        if(!project.getOwner().getId().equals(userId)){
//            throw new RuntimeException("You can't make changes to the project.");
//        }

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, memberId);
        if(!projectMemberRepository.existsById(projectMemberId)) {
            throw new RuntimeException("User is not a member of the project");
        }

        projectMemberRepository.deleteById(projectMemberId);
    }

    public Project getAccessibleProjectById(Long id, Long userId) {
        return projectRepository.findAccessibleProjectById(id, userId).orElseThrow();
    }
}
