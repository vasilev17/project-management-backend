package com.ProjectManagerBackend.controllers;

import com.ProjectManagerBackend.common.constants.ApiMessageConstants;
import com.ProjectManagerBackend.common.constants.StatusMessageConstants;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@Tag(name = ApiMessageConstants.PROJECT_TAG_NAME, description = ApiMessageConstants.PROJECT_TAG_DESCRIPTION)
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
    @Operation(summary = ApiMessageConstants.GET_FILTERED_PROJECTS, description = ApiMessageConstants.GET_FILTERED_PROJECTS_DESCRIPTION)
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
    @Operation(summary = ApiMessageConstants.GET_PROJECT)
    public ResponseEntity<Project> getProjectById(
            @PathVariable Long projectId
    ) throws Exception {
        Project project = projectService.getProjectById(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }


    @PostMapping
    @Operation(summary = ApiMessageConstants.CREATE_PROJECT, security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<ProjectViewModel> createProject(
            @Parameter(description = ApiMessageConstants.AUTHORIZATION_HEADER_MESSAGE)
            @RequestHeader(value = "Authorization", required = false) String jwt,
            @RequestBody ProjectDTO projectModel
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project createdproject = projectService.createProject(projectModel, user);
        ProjectViewModel viewModel = projectMapper.convertProjectToProjectViewModel(createdproject);
        return new ResponseEntity<>(viewModel, HttpStatus.OK);
    }

    @PatchMapping("/{projectId}")
    @Operation(summary = ApiMessageConstants.UPDATE_PROJECT, security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<ProjectViewModel> updateProject(
            @Parameter(description = ApiMessageConstants.AUTHORIZATION_HEADER_MESSAGE)
            @RequestHeader(value = "Authorization", required = false) String jwt,
            @PathVariable Long projectId,
            @RequestBody ProjectDTO projectModel
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project updatedproject = projectService.updateProject(user, projectModel, projectId);
        ProjectViewModel viewModel = projectMapper.convertProjectToProjectViewModel(updatedproject);
        return new ResponseEntity<>(viewModel, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    @Operation(summary = ApiMessageConstants.DELETE_PROJECT, security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<StatusMessageViewModel> deleteProject(
            @Parameter(description = ApiMessageConstants.AUTHORIZATION_HEADER_MESSAGE)
            @RequestHeader(value = "Authorization", required = false) String jwt,
            @PathVariable Long projectId
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(projectId, user);
        StatusMessageViewModel res = new StatusMessageViewModel(String.format(StatusMessageConstants.DELETION_SUCCESSFUL, "Project"));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/search")
    @Operation(summary = ApiMessageConstants.SEARCH_PERSONAL_PROJECTS,
            description = ApiMessageConstants.SEARCH_PERSONAL_PROJECTS_DESCRIPTION,
            security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<List<ProjectViewModel>> searchPersonalProjects(
            @Parameter(description = ApiMessageConstants.AUTHORIZATION_HEADER_MESSAGE)
            @RequestHeader(value = "Authorization", required = false) String jwt,
            @RequestParam String keyword
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
    @Operation(summary = ApiMessageConstants.GENERATE_COLLABORATION_CODE, security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<CollaborationInvitationViewModel> generateCollaborationCode(
            @Parameter(description = ApiMessageConstants.AUTHORIZATION_HEADER_MESSAGE)
            @RequestHeader(value = "Authorization", required = false) String jwt,
            @PathVariable Long projectId
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        String collabToken = collaborationService.generateCollaborationCode(user, projectId);

        CollaborationInvitationViewModel res = new CollaborationInvitationViewModel();
        res.setCollabToken(collabToken);
        res.setMessage(StatusMessageConstants.COLLABORATION_TOKEN_GENERATION_SUCCESSFUL);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/accept_request")
    @Operation(summary = ApiMessageConstants.ACCEPT_COLLABORATION, security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<CollaborationResponseViewModel> acceptCollaboration(
            @Parameter(description = ApiMessageConstants.AUTHORIZATION_HEADER_MESSAGE)
            @RequestHeader(value = "Authorization", required = false) String jwt,
            @RequestParam String token
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        CollaborationRequest request = collaborationService.acceptCollaboration(token, user.getId());
        projectService.addUserToProject(request.getProjectId(), user.getId());

        CollaborationResponseViewModel res = collaborationMapper.convertCollaborationRequestToCollaborationResponseViewModel(request);
        res.setMessage(StatusMessageConstants.COLLABORATION_TOKEN_ACCEPTION_SUCCESSFUL);

        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{projectId}/team/{userId}")
    @Operation(summary = ApiMessageConstants.REMOVE_USER_FROM_TEAM, security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<StatusMessageViewModel> removeUserFromProjectTeam(
            @Parameter(description = ApiMessageConstants.AUTHORIZATION_HEADER_MESSAGE)
            @RequestHeader(value = "Authorization", required = false) String jwt,
            @PathVariable Long projectId,
            @PathVariable Long userId

    ) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        projectService.removeUserFromProject(projectId, user, userId);

        StatusMessageViewModel res = new StatusMessageViewModel(StatusMessageConstants.USER_REMOVAL_SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}

