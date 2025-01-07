package com.ProjectManagerBackend.services;

import com.ProjectManagerBackend.common.constants.ExceptionConstants;
import com.ProjectManagerBackend.models.Discussion;
import com.ProjectManagerBackend.models.Message;
import com.ProjectManagerBackend.models.User;
import com.ProjectManagerBackend.repositories.MessageRepository;
import com.ProjectManagerBackend.repositories.UserRepository;
import com.ProjectManagerBackend.services.interfaces.MessageService;
import com.ProjectManagerBackend.services.interfaces.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Message sendMessage(User sender, Long projectId, String body) throws Exception {

        projectService.checkTeamMembership(projectId, sender, String.format(ExceptionConstants.UNAUTHORIZED_ACTION, "send discussion messages"));

        Discussion discussion = projectService.getProjectById(projectId).getDiscussion();

        if (discussion == null)
            throw new Exception(ExceptionConstants.DISCUSSION_WITH_PROJECT_ID_NOT_FOUND);

        Message message = new Message();
        message.setCreationDate(LocalDateTime.now());
        message.setBody(body);
        message.setDiscussion(discussion);
        message.setAuthor(sender);

        Message savedMessage = messageRepository.save(message);
        discussion.getMessages().add(savedMessage);

        return savedMessage;
    }

    @Override
    public List<Message> getMessagesByProjectId(Long projectId) throws Exception {

        Discussion discussion = projectService.getDiscussionByProjectId(projectId);
        List<Message> sortedDisc = messageRepository.findByDiscussionIdOrderByCreationDateAsc(discussion.getId());

        return sortedDisc;
    }
}
