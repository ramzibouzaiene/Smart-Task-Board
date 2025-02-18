package com.smarttaskboard.app.mapper;

import com.smarttaskboard.app.dto.CommentDto;
import com.smarttaskboard.app.model.Comment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment toEntity(CommentDto commentDto);

    CommentDto toDto(Comment comment);

    List<CommentDto> toDtoList(List<Comment> comments);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCommentFromDto(CommentDto commentDto, @MappingTarget Comment comment);
}
