package com.ProjectManagerBackend.controllers;

import com.ProjectManagerBackend.dtos.MessageDTO;
import com.ProjectManagerBackend.models.Discussion;
import com.ProjectManagerBackend.models.Message;
import com.ProjectManagerBackend.models.User;
import com.ProjectManagerBackend.services.interfaces.MessageService;
import com.ProjectManagerBackend.services.interfaces.ProjectService;
import com.ProjectManagerBackend.services.interfaces.UserFinder;
import com.ProjectManagerBackend.viewmodels.MessageViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private UserFinder userFinder;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/send/{projectId}")
    public ResponseEntity<MessageViewModel> sendMessage(@RequestBody MessageDTO message,
                                                        @PathVariable Long projectId,
                                                        @RequestHeader("Authorization") String jwt)
            throws Exception {

        User user = userFinder.findUserProfileByJwt(jwt);

        Message sentMessage = messageService.sendMessage(user,
                projectId, message.getBody());

        MessageViewModel messageViewModel = new MessageViewModel();
        messageViewModel.setId(sentMessage.getId());
        messageViewModel.setBody(sentMessage.getBody());
        messageViewModel.setAuthor(sentMessage.getAuthor());
        messageViewModel.setCreationDate(sentMessage.getCreationDate());


        return ResponseEntity.ok(messageViewModel);
    }

    @GetMapping("/discussion/{projectId}")
    public ResponseEntity<List<MessageViewModel>> getMessageDiscussionByProjectId(@PathVariable Long projectId)
            throws Exception {

        List<Message> messages = messageService.getMessagesByProjectId(projectId);

        List<MessageViewModel> messageViewModels = new ArrayList<>();

        for (Message message : messages) {

            MessageViewModel viewModel = new MessageViewModel();

            viewModel.setId(message.getId());
            viewModel.setBody(message.getBody());
            viewModel.setAuthor(message.getAuthor());
            viewModel.setCreationDate(message.getCreationDate());

            messageViewModels.add(viewModel);
        }

        return ResponseEntity.ok(messageViewModels);
    }
}
