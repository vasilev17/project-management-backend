package com.ProjectManagerBackend.repositories;

import com.ProjectManagerBackend.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
