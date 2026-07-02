package com.saumya.projects.lovable_clone.service;

import com.saumya.projects.lovable_clone.dto.project.ProjectRequest;
import com.saumya.projects.lovable_clone.dto.project.ProjectResponse;

import java.util.List;

public interface ProjectService {
    List<ProjectResponse> getProjects(Long userId);

    ProjectResponse getProject(Long id, Long userId);

    ProjectResponse createProject(ProjectRequest request, Long userId);

    ProjectResponse updateProject(Long id, ProjectRequest request, Long userId);

    void softDelete(Long id, Long userId);
}
