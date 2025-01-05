package com.ProjectManagerBackend.controllers;

import com.ProjectManagerBackend.dtos.TicketDTO;
import com.ProjectManagerBackend.models.Ticket;
import com.ProjectManagerBackend.models.User;
import com.ProjectManagerBackend.services.interfaces.TicketService;
import com.ProjectManagerBackend.services.interfaces.UserFinder;
import com.ProjectManagerBackend.viewmodels.StatusMessageViewModel;
import com.ProjectManagerBackend.viewmodels.TicketViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private UserFinder userFinder;

    @Autowired
    private TicketService ticketService;

    @PostMapping("/{projectId}")
    public ResponseEntity<TicketViewModel> createTicket(@RequestBody TicketDTO ticket,
                                                        @PathVariable Long projectId,
                                                        @RequestHeader("Authorization") String token)
            throws Exception {

        User user = userFinder.findUserProfileByJwt(token);

        Ticket createdTicket = ticketService.createTicket(ticket, projectId, user);

        TicketViewModel ticketModel = new TicketViewModel();
        ticketModel.setDescription(createdTicket.getDescription());
        ticketModel.setDeadline(createdTicket.getDeadline());
        ticketModel.setId(createdTicket.getId());
        ticketModel.setImportance(createdTicket.getImportance());
        ticketModel.setProject(createdTicket.getProject());
        ticketModel.setProjectID(createdTicket.getProjectID());
        ticketModel.setProgress(createdTicket.getProgress());
        ticketModel.setName(createdTicket.getName());
        ticketModel.setTags(createdTicket.getTags());
        ticketModel.setAssignee(createdTicket.getAssignee());
        ticketModel.setAuthor(createdTicket.getAuthor());

        return ResponseEntity.ok(ticketModel);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long ticketId) throws Exception {
        return ResponseEntity.ok(ticketService.getTicketById(ticketId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Ticket>> getTicketByProjectId(@PathVariable Long projectId)
            throws Exception {
        return ResponseEntity.ok(ticketService.getTicketsByProjectId(projectId));
    }

    @PutMapping("/{ticketId}/assignee/{userId}")
    public ResponseEntity<Ticket> addUserToTicket(@PathVariable Long ticketId,
                                                  @PathVariable Long userId,
                                                  @RequestHeader("Authorization") String token)
            throws Exception {
        User user = userFinder.findUserProfileByJwt(token);

        Ticket ticket = ticketService.addUserToTicket(ticketId, user, userId);
        return ResponseEntity.ok(ticket);
    }

    @PutMapping("/{ticketId}/progress/{progress}")
    public ResponseEntity<Ticket> updateTicketProgress(
            @PathVariable String progress,
            @PathVariable Long ticketId,
            @RequestHeader("Authorization") String token)
            throws Exception {

        User user = userFinder.findUserProfileByJwt(token);

        Ticket ticket = ticketService.updateProgress(ticketId, user, progress);

        return ResponseEntity.ok(ticket);
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<StatusMessageViewModel> deleteTicket(@PathVariable Long ticketId,
                                                               @RequestHeader("Authorization") String token)
            throws Exception {

        User user = userFinder.findUserProfileByJwt(token);
        ticketService.deleteTicket(ticketId, user);

        StatusMessageViewModel res = new StatusMessageViewModel();
        res.setMessage("Ticket deleted successfully!");

        return ResponseEntity.ok(res);
    }

}
