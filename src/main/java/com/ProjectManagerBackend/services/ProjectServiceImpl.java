package com.ProjectManagerBackend.services;

import com.ProjectManagerBackend.models.Discussion;
import com.ProjectManagerBackend.models.Project;
import com.ProjectManagerBackend.models.User;
import com.ProjectManagerBackend.repositories.ProjectRepository;
import com.ProjectManagerBackend.services.interfaces.DiscussionService;
import com.ProjectManagerBackend.services.interfaces.ProjectService;
import com.ProjectManagerBackend.services.interfaces.UserFinder;
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
    private UserFinder userFinder;

    @Autowired
    private DiscussionService discussionService;

    @Override
    public Project createProject(Project project, User user) throws Exception {
        Project createdProject = new Project();

        createdProject.setCreator(user);
        createdProject.setTags(project.getTags());
        createdProject.setName(project.getName());
        createdProject.setDevelopmentScope(project.getDevelopmentScope());
        createdProject.setDescription(project.getDescription());
        createdProject.getTeam().add(user);

        Project saveProject = projectRepo.save(createdProject);

        Discussion discussion = new Discussion();
        discussion.setProject(saveProject);

        Discussion projectdiscussion = discussionService.createDiscussion(discussion);
        saveProject.setDiscussion(projectdiscussion);

        return saveProject;
    }

    @Override
    public List<Project> getProjectByTeam(String email, String category, String tag) throws Exception {

        List<Project> projects = projectRepo.findAll();

        if (email != null) {
            User user = userFinder.findUserByEmail(email);
            projects = projectRepo.findByTeamContainingOrCreator(user, user);
        }
        if (category != null) {
            projects = projects.stream().filter(project -> project.getDevelopmentScope().equals(category))
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
            throw new Exception("Project not found!");
        }

        return optionalProject.get();
    }

    @Override
    public void deleteProject(Long projectId, User user) throws Exception {

        checkTeamMembership(projectId, user, "User not a project team member! Only team members can delete projects!");

        getProjectById((projectId));
        projectRepo.deleteById(projectId);
    }

    @Override
    public List<User> getProjectTeam(Long projectId) throws Exception {
        return projectRepo.findTeamById(projectId);
    }

    @Override
    public Project updateProject(User user, Project updatedProject, Long projectId) throws Exception {

        checkTeamMembership(projectId, user, "User not a project team member! Only team members can make changes!");

        Project project = getProjectById(projectId);

        if (updatedProject.getName() != null) {
            project.setName(updatedProject.getName());
        }
        if (updatedProject.getDescription() != null) {
            project.setDescription(updatedProject.getDescription());
        }
        if (updatedProject.getDevelopmentScope() != null) {
            project.setDevelopmentScope(updatedProject.getDevelopmentScope());
        }
        if (!updatedProject.getTags().isEmpty()) {
            project.setTags(updatedProject.getTags());
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
        User user = userFinder.findUserById(userId);

        if (!project.getTeam().contains(user)) {
            project.getDiscussion().getParticipants().add(user);
            project.getTeam().add(user);
        }

        projectRepo.save(project);
    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) throws Exception {

        Project project = getProjectById(projectId);
        User user = userFinder.findUserById(userId);

        if (project.getTeam().contains(user)) {
            project.getDiscussion().getParticipants().remove(user);
            project.getTeam().remove(user);
        }

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
