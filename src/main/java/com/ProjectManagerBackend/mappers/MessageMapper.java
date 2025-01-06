package com.ProjectManagerBackend.mappers;

import com.ProjectManagerBackend.models.Message;
import com.ProjectManagerBackend.viewmodels.MessageViewModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MessageMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "body", source = "body")
    @Mapping(target = "creationDate", source = "creationDate")
    MessageViewModel convertMessageToMessageViewModel(Message entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "body", source = "body")
    @Mapping(target = "creationDate", source = "creationDate")
    List<MessageViewModel> convertMessagesToMessageViewModels(List<Message> entities);

}
