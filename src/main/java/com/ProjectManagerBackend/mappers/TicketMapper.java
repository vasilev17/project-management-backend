package com.ProjectManagerBackend.mappers;

import com.ProjectManagerBackend.dtos.TicketDTO;
import com.ProjectManagerBackend.models.Ticket;
import com.ProjectManagerBackend.viewmodels.TicketViewModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TicketMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "deadline", source = "deadline")
    @Mapping(target = "importance", source = "importance")
    @Mapping(target = "progress", source = "progress")
    @Mapping(target = "tags", source = "tags")
    Ticket convertTicketDTOToTicket(TicketDTO dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "deadline", source = "deadline")
    @Mapping(target = "progress", source = "progress")
    @Mapping(target = "importance", source = "importance")
    @Mapping(target = "assignee", source = "assignee")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "project", source = "project")
    TicketViewModel convertTicketToTicketViewModel(Ticket entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "deadline", source = "deadline")
    @Mapping(target = "progress", source = "progress")
    @Mapping(target = "importance", source = "importance")
    @Mapping(target = "assignee", source = "assignee")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "project", source = "project")
    List<TicketViewModel> convertTicketsToTicketViewModels(List<Ticket> entities);

}
