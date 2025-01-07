package com.ProjectManagerBackend.services.interfaces;

import com.ProjectManagerBackend.models.CollaborationRequest;
import com.ProjectManagerBackend.models.User;

public interface CollaborationService {

    public String generateCollaborationCode (User creator, Long projectId) throws Exception;

    public CollaborationRequest acceptCollaboration(String token, Long userId) throws Exception;

}
