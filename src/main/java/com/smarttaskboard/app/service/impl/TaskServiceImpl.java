package com.smarttaskboard.app.service.impl;

import com.smarttaskboard.app.dto.TaskDto;
import com.smarttaskboard.app.exception.ResourceNotFoundException;
import com.smarttaskboard.app.mapper.TaskMapper;
import com.smarttaskboard.app.model.Project;
import com.smarttaskboard.app.model.Task;
import com.smarttaskboard.app.model.User;
import com.smarttaskboard.app.repository.ProjectRepository;
import com.smarttaskboard.app.repository.TaskRepository;
import com.smarttaskboard.app.repository.UserRepository;
import com.smarttaskboard.app.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        log.info("Returned a list of all tasks");
        return taskMapper.toDtoList(tasks);
    }

    @Override
    public TaskDto getTaskById(Long id) {
        Optional<Task> taskDetails = taskRepository.findById(id);
        return taskDetails.map(taskMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Task with the id" + id + " not found"));

    }

    @Override
    public void updateTask(Long id, TaskDto taskDto) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));

        taskMapper.updateTaskFromDto(taskDto, existingTask);
        if (taskDto.getAssignedUsers() != null && !taskDto.getAssignedUsers().isEmpty()) {
            Set<User> assignedUsers = taskDto.getAssignedUsers().stream()
                    .map(userDto -> userRepository.findById(userDto.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("User with id " + userDto.getId() + " not found")))
                    .collect(Collectors.toSet());
            existingTask.setAssignedUsers(assignedUsers);
        }
        log.info("Task {} was updated", taskDto.getTitle());
        taskRepository.save(existingTask);
    }

    @Override
    public TaskDto assignUsersToTask(Long taskId, Set<Long> userIds) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + taskId + " not found"));

        Set<User> assignedUsers = userIds.stream()
                .map(userId -> userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found")))
                .collect(Collectors.toSet());

        task.setAssignedUsers(assignedUsers);
        Task updatedTask = taskRepository.save(task);

        log.info("Assigned {} users to Task '{}'", assignedUsers.size(), task.getTitle());
        return taskMapper.toDto(updatedTask);
    }

    @Override
    public TaskDto addNewTask(TaskDto taskDto) {
        Project project = projectRepository.findById(taskDto.getProject().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Project with id " + taskDto.getProject().getId() + " not found"));

        Task newTask = taskMapper.toEntity(taskDto);
        newTask.setProject(project);

        Task savedTask = taskRepository.save(newTask);
        log.info("New Task '{}' was added to Project '{}'", taskDto.getTitle(), project.getName());
        return taskMapper.toDto(savedTask);
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            log.error("Task {} not found", id);
            throw new ResourceNotFoundException("Task with id " + id + " not found");
        }
        taskRepository.deleteById(id);
    }
}
