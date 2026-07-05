package com.saumya.projects.lovable_clone.service.impl;

import com.saumya.projects.lovable_clone.dto.project.ProjectRequest;
import com.saumya.projects.lovable_clone.dto.project.ProjectResponse;
import com.saumya.projects.lovable_clone.dto.project.ProjectSummaryResponse;
import com.saumya.projects.lovable_clone.entity.Project;
import com.saumya.projects.lovable_clone.entity.ProjectMember;
import com.saumya.projects.lovable_clone.entity.ProjectMemberId;
import com.saumya.projects.lovable_clone.entity.User;
import com.saumya.projects.lovable_clone.enums.ProjectRole;
import com.saumya.projects.lovable_clone.exceptions.ResourceNotFoundException;
import com.saumya.projects.lovable_clone.mapper.ProjectMapper;
import com.saumya.projects.lovable_clone.repository.ProjectMemberRepository;
import com.saumya.projects.lovable_clone.repository.ProjectRepository;
import com.saumya.projects.lovable_clone.repository.UserRepository;
import com.saumya.projects.lovable_clone.service.ProjectService;
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
@Transactional  // so the entities are not generated and can be rolled back if something goes wrong
public class ProjectServiceImpl implements ProjectService {

    ProjectRepository projectRepository;
    UserRepository userRepository;
    ProjectMemberRepository projectMemberRepository;
    ProjectMapper projectMapper;

    @Override
    public List<ProjectSummaryResponse> getUserProjects(Long userId) {
// one way:
//        return projectRepository.findProjectsByUser(userId)
//        .stream()
//        .map(projectMapper::toProjectSummaryResponse)
//        .toList();

// another way:
        var projects = projectRepository.findProjectsByUser(userId);
        return projectMapper.toListProjectSummaryResponse(projects);
    }

    @Override
    public ProjectResponse getProjectById(Long id, Long userId) {
        Project project = getAccessibleProjectById(id, userId);
        return projectMapper.toProjectResponse(project);
    }

    @Override
    public ProjectResponse createProject(ProjectRequest request, Long userId) {
        Project project = Project.builder()
                .name(request.name())
                .build();

        projectRepository.save(project);

        User owner = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(userId, "User")
        );
        ProjectMemberId projectMemberId = new ProjectMemberId(project.getId(), owner.getId());
        ProjectMember projectMember = ProjectMember.builder()
                .id(projectMemberId)
                .project(project)
                .user(owner)
                .projectRole(ProjectRole.OWNER)
                .invitedAt(Instant.now())
                .build();

        projectMemberRepository.save(projectMember);

        // project.getOwner(): modelMapper does not support a record within a record. MapStruct does.
        // return new ProjectResponse(project.getId(), project.getName(), project.getOwner(), project.getCreatedAt(), project.getUpdatedAt(), project.getDeletedAt());

        // we need to map Project Entity to ProjectResponse
        return projectMapper.toProjectResponse(project);
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest request, Long userId) {
        Project project = getAccessibleProjectById(id, userId);

        // updating project name
        project.setName(request.name());
        project = projectRepository.save(project);

        return projectMapper.toProjectResponse(project);
    }

    @Override
    public void softDelete(Long id, Long userId) {
        Project project = getAccessibleProjectById(id, userId);

        // to be handled by Spring Security later
//        if(!project.getOwner().getId().equals(userId)){
//            throw new RuntimeException("You are not authorised to delete this project.");
//        }

        project.setDeletedAt(Instant.now());
        projectRepository.save(project);
    }

    public Project getAccessibleProjectById(Long id, Long userId) {
        return projectRepository.findAccessibleProjectById(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException(id, "Project"));
    }
}
