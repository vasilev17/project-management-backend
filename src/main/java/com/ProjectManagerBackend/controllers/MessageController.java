package com.ProjectManagerBackend.controllers;

import com.ProjectManagerBackend.dtos.MessageDTO;
import com.ProjectManagerBackend.mappers.MessageMapper;
import com.ProjectManagerBackend.models.Message;
import com.ProjectManagerBackend.models.User;
import com.ProjectManagerBackend.services.interfaces.MessageService;
import com.ProjectManagerBackend.services.interfaces.UserService;
import com.ProjectManagerBackend.viewmodels.MessageViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageMapper messageMapper;

    @PostMapping("/send/{projectId}")
    public ResponseEntity<MessageViewModel> sendMessage(@RequestBody MessageDTO message,
                                                        @PathVariable Long projectId,
                                                        @RequestHeader("Authorization") String jwt)
            throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        Message sentMessage = messageService.sendMessage(user,
                projectId, message.getBody());

        MessageViewModel messageViewModel = messageMapper.convertMessageToMessageViewModel(sentMessage);

        return ResponseEntity.ok(messageViewModel);
    }

    @GetMapping("/discussion/{projectId}")
    public ResponseEntity<List<MessageViewModel>> getMessageDiscussionByProjectId(@PathVariable Long projectId)
            throws Exception {

        List<Message> messages = messageService.getMessagesByProjectId(projectId);

        List<MessageViewModel> messageViewModels = messageMapper.convertMessagesToMessageViewModels(messages);

        return ResponseEntity.ok(messageViewModels);
    }
}
