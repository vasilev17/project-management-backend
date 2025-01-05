package com.ProjectManagerBackend.services;

import com.ProjectManagerBackend.dtos.TicketDTO;
import com.ProjectManagerBackend.models.Project;
import com.ProjectManagerBackend.models.Ticket;
import com.ProjectManagerBackend.models.User;
import com.ProjectManagerBackend.repositories.TicketRepository;
import com.ProjectManagerBackend.services.interfaces.ProjectService;
import com.ProjectManagerBackend.services.interfaces.TicketService;
import com.ProjectManagerBackend.services.interfaces.UserFinder;
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
    private UserFinder userFinder;

    @Override
    public Ticket getTicketById(Long ticketId) throws Exception {

        Optional<Ticket> ticket = ticketRepo.findById(ticketId);
        if (ticket.isPresent()) {
            return ticket.get();
        }
        throw new Exception("No ticket found with ticket Id " + ticketId);
    }

    @Override
    public List<Ticket> getTicketsByProjectId(Long projectId) throws Exception {
        return ticketRepo.findByProjectId(projectId);
    }

    @Override
    public Ticket createTicket(TicketDTO ticketModel, Long projectId, User user) throws Exception {

        projectService.checkTeamMembership(projectId, user, "User not a project team member! Only team members can create tickets!");

        Project project = projectService.getProjectById(projectId);

        Ticket ticket = new Ticket();
        ticket.setAuthor(user);
        ticket.setName(ticketModel.getName());
        ticket.setDescription(ticketModel.getDescription());
        ticket.setProgress(ticketModel.getProgress());
        ticket.setImportance(ticketModel.getImportance());
        ticket.setDeadline(ticketModel.getDeadline());
        ticket.setTags(ticketModel.getTags());

        ticket.setProject(project);

        return ticketRepo.save(ticket);
    }

    @Override
    public void deleteTicket(Long ticketId, User user) throws Exception {

        Project project = ticketRepo.findProjectByTicketId(ticketId);

        if (project == null) {
            throw new Exception("Project with such ticketId does not exist!");
        }

        projectService.checkTeamMembership(project.getId(), user, "User not a project team member! Only team members can delete tickets!");

        //Check if ticket exists
        getTicketById(ticketId);

        ticketRepo.deleteById(ticketId);
    }

    @Override
    public Ticket addUserToTicket(Long ticketId, User assigner, Long userId) throws Exception {

        Project project = ticketRepo.findProjectByTicketId(ticketId);

        if (project == null) {
            throw new Exception("Project with such ticketId does not exist!");
        }

        projectService.checkTeamMembership(project.getId(), assigner, "User not a project team member! Only team members can assign tickets!");

        User user = userFinder.findUserById(userId);
        Ticket ticket = getTicketById(ticketId);
        ticket.setAssignee(user);

        return ticketRepo.save(ticket);
    }

    @Override
    public Project getProjectByTicketId(Long ticketId) throws Exception {
        return ticketRepo.findProjectByTicketId(ticketId);
    }

    @Override
    public Ticket updateProgress(Long ticketId, User user, String progress) throws Exception {

        Project project = ticketRepo.findProjectByTicketId(ticketId);

        if (project == null) {
            throw new Exception("Project with such ticketId does not exist!");
        }

        projectService.checkTeamMembership(project.getId(), user, "User not a project team member! Only team members can update ticket progress!");

        Ticket ticket = getTicketById(ticketId);
        ticket.setProgress(progress);

        return ticketRepo.save(ticket);
    }
}
