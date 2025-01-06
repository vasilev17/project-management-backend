package com.ProjectManagerBackend.mappers;

import com.ProjectManagerBackend.models.Comment;
import com.ProjectManagerBackend.viewmodels.CommentViewModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "body", source = "body")
    @Mapping(target = "creationDate", source = "creationDate")
    CommentViewModel convertCommentToCommentViewModel(Comment entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "body", source = "body")
    @Mapping(target = "creationDate", source = "creationDate")
    List<CommentViewModel> convertCommentsToCommentViewModels(List<Comment> entities);

}
