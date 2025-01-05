package com.ProjectManagerBackend.controllers;


import com.ProjectManagerBackend.dtos.CommentDTO;
import com.ProjectManagerBackend.models.Comment;
import com.ProjectManagerBackend.models.Message;
import com.ProjectManagerBackend.models.User;
import com.ProjectManagerBackend.services.interfaces.CommentService;
import com.ProjectManagerBackend.services.interfaces.UserFinder;
import com.ProjectManagerBackend.viewmodels.CommentViewModel;
import com.ProjectManagerBackend.viewmodels.MessageViewModel;
import com.ProjectManagerBackend.viewmodels.StatusMessageViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CodeEmitter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserFinder userFinder;

    @PostMapping("/{ticketId}")
    public ResponseEntity<CommentViewModel> createComment(
            @RequestBody CommentDTO comment,
            @PathVariable Long ticketId,
            @RequestHeader("Authorization") String jwt)
            throws Exception {

        User user = userFinder.findUserProfileByJwt(jwt);

        Comment createdComment = commentService.createComment(ticketId, user, comment.getBody());

        CommentViewModel viewModel = new CommentViewModel();

        viewModel.setId(createdComment.getId());
        viewModel.setBody(createdComment.getBody());
        viewModel.setAuthor(createdComment.getAuthor());
        viewModel.setCreationDate(createdComment.getCreationDate());

        return new ResponseEntity<>(viewModel, HttpStatus.CREATED);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<List<CommentViewModel>> getCommentsByTicketId(@PathVariable Long ticketId) {

        List<Comment> comments = commentService.findCommentByTicketId(ticketId);

        List<CommentViewModel> commentViewModels = new ArrayList<>();

        for (Comment comment : comments) {

            CommentViewModel viewModel = new CommentViewModel();

            viewModel.setId(comment.getId());
            viewModel.setBody(comment.getBody());
            viewModel.setAuthor(comment.getAuthor());
            viewModel.setCreationDate(comment.getCreationDate());

            commentViewModels.add(viewModel);
        }

        return new ResponseEntity<>(commentViewModels, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<StatusMessageViewModel> deleteComment(@PathVariable Long commentId,
                                                                @RequestHeader("Authorization") String jwt)
            throws Exception {

        User user = userFinder.findUserProfileByJwt(jwt);
        commentService.deleteComment(commentId, user);
        StatusMessageViewModel res = new StatusMessageViewModel();
        res.setMessage("Comment deleted successfully!");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
