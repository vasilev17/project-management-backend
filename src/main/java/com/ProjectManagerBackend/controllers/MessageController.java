package com.ProjectManagerBackend.controllers;

import com.ProjectManagerBackend.common.constants.ApiMessageConstants;
import com.ProjectManagerBackend.dtos.MessageDTO;
import com.ProjectManagerBackend.mappers.MessageMapper;
import com.ProjectManagerBackend.models.Message;
import com.ProjectManagerBackend.models.User;
import com.ProjectManagerBackend.services.interfaces.MessageService;
import com.ProjectManagerBackend.services.interfaces.UserService;
import com.ProjectManagerBackend.viewmodels.MessageViewModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@Tag(name = ApiMessageConstants.Message_TAG_NAME, description = ApiMessageConstants.Message_TAG_DESCRIPTION)
public class MessageController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageMapper messageMapper;

    @PostMapping("/send/{projectId}")
    @Operation(summary = ApiMessageConstants.SEND_MESSAGE, security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<MessageViewModel> sendMessage(@RequestBody MessageDTO message,
                                                        @PathVariable Long projectId,
                                                        @Parameter(description = ApiMessageConstants.AUTHORIZATION_HEADER_MESSAGE)
                                                        @RequestHeader(value = "Authorization", required = false) String jwt)
            throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        Message sentMessage = messageService.sendMessage(user,
                projectId, message.getBody());

        MessageViewModel messageViewModel = messageMapper.convertMessageToMessageViewModel(sentMessage);

        return ResponseEntity.ok(messageViewModel);
    }

    @GetMapping("/discussion/{projectId}")
    @Operation(summary = ApiMessageConstants.GET_PROJECT_MESSAGES)
    public ResponseEntity<List<MessageViewModel>> getMessageDiscussionByProjectId(@PathVariable Long projectId)
            throws Exception {

        List<Message> messages = messageService.getMessagesByProjectId(projectId);

        List<MessageViewModel> messageViewModels = messageMapper.convertMessagesToMessageViewModels(messages);

        return ResponseEntity.ok(messageViewModels);
    }
}
