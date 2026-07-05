package com.saumya.projects.lovable_clone.repository;

import com.saumya.projects.lovable_clone.entity.ProjectMember;
import com.saumya.projects.lovable_clone.entity.ProjectMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {

    List<ProjectMember> findByProjectId(Long projectId);
}

// JpaRepository<ProjectMember, ProjectMemberId> :
// <T, ID> : T - Entity class this repository manages, ID - Primary key type of the entity
