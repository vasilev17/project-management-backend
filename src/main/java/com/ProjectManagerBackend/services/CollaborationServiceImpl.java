package com.ProjectManagerBackend.services;


import com.ProjectManagerBackend.models.CollaborationRequest;
import com.ProjectManagerBackend.models.User;
import com.ProjectManagerBackend.repositories.CollaborationRequestRepository;
import com.ProjectManagerBackend.services.interfaces.CollaborationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CollaborationServiceImpl implements CollaborationService {

    @Autowired
    private CollaborationRequestRepository collabRequestRepo;

    @Autowired
    private ProjectServiceImpl projectService;

    @Override
    public String sendCollaborationRequest(User creator, Long projectId) throws Exception {

        //Check if project exists
        projectService.getProjectById(projectId);

        try {
            String requestToken = UUID.randomUUID().toString();

            CollaborationRequest request = new CollaborationRequest();
            request.setCreator(creator);
            request.setProjectId(projectId);
            request.setToken(requestToken);

            collabRequestRepo.save(request);

            return requestToken;

        } catch (Exception e) {
            throw new Exception("Error generating collaboration request!");
        }

    }

    @Override
    public CollaborationRequest acceptCollaborationRequest(String token, Long userId) throws Exception {

        CollaborationRequest request = collabRequestRepo.findByToken(token);

        if (request == null) {
            throw new Exception("Invalid request token!");
        }

        return request;
    }

}
