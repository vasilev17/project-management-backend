package com.ProjectManagerBackend.controllers;

import com.ProjectManagerBackend.common.constants.ApiMessageConstants;
import com.ProjectManagerBackend.common.constants.StatusMessageConstants;
import com.ProjectManagerBackend.dtos.CommentDTO;
import com.ProjectManagerBackend.mappers.CommentMapper;
import com.ProjectManagerBackend.models.Comment;
import com.ProjectManagerBackend.models.User;
import com.ProjectManagerBackend.services.interfaces.CommentService;
import com.ProjectManagerBackend.services.interfaces.UserService;
import com.ProjectManagerBackend.viewmodels.CommentViewModel;
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
@RequestMapping("/comments")
@Tag(name = ApiMessageConstants.COMMENT_TAG_NAME, description = ApiMessageConstants.COMMENT_TAG_DESCRIPTION)
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentMapper commentMapper;

    @PostMapping("/{ticketId}")
    @Operation(summary = ApiMessageConstants.SEND_COMMENT, security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<CommentViewModel> createComment(
            @RequestBody CommentDTO comment,
            @PathVariable Long ticketId,
            @Parameter(description = ApiMessageConstants.AUTHORIZATION_HEADER_MESSAGE)
            @RequestHeader(value = "Authorization", required = false) String jwt)
            throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        Comment createdComment = commentService.createComment(ticketId, user, comment.getBody());

        CommentViewModel viewModel = commentMapper.convertCommentToCommentViewModel(createdComment);

        return new ResponseEntity<>(viewModel, HttpStatus.CREATED);
    }

    @GetMapping("/{ticketId}")
    @Operation(summary = ApiMessageConstants.GET_COMMENT_BY_TICKET_ID)
    public ResponseEntity<List<CommentViewModel>> getCommentsByTicketId(@PathVariable Long ticketId) {

        List<Comment> comments = commentService.findCommentByTicketId(ticketId);

        List<CommentViewModel> commentViewModels = commentMapper.convertCommentsToCommentViewModels(comments);

        return new ResponseEntity<>(commentViewModels, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = ApiMessageConstants.DELETE_COMMENT, security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<StatusMessageViewModel> deleteComment(@PathVariable Long commentId,
                                                                @Parameter(description = ApiMessageConstants.AUTHORIZATION_HEADER_MESSAGE)
                                                                @RequestHeader(value = "Authorization", required = false) String jwt)
            throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        commentService.deleteComment(commentId, user);

        StatusMessageViewModel res = new StatusMessageViewModel(String.format(StatusMessageConstants.DELETION_SUCCESSFUL, "Comment"));

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
