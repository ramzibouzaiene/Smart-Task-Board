package com.smarttaskboard.app.service;

import com.smarttaskboard.app.dto.ProjectDto;

import java.util.List;

public interface ProjectService {
    List<ProjectDto> getAllProjects();

    ProjectDto getProjectByName(String projectName);

    ProjectDto getProjectById(Long id);

    void updateProject(Long id, ProjectDto projectDto);

    ProjectDto addNewProject(ProjectDto projectDto);

    void assignMemberToProject(Long projectId, Long userId);

    void addTaskToProject(Long projectId, Long taskId);

    void deleteProject(Long id);
}
