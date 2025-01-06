package com.ProjectManagerBackend.mappers;

import com.ProjectManagerBackend.dtos.ProjectDTO;
import com.ProjectManagerBackend.models.Project;
import com.ProjectManagerBackend.viewmodels.ProjectViewModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "developmentScope", source = "developmentScope")
    Project convertProjectDTOToProject(ProjectDTO dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "developmentScope", source = "developmentScope")
    @Mapping(target = "creator", source = "creator")
    ProjectViewModel convertProjectToProjectViewModel(Project entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "developmentScope", source = "developmentScope")
    @Mapping(target = "creator", source = "creator")
    List<ProjectViewModel> convertProjectsToProjectViewModels(List<Project> entities);

}
