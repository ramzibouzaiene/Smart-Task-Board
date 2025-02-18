package com.smarttaskboard.app.service.impl;

import com.smarttaskboard.app.dto.ProjectDto;
import com.smarttaskboard.app.exception.ResourceAlreadyExistException;
import com.smarttaskboard.app.exception.ResourceNotFoundException;
import com.smarttaskboard.app.exception.UnauthorizedException;
import com.smarttaskboard.app.mapper.ProjectMapper;
import com.smarttaskboard.app.model.Project;
import com.smarttaskboard.app.model.Task;
import com.smarttaskboard.app.model.User;
import com.smarttaskboard.app.repository.ProjectRepository;
import com.smarttaskboard.app.repository.TaskRepository;
import com.smarttaskboard.app.repository.UserRepository;
import com.smarttaskboard.app.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Override
    public List<ProjectDto> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        log.info("Returned a list of projects");
        return projectMapper.toDtoList(projects);
    }

    @Override
    public ProjectDto getProjectByName(String projectName) {
        Optional<Project> projectDetails = projectRepository.findByNameIgnoreCase(projectName);
        return projectDetails.map(projectMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Project not found"));
    }

    @Override
    public ProjectDto getProjectById(Long id) {
        Optional<Project> projectById = projectRepository.findById(id);
        return projectById.map(projectMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Project with the id" + id + " not found"));
    }

    @Transactional
    @Override
    public ProjectDto addNewProject(ProjectDto projectDto) {
        if (projectDto.getOwner() == null) {
            throw new ResourceAlreadyExistException("Project must have an owner");
        }

        User owner = userRepository.findById(projectDto.getOwner().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        Project newProject = projectMapper.toEntity(projectDto);
        newProject.setOwner(owner);

        Project savedProject = projectRepository.save(newProject);
        log.info("New project '{}' created by owner '{}'", projectDto.getName(), owner.getUsername());

        return projectMapper.toDto(savedProject);
    }

    @Transactional
    @Override
    public void updateProject(Long id, ProjectDto projectDto) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project with id " + id + " not found"));

        if (!existingProject.getOwner().getId().equals(projectDto.getOwner().getId())) {
            throw new UnauthorizedException("Only the project owner can update this project.");
        }

        projectMapper.updateProjectFromDto(projectDto, existingProject);
        projectRepository.save(existingProject);

        log.info("Project '{}' (ID: {}) updated", projectDto.getName(), id);
    }

    @Transactional
    @Override
    public void assignMemberToProject(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project with id " + projectId + " not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        if (project.getMembers().contains(user)) {
            throw new ResourceAlreadyExistException("User is already a member of this project.");
        }

        project.getMembers().add(user);
        projectRepository.save(project);

        log.info("User '{}' assigned to project '{}'", user.getUsername(), project.getName());
    }

    @Transactional
    @Override
    public void addTaskToProject(Long projectId, Long taskId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project with id " + projectId + " not found"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + taskId + " not found"));

        if (project.getTasks().contains(task)) {
            throw new ResourceAlreadyExistException("Task is already assigned to this project.");
        }

        project.getTasks().add(task);
        task.setProject(project);
        projectRepository.save(project);
        taskRepository.save(task);

        log.info("Task '{}' added to project '{}'", task.getTitle(), project.getName());
    }

    @Override
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            log.error("Project with id {} not found", id);
            throw new ResourceNotFoundException("Project with id " + id + " not found");
        }
        projectRepository.deleteById(id);
    }
}
