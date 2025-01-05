package com.ProjectManagerBackend.repositories;

import com.ProjectManagerBackend.models.Comment;
import com.ProjectManagerBackend.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTicketId(Long ticketId);

    @Query("SELECT c.ticket FROM Comment c WHERE c.id = :commentId")
    Ticket findTicketByCommentId(Long commentId);

}
