package com.ProjectManagerBackend.services;

import com.ProjectManagerBackend.common.enums.DevelopmentScope;
import com.ProjectManagerBackend.common.constants.ExceptionConstants;
import com.ProjectManagerBackend.dtos.ProjectDTO;
import com.ProjectManagerBackend.mappers.ProjectMapper;
import com.ProjectManagerBackend.models.Discussion;
import com.ProjectManagerBackend.models.Project;
import com.ProjectManagerBackend.models.User;
import com.ProjectManagerBackend.repositories.ProjectRepository;
import com.ProjectManagerBackend.services.interfaces.DiscussionService;
import com.ProjectManagerBackend.services.interfaces.ProjectService;
import com.ProjectManagerBackend.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private DiscussionService discussionService;

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public Project createProject(ProjectDTO projectModel, User user) throws Exception {

        Project createdProject = projectMapper.convertProjectDTOToProject(projectModel);
        createdProject.setCreator(user);
        createdProject.getTeam().add(user);

        Project saveProject = projectRepo.save(createdProject);

        Discussion discussion = new Discussion();
        discussion.setProject(saveProject);

        Discussion projectdiscussion = discussionService.createDiscussion(discussion);
        saveProject.setDiscussion(projectdiscussion);

        return saveProject;
    }

    @Override
    public List<Project> getProjectsByTeam(String email, DevelopmentScope developmentScope, String tag) throws Exception {

        List<Project> projects = projectRepo.findAll();

        if (email != null) {
            User user = userService.findUserByEmail(email);
            projects = projectRepo.findByTeamContainingOrCreator(user, user);
        }
        if (developmentScope != null) {
            projects = projects.stream().filter(project -> project.getDevelopmentScope().equals(developmentScope))
                    .collect(Collectors.toList());
        }
        if (tag != null) {
            projects = projects.stream().filter(project -> project.getTags().contains(tag))
                    .collect((Collectors.toList()));
        }

        return projects;
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {

        Optional<Project> optionalProject = projectRepo.findById(projectId);

        if (optionalProject.isEmpty()) {
            throw new Exception(String.format(ExceptionConstants.ID_NOT_FOUND, "Project", projectId));
        }

        return optionalProject.get();
    }

    @Override
    public void deleteProject(Long projectId, User user) throws Exception {

        checkTeamMembership(projectId, user, String.format(ExceptionConstants.UNAUTHORIZED_ACTION, "delete projects"));

        getProjectById((projectId));
        projectRepo.deleteById(projectId);
    }

    @Override
    public List<User> getProjectTeam(Long projectId) throws Exception {
        return projectRepo.findTeamById(projectId);
    }

    @Override
    public Project updateProject(User user, ProjectDTO updatedProjectModel, Long projectId) throws Exception {

        checkTeamMembership(projectId, user, String.format(ExceptionConstants.UNAUTHORIZED_ACTION, "make changes"));

        Project project = getProjectById(projectId);

        if (updatedProjectModel.getName() != null) {
            project.setName(updatedProjectModel.getName());
        }
        if (updatedProjectModel.getDescription() != null) {
            project.setDescription(updatedProjectModel.getDescription());
        }
        if (updatedProjectModel.getDevelopmentScope() != null) {
            project.setDevelopmentScope(updatedProjectModel.getDevelopmentScope());
        }
        if (!updatedProjectModel.getTags().isEmpty()) {
            project.setTags(updatedProjectModel.getTags());
        }

        return projectRepo.save(project);
    }

    @Override
    public void checkTeamMembership(Long projectId, User user, String errorMsg) throws Exception {
        if (!getProjectTeam(projectId).contains(user)) {
            throw new Exception(errorMsg);
        }
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {

        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);

        if (!project.getTeam().contains(user)) {
            project.getDiscussion().getParticipants().add(user);
            project.getTeam().add(user);
        }

        projectRepo.save(project);
    }

    @Override
    public void removeUserFromProject(Long projectId, User deleter, Long userId) throws Exception {

        User userToDelete = userService.findUserById(userId);
        Project project = getProjectById(projectId);

        if (project.getCreator() == userToDelete)
            throw new Exception(ExceptionConstants.PROJECT_CREATOR_REMOVAL_ERROR);

        else if (!project.getTeam().contains(userToDelete))
            throw new Exception(ExceptionConstants.USER_NOT_PART_OF_PROJECT_TEAM_ERROR);

        project.getDiscussion().getParticipants().remove(userToDelete);
        project.getTeam().remove(userToDelete);

        projectRepo.save(project);
    }

    @Override
    public Discussion getDiscussionByProjectId(Long projectId) throws Exception {
        Project project = getProjectById(projectId);
        return project.getDiscussion();
    }

    @Override
    public List<Project> searchProjects(String keyword, User user) throws Exception {
        String partialName = "%" + keyword + "%";
        return projectRepo.findByNameContainingAndTeamContains(keyword, user);
    }
}
