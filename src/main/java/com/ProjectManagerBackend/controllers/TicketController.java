package com.ProjectManagerBackend.controllers;

import com.ProjectManagerBackend.common.constants.ApiMessageConstants;
import com.ProjectManagerBackend.common.constants.StatusMessageConstants;
import com.ProjectManagerBackend.common.enums.Progress;
import com.ProjectManagerBackend.dtos.TicketDTO;
import com.ProjectManagerBackend.mappers.TicketMapper;
import com.ProjectManagerBackend.models.Ticket;
import com.ProjectManagerBackend.models.User;
import com.ProjectManagerBackend.services.interfaces.TicketService;
import com.ProjectManagerBackend.services.interfaces.UserService;
import com.ProjectManagerBackend.viewmodels.StatusMessageViewModel;
import com.ProjectManagerBackend.viewmodels.TicketViewModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@Tag(name = ApiMessageConstants.TICKET_TAG_NAME, description = ApiMessageConstants.TICKET_TAG_DESCRIPTION)
public class TicketController {

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketMapper ticketMapper;

    @PostMapping("/{projectId}")
    @Operation(summary = ApiMessageConstants.CREATE_TICKET, security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<TicketViewModel> createTicket(@Parameter(description = ApiMessageConstants.AUTHORIZATION_HEADER_MESSAGE)
                                                        @RequestHeader(value = "Authorization", required = false) String token,
                                                        @RequestBody TicketDTO ticket,
                                                        @PathVariable Long projectId)
            throws Exception {

        User user = userService.findUserProfileByJwt(token);

        Ticket createdTicket = ticketService.createTicket(ticket, projectId, user);

        TicketViewModel ticketModel = ticketMapper.convertTicketToTicketViewModel(createdTicket);

        return ResponseEntity.ok(ticketModel);
    }

    @GetMapping("/{ticketId}")
    @Operation(summary = ApiMessageConstants.GET_TICKET)
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long ticketId) throws Exception {
        return ResponseEntity.ok(ticketService.getTicketById(ticketId));
    }

    @GetMapping("/project/{projectId}")
    @Operation(summary = ApiMessageConstants.GET_PROJECT_TICKETS)
    public ResponseEntity<List<TicketViewModel>> getTicketsByProjectId(@PathVariable Long projectId)
            throws Exception {
        List<Ticket> tickets = ticketService.getTicketsByProjectId(projectId);
        List<TicketViewModel> viewModels = ticketMapper.convertTicketsToTicketViewModels(tickets);
        return ResponseEntity.ok(viewModels);
    }

    @PutMapping("/{ticketId}/assignee/{userId}")
    @Operation(summary = ApiMessageConstants.ASSIGN_USER_TO_TICKET, security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<TicketViewModel> assignUserToTicket(@PathVariable Long ticketId,
                                                              @PathVariable Long userId,
                                                              @Parameter(description = ApiMessageConstants.AUTHORIZATION_HEADER_MESSAGE)
                                                              @RequestHeader(value = "Authorization", required = false) String token)
            throws Exception {
        User user = userService.findUserProfileByJwt(token);

        Ticket ticket = ticketService.assignUserToTicket(ticketId, user, userId);
        TicketViewModel viewModel = ticketMapper.convertTicketToTicketViewModel(ticket);
        return ResponseEntity.ok(viewModel);
    }

    @PutMapping("/{ticketId}/progress/{progress}")
    @Operation(summary = ApiMessageConstants.UPDATE_TICKET_PROGRESS, security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<TicketViewModel> updateTicketProgress(
            @PathVariable Progress progress,
            @PathVariable Long ticketId,
            @Parameter(description = ApiMessageConstants.AUTHORIZATION_HEADER_MESSAGE)
            @RequestHeader(value = "Authorization", required = false) String token)
            throws Exception {

        User user = userService.findUserProfileByJwt(token);

        Ticket ticket = ticketService.updateProgress(ticketId, user, progress);
        TicketViewModel viewModel = ticketMapper.convertTicketToTicketViewModel(ticket);
        return ResponseEntity.ok(viewModel);
    }

    @DeleteMapping("/{ticketId}")
    @Operation(summary = ApiMessageConstants.DELETE_TICKET, security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<StatusMessageViewModel> deleteTicket(@PathVariable Long ticketId,
                                                               @Parameter(description = ApiMessageConstants.AUTHORIZATION_HEADER_MESSAGE)
                                                               @RequestHeader(value = "Authorization", required = false) String token)
            throws Exception {

        User user = userService.findUserProfileByJwt(token);
        ticketService.deleteTicket(ticketId, user);

        StatusMessageViewModel res = new StatusMessageViewModel(String.format(StatusMessageConstants.DELETION_SUCCESSFUL, "Ticket"));
        return ResponseEntity.ok(res);
    }

}
