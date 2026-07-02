package com.saumya.projects.lovable_clone.controller;

import com.saumya.projects.lovable_clone.dto.projectMember.InviteMemberRequest;
import com.saumya.projects.lovable_clone.dto.projectMember.ProjectMemberResponse;
import com.saumya.projects.lovable_clone.dto.projectMember.UpdateMemberRoleRequest;
import com.saumya.projects.lovable_clone.enums.ProjectRole;
import com.saumya.projects.lovable_clone.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects/{projectId}")
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    @GetMapping("/members")
    public ResponseEntity<List<ProjectMemberResponse>> getAllMembers(@PathVariable Long projectId) {
        Long userId = 1L;
        return ResponseEntity.ok(projectMemberService.getPerms(projectId, userId));
    }

    @PostMapping
    public ResponseEntity<ProjectMemberResponse> inviteMember(
            @PathVariable Long projectId,
            @RequestBody InviteMemberRequest request
    ) {
        Long userId = 1L;
        return ResponseEntity.status(HttpStatus.CREATED).body(projectMemberService.inviteMember(projectId, request, userId));
    }

    @PatchMapping("/memberId")
    public ResponseEntity<ProjectMemberResponse> updateMemberRole(
            @PathVariable Long projectId,
            @PathVariable Long memberId,
            @RequestBody UpdateMemberRoleRequest request
    ) {
        Long userId = 1L;
        return ResponseEntity.status(HttpStatus.CREATED).body(projectMemberService.updateMemberRole(projectId, memberId, request, userId));
    }

    @DeleteMapping("/memberId")
    public ResponseEntity<ProjectMemberResponse> deleteMember(
            @PathVariable Long projectId,
            @PathVariable Long memberId
    ) {
        Long userId = 1L;
        return ResponseEntity.ok(projectMemberService.deleteProjectMember(projectId, memberId, userId));
    }
}
