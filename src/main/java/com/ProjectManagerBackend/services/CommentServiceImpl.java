package com.ProjectManagerBackend.services;

import com.ProjectManagerBackend.common.constants.ExceptionConstants;
import com.ProjectManagerBackend.models.Comment;
import com.ProjectManagerBackend.models.Project;
import com.ProjectManagerBackend.models.Ticket;
import com.ProjectManagerBackend.models.User;
import com.ProjectManagerBackend.repositories.CommentRepository;
import com.ProjectManagerBackend.repositories.TicketRepository;
import com.ProjectManagerBackend.repositories.UserRepository;
import com.ProjectManagerBackend.services.interfaces.CommentService;
import com.ProjectManagerBackend.services.interfaces.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TicketRepository ticketRepo;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private CommentRepository commentRepo;

    @Override
    public Comment createComment(Long ticketId, User user, String content) throws Exception {

        Project project = ticketRepo.findProjectByTicketId(ticketId);

        if (project == null) {
            throw new Exception(ExceptionConstants.PROJECT_WITH_TICKET_ID_NOT_FOUND);
        }

        projectService.checkTeamMembership(project.getId(), user, String.format(ExceptionConstants.UNAUTHORIZED_ACTION, "send ticket comments"));

        Optional<Ticket> ticketOptional = ticketRepo.findById(ticketId);

        if (ticketOptional.isEmpty())
            throw new Exception(String.format(ExceptionConstants.ID_NOT_FOUND, "Ticket", ticketId));

        Ticket ticket = ticketOptional.get();

        Comment comment = new Comment();

        comment.setAuthor(user);
        comment.setTicket(ticket);
        comment.setBody(content);
        comment.setCreationDate(LocalDateTime.now());

        Comment savedComment = commentRepo.save(comment);
        ticket.getComments().add(savedComment);

        return savedComment;
    }

    @Override
    public List<Comment> findCommentByTicketId(Long ticketId) {
        return commentRepo.findByTicketId(ticketId);
    }

    @Override
    public Ticket getTicketByCommentId(Long commentId) throws Exception {
        return commentRepo.findTicketByCommentId(commentId);
    }

    @Override
    public void deleteComment(Long commentId, User user) throws Exception {

        Ticket ticket = commentRepo.findTicketByCommentId(commentId);

        if (ticket == null) {
            throw new Exception(ExceptionConstants.TICKET_WITH_COMMENT_ID_NOT_FOUND);
        }

        Optional<Comment> commentOptional = commentRepo.findById(commentId);

        if (commentOptional.isEmpty())
            throw new Exception(String.format(ExceptionConstants.ID_NOT_FOUND, "Comment", commentId));

        Comment comment = commentOptional.get();

        if (comment.getAuthor().equals(user))
            commentRepo.delete(comment);
        else
            throw new Exception(String.format(ExceptionConstants.UNAUTHORIZED_AUTHOR_ONLY_ACTION, "comment"));

    }

}
