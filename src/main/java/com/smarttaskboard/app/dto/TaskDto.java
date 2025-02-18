package com.smarttaskboard.app.dto;

import com.smarttaskboard.app.enums.PriorityStatus;
import com.smarttaskboard.app.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate dueDate;
    private PriorityStatus priority;
    private ProjectDto project;
    private Set<UserDto> assignedUsers;
    private List<CommentDto> comments;
}
