package com.ProjectManagerBackend.services.interfaces;

import com.ProjectManagerBackend.models.Discussion;
import com.ProjectManagerBackend.models.Project;
import com.ProjectManagerBackend.models.User;

import java.util.List;

public interface ProjectService {

    Project createProject(Project project, User user) throws Exception;

    List<Project> getProjectByTeam(String email, String category, String tag) throws Exception;

    Project getProjectById(Long projectId) throws Exception;

    void deleteProject(Long projectId, User user) throws Exception;

    Project updateProject(User user, Project updatedProject, Long projectId) throws Exception;

    void addUserToProject(Long projectId, Long userId) throws Exception;

    void removeUserFromProject(Long projectId, Long userId) throws Exception;

    Discussion getDiscussionByProjectId(Long projectId) throws Exception;

    List<Project> searchProjects(String keyword, User user) throws Exception;

    List<User> getProjectTeam(Long projectId) throws Exception;

}
