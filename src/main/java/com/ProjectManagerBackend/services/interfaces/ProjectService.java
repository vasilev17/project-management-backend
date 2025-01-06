package com.ProjectManagerBackend.services.interfaces;

import com.ProjectManagerBackend.common.enums.DevelopmentScope;
import com.ProjectManagerBackend.dtos.ProjectDTO;
import com.ProjectManagerBackend.models.Discussion;
import com.ProjectManagerBackend.models.Project;
import com.ProjectManagerBackend.models.User;

import java.util.List;

public interface ProjectService {

    Project createProject(ProjectDTO projectModel, User user) throws Exception;

    List<Project> getProjectsByTeam(String email, DevelopmentScope developmentScope, String tag) throws Exception;

    Project getProjectById(Long projectId) throws Exception;

    void deleteProject(Long projectId, User user) throws Exception;

    Project updateProject(User user, ProjectDTO updatedProjectModel, Long projectId) throws Exception;

    void addUserToProject(Long projectId, Long userId) throws Exception;

    void removeUserFromProject(Long projectId, User deleter, Long userId) throws Exception;

    Discussion getDiscussionByProjectId(Long projectId) throws Exception;

    List<Project> searchProjects(String keyword, User user) throws Exception;

    List<User> getProjectTeam(Long projectId) throws Exception;

    void checkTeamMembership(Long projectId, User user, String errorMsg) throws Exception;

}
