package com.saumya.projects.lovable_clone.mapper;

import com.saumya.projects.lovable_clone.dto.project.ProjectResponse;
import com.saumya.projects.lovable_clone.dto.project.ProjectSummaryResponse;
import com.saumya.projects.lovable_clone.entity.Project;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectResponse toProjectResponse(Project project);
    ProjectSummaryResponse toProjectSummaryResponse(Project project);

    List<ProjectSummaryResponse> toListProjectSummaryResponse(List<Project> projects);
}
