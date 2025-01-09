package com.ProjectManagerBackend.services;

import com.ProjectManagerBackend.common.enums.Progress;
import com.ProjectManagerBackend.common.constants.ExceptionConstants;
import com.ProjectManagerBackend.dtos.TicketDTO;
import com.ProjectManagerBackend.mappers.TicketMapper;
import com.ProjectManagerBackend.models.Project;
import com.ProjectManagerBackend.models.Ticket;
import com.ProjectManagerBackend.models.User;
import com.ProjectManagerBackend.repositories.TicketRepository;
import com.ProjectManagerBackend.services.interfaces.ProjectService;
import com.ProjectManagerBackend.services.interfaces.TicketService;
import com.ProjectManagerBackend.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepo;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketMapper ticketMapper;

    @Override
    public Ticket getTicketById(Long ticketId) throws Exception {

        Optional<Ticket> ticket = ticketRepo.findById(ticketId);
        if (ticket.isPresent()) {
            return ticket.get();
        }
        throw new Exception(String.format(ExceptionConstants.ID_NOT_FOUND, "Ticket", ticketId));
    }

    @Override
    public List<Ticket> getTicketsByProjectId(Long projectId) throws Exception {
        return ticketRepo.findByProjectId(projectId);
    }

    @Override
    public Ticket createTicket(TicketDTO ticketModel, Long projectId, User user) throws Exception {

        Project project = projectService.getProjectById(projectId);

        projectService.checkTeamMembership(projectId, user, String.format(ExceptionConstants.UNAUTHORIZED_ACTION, "create tickets"));

        if (ticketModel.getName() == null || ticketModel.getName().isEmpty())
            throw new Exception(String.format(ExceptionConstants.FIELD_REQUIRED, "Name"));

        Ticket ticket = ticketMapper.convertTicketDTOToTicket(ticketModel);

        ticket.setProject(project);
        ticket.setAuthor(user);
        ticket.setProjectID(projectId);

        return ticketRepo.save(ticket);
    }


    @Override
    public void deleteTicket(Long ticketId, User user) throws Exception {

        Project project = ticketRepo.findProjectByTicketId(ticketId);

        if (project == null) {
            throw new Exception(ExceptionConstants.PROJECT_WITH_TICKET_ID_NOT_FOUND);
        }

        projectService.checkTeamMembership(project.getId(), user, String.format(ExceptionConstants.UNAUTHORIZED_ACTION, "delete tickets"));

        //Check if ticket exists
        getTicketById(ticketId);

        ticketRepo.deleteById(ticketId);
    }

    @Override
    public Ticket assignUserToTicket(Long ticketId, User assigner, Long userId) throws Exception {

        Project project = ticketRepo.findProjectByTicketId(ticketId);

        if (project == null) {
            throw new Exception(ExceptionConstants.PROJECT_WITH_TICKET_ID_NOT_FOUND);
        }

        projectService.checkTeamMembership(project.getId(), assigner, String.format(ExceptionConstants.UNAUTHORIZED_ACTION, "assign tickets"));

        User user = userService.findUserById(userId);
        Ticket ticket = getTicketById(ticketId);
        ticket.setAssignee(user);

        return ticketRepo.save(ticket);
    }

    @Override
    public Project getProjectByTicketId(Long ticketId) throws Exception {
        return ticketRepo.findProjectByTicketId(ticketId);
    }

    @Override
    public Ticket updateProgress(Long ticketId, User user, Progress progress) throws Exception {

        Project project = ticketRepo.findProjectByTicketId(ticketId);

        if (project == null) {
            throw new Exception(ExceptionConstants.PROJECT_WITH_TICKET_ID_NOT_FOUND);
        }

        projectService.checkTeamMembership(project.getId(), user, String.format(ExceptionConstants.UNAUTHORIZED_ACTION, "update ticket progress"));

        Ticket ticket = getTicketById(ticketId);
        ticket.setProgress(progress);

        return ticketRepo.save(ticket);
    }
}
