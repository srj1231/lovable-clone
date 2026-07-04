package com.saumya.projects.lovable_clone.service.impl;

import com.saumya.projects.lovable_clone.dto.project.ProjectRequest;
import com.saumya.projects.lovable_clone.dto.project.ProjectResponse;
import com.saumya.projects.lovable_clone.entity.Project;
import com.saumya.projects.lovable_clone.entity.User;
import com.saumya.projects.lovable_clone.mapper.ProjectMapper;
import com.saumya.projects.lovable_clone.repository.ProjectRepository;
import com.saumya.projects.lovable_clone.repository.UserRepository;
import com.saumya.projects.lovable_clone.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Transactional  // so the entities are not generated and can be rolled back if something goes wrong
public class ProjectServiceImpl implements ProjectService {

    ProjectRepository projectRepository;
    UserRepository userRepository;
    ProjectMapper projectMapper;

    @Override
    public List<ProjectResponse> getProjects(Long userId) {
        return List.of();
    }

    @Override
    public ProjectResponse getProject(Long id, Long userId) {
        return null;
    }

    @Override
    public ProjectResponse createProject(ProjectRequest request, Long userId) {
        User owner = userRepository.findById(userId).orElseThrow();
        Project project = Project.builder()
                .name(request.name())
                .owner(owner)
                .build();

        project = projectRepository.save(project);

        // project.getOwner(): modelMapper does not support a record within a record. MapStruct does.
        // return new ProjectResponse(project.getId(), project.getName(), project.getOwner(), project.getCreatedAt(), project.getUpdatedAt(), project.getDeletedAt());

        // we need to map Project Entity to ProjectResponse
        return projectMapper.toProjectResponse(project);
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest request, Long userId) {
        return null;
    }

    @Override
    public void softDelete(Long id, Long userId) {

    }
}
