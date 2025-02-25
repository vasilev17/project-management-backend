package com.ProjectManagerBackend.services.interfaces;

import com.ProjectManagerBackend.common.enums.Progress;
import com.ProjectManagerBackend.dtos.TicketDTO;
import com.ProjectManagerBackend.models.Project;
import com.ProjectManagerBackend.models.Ticket;
import com.ProjectManagerBackend.models.User;

import java.util.List;

public interface TicketService {

    Ticket getTicketById(Long ticketId) throws Exception;

    List<Ticket> getTicketsByProjectId(Long projectId) throws Exception;

    Ticket createTicket(TicketDTO ticket, Long projectId, User user) throws Exception;

    void deleteTicket(Long ticketId, User user) throws Exception;

    Ticket assignUserToTicket(Long ticketId, User assigner, Long userId) throws Exception;

    Ticket updateProgress(Long ticketId, User user, Progress progress) throws Exception;

    Project getProjectByTicketId(Long ticketId) throws Exception;
}
