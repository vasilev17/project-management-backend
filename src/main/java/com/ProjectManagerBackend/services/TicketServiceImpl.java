package com.ProjectManagerBackend.services;

import com.ProjectManagerBackend.dtos.TicketDTO;
import com.ProjectManagerBackend.models.Ticket;
import com.ProjectManagerBackend.repositories.TicketRepository;
import com.ProjectManagerBackend.services.interfaces.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public Optional<Ticket> getTicketById(Long ticketId) throws Exception {

        Optional<Ticket> ticket = ticketRepository.findById(ticketId);
        if(ticket.isPresent())
        {
            return ticket;
        }
        throw new Exception("No tickets found with ticketId" + ticketId);
    }

    @Override
    public List<Ticket> getTicketByProjectId(Long projectId) throws Exception {
        return List.of();
    }

    @Override
    public Ticket createTicket(TicketDTO ticket, Long userid) throws Exception {
        return null;
    }

    @Override
    public String deleteTicket(Long ticketId, Long userId) throws Exception {
        return "";
    }

    @Override
    public Ticket addUserToTicket(Long ticketId, Long userId) throws Exception {
        return null;
    }

    @Override
    public Ticket updateStatus(Long ticketId, Long userId) throws Exception {
        return null;
    }
}
