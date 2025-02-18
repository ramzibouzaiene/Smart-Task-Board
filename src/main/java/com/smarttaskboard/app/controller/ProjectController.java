package com.smarttaskboard.app.controller;

import com.smarttaskboard.app.dto.ProjectDto;
import com.smarttaskboard.app.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/projects")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        log.info("Fetching projects list");
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @PostMapping
    public ResponseEntity<ProjectDto> addNewProject(@RequestBody ProjectDto projectDto) {
        log.info("Adding new project {}", projectDto.getName());
        return ResponseEntity.ok(projectService.addNewProject(projectDto));
    }

    @GetMapping("/{projectName}")
    public ResponseEntity<ProjectDto> getProjectByName(@PathVariable String projectName) {
        log.info("Project {} details was fetched", projectName);
        return ResponseEntity.ok(projectService.getProjectByName(projectName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id) {
        log.info("Project with the id {} was fetched", id);
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateExistingProject(@PathVariable Long id, @RequestBody ProjectDto projectDto) {
        projectService.updateProject(id, projectDto);
        log.info("Project with the id {} was updated", id);
        return ResponseEntity.ok("Project Was Updated Successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        log.info("Project with the id {} was deleted", id);
        return ResponseEntity.ok("Project Was Deleted Successfully");
    }

    @PostMapping("/{id}/user/{userId}")
    public ResponseEntity<String> assignMemberToProject(@PathVariable Long id, @PathVariable Long userId) {
        projectService.assignMemberToProject(id, userId);
        log.info("Project with the id {} was to the user {}", id, userId);
        return ResponseEntity.ok("User Was Assigned Successfully To The Project");
    }

    @PostMapping("/{id}/task/{taskId}")
    public ResponseEntity<String> addTaskToProject(@PathVariable Long id, @PathVariable Long taskId) {
        projectService.addTaskToProject(id, taskId);
        log.info("Task with the id {} was to the project {}", taskId, id);
        return ResponseEntity.ok("Task Was Added Successfully To The Project");
    }
}
