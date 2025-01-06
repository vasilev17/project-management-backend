package com.ProjectManagerBackend.mappers;

import com.ProjectManagerBackend.models.CollaborationRequest;
import com.ProjectManagerBackend.viewmodels.CollaborationResponseViewModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CollaborationMapper {

    @Mapping(target = "projectId", source = "projectId")
    @Mapping(target = "collabRequestCreator", source = "creator")
    CollaborationResponseViewModel convertCollaborationRequestToCollaborationResponseViewModel(CollaborationRequest entity);

}
