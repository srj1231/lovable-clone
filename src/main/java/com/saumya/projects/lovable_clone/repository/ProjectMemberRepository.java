package com.saumya.projects.lovable_clone.repository;

import com.saumya.projects.lovable_clone.entity.ProjectMember;
import com.saumya.projects.lovable_clone.entity.ProjectMemberId;
import com.saumya.projects.lovable_clone.enums.ProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {

    List<ProjectMember> findByProjectId(Long projectId);

    @Query("""
        SELECT pm.projectRole FROM ProjectMember pm
        WHERE pm.id.projectId = :projectId AND pm.id.userId = :userId
    """)
    Optional<ProjectRole> findRoleByProjectIdAndUserId(
            @Param("projectId") Long projectId, @Param("userId") Long userId);
}

// JpaRepository<ProjectMember, ProjectMemberId> :
// <T, ID> : T - Entity class this repository manages, ID - Primary key type of the entity
