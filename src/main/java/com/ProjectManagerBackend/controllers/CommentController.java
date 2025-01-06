package com.ProjectManagerBackend.controllers;

import com.ProjectManagerBackend.dtos.CommentDTO;
import com.ProjectManagerBackend.mappers.CommentMapper;
import com.ProjectManagerBackend.models.Comment;
import com.ProjectManagerBackend.models.User;
import com.ProjectManagerBackend.services.interfaces.CommentService;
import com.ProjectManagerBackend.services.interfaces.UserService;
import com.ProjectManagerBackend.viewmodels.CommentViewModel;
import com.ProjectManagerBackend.viewmodels.StatusMessageViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentMapper commentMapper;

    @PostMapping("/{ticketId}")
    public ResponseEntity<CommentViewModel> createComment(
            @RequestBody CommentDTO comment,
            @PathVariable Long ticketId,
            @RequestHeader("Authorization") String jwt)
            throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        Comment createdComment = commentService.createComment(ticketId, user, comment.getBody());

        CommentViewModel viewModel = commentMapper.convertCommentToCommentViewModel(createdComment);

        return new ResponseEntity<>(viewModel, HttpStatus.CREATED);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<List<CommentViewModel>> getCommentsByTicketId(@PathVariable Long ticketId) {

        List<Comment> comments = commentService.findCommentByTicketId(ticketId);

        List<CommentViewModel> commentViewModels = commentMapper.convertCommentsToCommentViewModels(comments);

        return new ResponseEntity<>(commentViewModels, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<StatusMessageViewModel> deleteComment(@PathVariable Long commentId,
                                                                @RequestHeader("Authorization") String jwt)
            throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        commentService.deleteComment(commentId, user);

        StatusMessageViewModel res = new StatusMessageViewModel("Comment deleted successfully!");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
