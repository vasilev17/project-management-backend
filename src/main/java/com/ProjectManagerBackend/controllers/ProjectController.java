package com.ProjectManagerBackend.controllers;


import com.ProjectManagerBackend.models.Discussion;
import com.ProjectManagerBackend.models.Project;
import com.ProjectManagerBackend.models.CollaborationRequest;
import com.ProjectManagerBackend.models.User;
import com.ProjectManagerBackend.services.interfaces.ProjectService;
import com.ProjectManagerBackend.services.interfaces.CollaborationService;
import com.ProjectManagerBackend.services.interfaces.UserFinder;
import com.ProjectManagerBackend.viewmodels.CollaborationRequestViewModel;
import com.ProjectManagerBackend.viewmodels.CollaborationViewModel;
import com.ProjectManagerBackend.viewmodels.MessageViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private UserFinder userFinder;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private CollaborationService collaborationService;

    @GetMapping
    public ResponseEntity<List<Project>> getFilteredProjects(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tag
    ) throws Exception {
        List<Project> projects = projectService.getProjectByTeam(email, category, tag);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(
            @PathVariable Long projectId
    ) throws Exception {
        Project projects = projectService.getProjectById(projectId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception {
        User user = userFinder.findUserProfileByJwt(jwt);
        Project createdproject = projectService.createProject(project, user);
        return new ResponseEntity<>(createdproject, HttpStatus.OK);
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception {
        User user = userFinder.findUserProfileByJwt(jwt);
        Project updatedproject = projectService.updateProject(user, project, projectId);
        return new ResponseEntity<>(updatedproject, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageViewModel> deleteProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt

    ) throws Exception {
        User user = userFinder.findUserProfileByJwt(jwt);
        projectService.deleteProject(projectId, user);
        MessageViewModel res = new MessageViewModel("Project deleted successfully!");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchPersonalProjects(
            @RequestParam(required = false) String keyword,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userFinder.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.searchProjects(keyword, user);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectId}/discussion")
    public ResponseEntity<Discussion> getDiscussionByProjectId(
            @PathVariable Long projectId
    ) throws Exception {
        Discussion discussion = projectService.getDiscussionByProjectId(projectId);
        return new ResponseEntity<>(discussion, HttpStatus.OK);
    }

    @PostMapping("/request/{projectId}")
    public ResponseEntity<CollaborationViewModel> sendCollaborationRequest(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userFinder.findUserProfileByJwt(jwt);
        String collabToken = collaborationService.sendCollaborationRequest(user, projectId);
        CollaborationViewModel res = new CollaborationViewModel("Collaboration token generated successfully!", collabToken);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/accept_request")
    public ResponseEntity<CollaborationRequestViewModel> acceptCollaborationRequest(
            @RequestParam String token,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userFinder.findUserProfileByJwt(jwt);
        CollaborationRequest request = collaborationService.acceptCollaborationRequest(token, user.getId());
        projectService.addUserToProject(request.getProjectId(), user.getId());
        CollaborationRequestViewModel res = new CollaborationRequestViewModel("Collaboration request accepted successfully!", request.getProjectId(), request.getCreator());
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }


}

