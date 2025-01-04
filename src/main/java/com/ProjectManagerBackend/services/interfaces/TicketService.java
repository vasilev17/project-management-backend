package com.ProjectManagerBackend.services.interfaces;

import com.ProjectManagerBackend.dtos.TicketDTO;
import com.ProjectManagerBackend.models.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketService {

    Optional<Ticket> getTicketById(Long ticketId) throws Exception;

    List<Ticket> getTicketByProjectId(Long projectId) throws Exception;

    Ticket createTicket(TicketDTO ticket, Long userid) throws  Exception;

    String deleteTicket(Long ticketId, Long userId) throws Exception;

    Ticket addUserToTicket(Long ticketId, Long userId) throws Exception;

    Ticket updateStatus(Long ticketId, Long userId) throws Exception;
}
