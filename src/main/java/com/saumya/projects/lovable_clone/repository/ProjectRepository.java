package com.saumya.projects.lovable_clone.repository;

import com.saumya.projects.lovable_clone.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

//    Returns all non-deleted projects where the given user is a member (has a ProjectMember entry).
    @Query(
            """
            SELECT p FROM Project p
            WHERE p.deletedAt IS NULL
                AND EXISTS(
                    SELECT 1 FROM ProjectMember pm
                    WHERE pm.project.id = p.id
                    AND pm.user.id = :userId
                )
            ORDER BY p.createdAt DESC
            """
    )
    List<Project> findProjectsByUser(@Param("userId") Long userId);

    @Query(
            """
            SELECT p FROM Project p
            WHERE p.id = :projectId
                AND p.deletedAt IS NULL
                AND EXISTS(
                    SELECT 1 FROM ProjectMember pm
                    WHERE pm.project.id = p.id
                    AND pm.user.id = :userId
                )
            """
    )
    Optional<Project> findAccessibleProjectById(@Param("projectId") Long projectId,
                                                @Param("userId") Long userId);
}

/*
    Query to get projects by user id -
        SELECT p FROM Project p --> selects Project entities
        WHERE p.deletedAt IS NULL --> ensures project is not deleted
            AND EXISTS( --> subquery ensures user is a member of the project
                SELECT 1 FROM ProjectMember pm --> select 1 to check existence, actual column values don't matter
                WHERE pm.project.id = p.id --> joins ProjectMember to Project
                AND pm.user.id = :userId --> filters for the specific user
            )
        ORDER BY p.createdAt DESC
 */
