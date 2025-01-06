package com.ProjectManagerBackend.services.interfaces;

import com.ProjectManagerBackend.models.Comment;
import com.ProjectManagerBackend.models.Ticket;
import com.ProjectManagerBackend.models.User;

import java.util.List;

public interface CommentService {

    Comment createComment(Long ticketId, User user, String comment) throws Exception;

    List<Comment> findCommentByTicketId(Long ticketId);

    void deleteComment(Long commentId, User user) throws Exception;

    Ticket getTicketByCommentId(Long commentId) throws Exception;
}
