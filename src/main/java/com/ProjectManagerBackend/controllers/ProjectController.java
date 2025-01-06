package com.ProjectManagerBackend.controllers;

import com.ProjectManagerBackend.common.enums.DevelopmentScope;
import com.ProjectManagerBackend.dtos.ProjectDTO;
import com.ProjectManagerBackend.mappers.CollaborationMapper;
import com.ProjectManagerBackend.mappers.ProjectMapper;
import com.ProjectManagerBackend.models.Project;
import com.ProjectManagerBackend.models.CollaborationRequest;
import com.ProjectManagerBackend.models.User;
import com.ProjectManagerBackend.services.interfaces.ProjectService;
import com.ProjectManagerBackend.services.interfaces.CollaborationService;
import com.ProjectManagerBackend.services.interfaces.UserService;
import com.ProjectManagerBackend.viewmodels.CollaborationResponseViewModel;
import com.ProjectManagerBackend.viewmodels.CollaborationInvitationViewModel;
import com.ProjectManagerBackend.viewmodels.ProjectViewModel;
import com.ProjectManagerBackend.viewmodels.StatusMessageViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private CollaborationService collaborationService;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private CollaborationMapper collaborationMapper;

    @GetMapping
    public ResponseEntity<List<ProjectViewModel>> getFilteredProjects(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) DevelopmentScope developmentScope,
            @RequestParam(required = false) String tag
    ) throws Exception {
        List<Project> projects = projectService.getProjectsByTeam(email, developmentScope, tag);
        List<ProjectViewModel> viewModels = projectMapper.convertProjectsToProjectViewModels(projects);
        return new ResponseEntity<>(viewModels, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(
            @PathVariable Long projectId
    ) throws Exception {
        Project project = projectService.getProjectById(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProjectViewModel> createProject(
            @RequestHeader("Authorization") String jwt,
            @RequestBody ProjectDTO projectModel
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project createdproject = projectService.createProject(projectModel, user);
        ProjectViewModel viewModel = projectMapper.convertProjectToProjectViewModel(createdproject);
        return new ResponseEntity<>(viewModel, HttpStatus.OK);
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<ProjectViewModel> updateProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt,
            @RequestBody ProjectDTO projectModel
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project updatedproject = projectService.updateProject(user, projectModel, projectId);
        ProjectViewModel viewModel = projectMapper.convertProjectToProjectViewModel(updatedproject);
        return new ResponseEntity<>(viewModel, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<StatusMessageViewModel> deleteProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt

    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(projectId, user);
        StatusMessageViewModel res = new StatusMessageViewModel("Project deleted successfully!");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProjectViewModel>> searchPersonalProjects(
            @RequestParam String keyword,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.searchProjects(keyword, user);
        List<ProjectViewModel> viewModels = projectMapper.convertProjectsToProjectViewModels(projects);
        return new ResponseEntity<>(viewModels, HttpStatus.OK);
    }

    // ! Getting discussion messages logic is in MessageController !
/*
    @GetMapping("/{projectId}/discussion")
    public ResponseEntity<Discussion> getDiscussionByProjectId(
            @PathVariable Long projectId
    ) throws Exception {
        Discussion discussion = projectService.getDiscussionByProjectId(projectId);
        return new ResponseEntity<>(discussion, HttpStatus.OK);
    }
*/

    @PostMapping("/request/{projectId}")
    public ResponseEntity<CollaborationInvitationViewModel> sendCollaborationRequest(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        String collabToken = collaborationService.sendCollaborationRequest(user, projectId);

        CollaborationInvitationViewModel res = new CollaborationInvitationViewModel();
        res.setCollabToken(collabToken);
        res.setMessage("Collaboration token generated successfully!");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/accept_request")
    public ResponseEntity<CollaborationResponseViewModel> acceptCollaborationRequest(
            @RequestParam String token,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        CollaborationRequest request = collaborationService.acceptCollaborationRequest(token, user.getId());
        projectService.addUserToProject(request.getProjectId(), user.getId());

        CollaborationResponseViewModel res = collaborationMapper.convertCollaborationRequestToCollaborationResponseViewModel(request);
        res.setMessage("Collaboration request accepted successfully!");

        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{projectId}/team/{userId}")
    public ResponseEntity<StatusMessageViewModel> removeUserFromProjectTeam(
            @PathVariable Long projectId,
            @PathVariable Long userId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        projectService.removeUserFromProject(projectId, user, userId);

        StatusMessageViewModel res = new StatusMessageViewModel("User removed from project team successfully!");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}

