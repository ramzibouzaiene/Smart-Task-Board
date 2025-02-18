package com.smarttaskboard.app.service;


import com.smarttaskboard.app.dto.TaskDto;

import java.util.List;
import java.util.Set;

public interface TaskService {
    List<TaskDto> getAllTasks();

    TaskDto getTaskById(Long id);

    void updateTask(Long id, TaskDto taskDto);

    TaskDto addNewTask(TaskDto taskDto);
    TaskDto assignUsersToTask(Long taskId, Set<Long> userIds);

    void deleteTask(Long id);
}
