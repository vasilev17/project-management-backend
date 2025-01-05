package com.ProjectManagerBackend.services.interfaces;

import com.ProjectManagerBackend.models.Message;
import com.ProjectManagerBackend.models.User;

import java.util.List;

public interface MessageService {

    Message sendMessage(User sender, Long discussionId, String body) throws Exception;

    List<Message> getMessagesByProjectId(Long projectId) throws Exception;
}
