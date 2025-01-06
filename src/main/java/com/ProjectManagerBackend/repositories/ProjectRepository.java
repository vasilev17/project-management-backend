package com.ProjectManagerBackend.repositories;

import com.ProjectManagerBackend.models.Project;
import com.ProjectManagerBackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByTeamContainingOrCreator(User user, User creator);

    List<Project> findByNameContainingAndTeamContains(String partialName, User user);

    @Query("SELECT p.team FROM Project p WHERE p.id = :projectId")
    List<User> findTeamById(@Param("projectId") Long projectId);

}
