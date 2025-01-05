package com.ProjectManagerBackend.repositories;

import com.ProjectManagerBackend.models.Project;
import com.ProjectManagerBackend.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByProjectId(Long projectId);

    @Query("SELECT t.project FROM Ticket t WHERE t.id = :ticketId")
    Project findProjectByTicketId(Long ticketId);

}
