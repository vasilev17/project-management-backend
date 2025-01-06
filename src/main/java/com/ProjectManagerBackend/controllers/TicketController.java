package com.ProjectManagerBackend.controllers;

import com.ProjectManagerBackend.common.enums.Progress;
import com.ProjectManagerBackend.dtos.TicketDTO;
import com.ProjectManagerBackend.mappers.TicketMapper;
import com.ProjectManagerBackend.models.Ticket;
import com.ProjectManagerBackend.models.User;
import com.ProjectManagerBackend.services.interfaces.TicketService;
import com.ProjectManagerBackend.services.interfaces.UserService;
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
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketMapper ticketMapper;

    @PostMapping("/{projectId}")
    public ResponseEntity<TicketViewModel> createTicket(@RequestBody TicketDTO ticket,
                                                        @PathVariable Long projectId,
                                                        @RequestHeader("Authorization") String token)
            throws Exception {

        User user = userService.findUserProfileByJwt(token);

        Ticket createdTicket = ticketService.createTicket(ticket, projectId, user);

        TicketViewModel ticketModel = ticketMapper.convertTicketToTicketViewModel(createdTicket);

        return ResponseEntity.ok(ticketModel);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long ticketId) throws Exception {
        return ResponseEntity.ok(ticketService.getTicketById(ticketId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TicketViewModel>> getTicketsByProjectId(@PathVariable Long projectId)
            throws Exception {
        List<Ticket> tickets = ticketService.getTicketsByProjectId(projectId);
        List<TicketViewModel> viewModels = ticketMapper.convertTicketsToTicketViewModels(tickets);
        return ResponseEntity.ok(viewModels);
    }

    @PutMapping("/{ticketId}/assignee/{userId}")
    public ResponseEntity<TicketViewModel> addUserToTicket(@PathVariable Long ticketId,
                                                           @PathVariable Long userId,
                                                           @RequestHeader("Authorization") String token)
            throws Exception {
        User user = userService.findUserProfileByJwt(token);

        Ticket ticket = ticketService.addUserToTicket(ticketId, user, userId);
        TicketViewModel viewModel = ticketMapper.convertTicketToTicketViewModel(ticket);
        return ResponseEntity.ok(viewModel);
    }

    @PutMapping("/{ticketId}/progress/{progress}")
    public ResponseEntity<TicketViewModel> updateTicketProgress(
            @PathVariable Progress progress,
            @PathVariable Long ticketId,
            @RequestHeader("Authorization") String token)
            throws Exception {

        User user = userService.findUserProfileByJwt(token);

        Ticket ticket = ticketService.updateProgress(ticketId, user, progress);
        TicketViewModel viewModel = ticketMapper.convertTicketToTicketViewModel(ticket);
        return ResponseEntity.ok(viewModel);
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<StatusMessageViewModel> deleteTicket(@PathVariable Long ticketId,
                                                               @RequestHeader("Authorization") String token)
            throws Exception {

        User user = userService.findUserProfileByJwt(token);
        ticketService.deleteTicket(ticketId, user);

        StatusMessageViewModel res = new StatusMessageViewModel("Ticket deleted successfully!");
        return ResponseEntity.ok(res);
    }

}
