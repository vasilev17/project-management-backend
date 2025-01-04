package com.ProjectManagerBackend.services.interfaces;

import com.ProjectManagerBackend.models.CollaborationRequest;
import com.ProjectManagerBackend.models.User;

public interface CollaborationService {

    public String sendCollaborationRequest (User creator, Long projectId) throws Exception;

    public CollaborationRequest acceptCollaborationRequest(String token, Long userId) throws Exception;

}
