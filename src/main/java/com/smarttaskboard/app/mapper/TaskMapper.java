package com.smarttaskboard.app.mapper;

import com.smarttaskboard.app.dto.TaskDto;
import com.smarttaskboard.app.model.Task;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task toEntity(TaskDto taskDto);

    TaskDto toDto(Task task);

    List<TaskDto> toDtoList(List<Task> tasks);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTaskFromDto(TaskDto taskDto, @MappingTarget Task task);
}
