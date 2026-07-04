package com.saumya.projects.lovable_clone.mapper;

import com.saumya.projects.lovable_clone.dto.project.ProjectResponse;
import com.saumya.projects.lovable_clone.entity.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectResponse toProjectResponse(Project project);
}
